package org.example.hms.classes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;

public class DatabaseSync extends Application {
    private static final String CLOUD_DB_URL = "jdbc:mysql://195.123.166.125/akram";
    private static final String CLOUD_DB_USER = "sanad";
    private static final String CLOUD_DB_PASSWORD = "sanad";

    private static final String LOCAL_DB_URL = "jdbc:mysql://127.0.0.1:3306/akram";
    private static final String LOCAL_DB_USER = "root";
    private static final String LOCAL_DB_PASSWORD = "";

    private ProgressBar progressBar;
    private Label progressLabel;
    private Stage progressStage;

    @Override
    public void start(Stage primaryStage) {
        progressStage = new Stage();
        progressBar = new ProgressBar(0);
        progressLabel = new Label("0%");

        VBox root = new VBox(20, progressBar, progressLabel);
        root.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        Scene scene = new Scene(root, 300, 150);
        progressStage.setScene(scene);
        progressStage.setTitle("Syncing Database");
        progressStage.show();

        new Thread(this::syncDatabases).start();
    }

    public void syncDatabases() {
        try (Connection cloudConn = DriverManager.getConnection(CLOUD_DB_URL, CLOUD_DB_USER, CLOUD_DB_PASSWORD);
             Connection localConn = DriverManager.getConnection(LOCAL_DB_URL, LOCAL_DB_USER, LOCAL_DB_PASSWORD)) {

            List<String> cloudTables = getTables(cloudConn);
            List<String> localTables = getTables(localConn);

            // Count only missing tables and tables that need updates
            int totalTables = cloudTables.size();
            if (totalTables == 0) totalTables = 1; // Avoid division by zero

            int currentTable = 0;

            for (String table : cloudTables) {
                if (!localTables.contains(table)) {
                    cloneTable(cloudConn, localConn, table);
                }
                syncTableStructure(cloudConn, localConn, table);
                syncTableData(cloudConn, localConn, table);

                currentTable++;
                double progress = Math.min((double) currentTable / totalTables, 1.0); // Ensure max is 100%
                int percentage = (int) (progress * 100);

                Platform.runLater(() -> {
                    progressBar.setProgress(progress);
                    progressLabel.setText(percentage + "%");
                });
            }

            Platform.runLater(progressStage::close);
            System.out.println("Database synchronization complete.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static List<String> getTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (!tableName.startsWith("pma__") && !tableName.equalsIgnoreCase("performance_schema")) {
                    tables.add(tableName);
                }
            }
        }
        return tables;
    }

    private static void cloneTable(Connection srcConn, Connection destConn, String table) throws SQLException {
        try (Statement stmt = srcConn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table)) {
            if (rs.next()) {
                try (Statement destStmt = destConn.createStatement()) {
                    destStmt.executeUpdate(rs.getString(2));
                }
            }
        }
    }

    private static void syncTableStructure(Connection srcConn, Connection destConn, String table) throws SQLException {
        Map<String, String> srcSchema = getTableSchema(srcConn, table);
        Map<String, String> destSchema = getTableSchema(destConn, table);

        for (String column : srcSchema.keySet()) {
            if (!destSchema.containsKey(column)) {
                try (Statement stmt = destConn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD COLUMN " + column + " " + srcSchema.get(column));
                }
            }
        }
    }

    private static Map<String, String> getTableSchema(Connection conn, String table) throws SQLException {
        Map<String, String> schema = new LinkedHashMap<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("DESCRIBE " + table)) {
            while (rs.next()) {
                schema.put(rs.getString("Field"), rs.getString("Type"));
            }
        }
        return schema;
    }

    private static void syncTableData(Connection srcConn, Connection destConn, String table) throws SQLException {
        String selectSQL = "SELECT * FROM " + table;
        try (Statement srcStmt = srcConn.createStatement();
             ResultSet rs = srcStmt.executeQuery(selectSQL);
             PreparedStatement insertStmt = createInsertStatement(destConn, table, rs)) {
            while (rs.next()) {
                if (!rowExists(destConn, table, rs)) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        insertStmt.setObject(i, rs.getObject(i));
                    }
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    private static boolean rowExists(Connection conn, String table, ResultSet rs) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM " + table + " WHERE ");
        ResultSetMetaData metaData = rs.getMetaData();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (i > 1) query.append(" AND ");
            query.append(metaData.getColumnName(i)).append(" = ?");
        }

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                stmt.setObject(i, rs.getObject(i));
            }
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    private static PreparedStatement createInsertStatement(Connection conn, String table, ResultSet rs) throws SQLException {
        StringBuilder insertSQL = new StringBuilder("INSERT IGNORE INTO " + table + " VALUES (");
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            insertSQL.append("?, ");
        }
        insertSQL.setLength(insertSQL.length() - 2);
        insertSQL.append(")");
        return conn.prepareStatement(insertSQL.toString());
    }



    private void updateProgress(int current, int total) {
        double progress = (double) current / total;
        int percentage = (int) (progress * 100);
        Platform.runLater(() -> {
            progressBar.setProgress(progress);
            progressLabel.setText(percentage + "%");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
