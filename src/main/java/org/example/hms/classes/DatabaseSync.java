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

            List<String> tables = getTables(cloudConn);
            int totalTables = tables.size();
            int currentTable = 0;

            for (String table : tables) {
                syncTableStructure(cloudConn, localConn, table);
                syncTableData(cloudConn, localConn, table);
                currentTable++;
                final double progress = (double) currentTable / totalTables;
                final int percentage = (int) (progress * 100);
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
                // Exclude system tables
                if (!tableName.startsWith("pma__")) {
                    tables.add(tableName);
                }
            }
        }
        return tables;
    }


    private static void syncTableStructure(Connection cloudConn, Connection localConn, String table) throws SQLException {
        Map<String, String> cloudSchema = getTableSchema(cloudConn, table);
        Map<String, String> localSchema = getTableSchema(localConn, table);

        if (localSchema.isEmpty()) {
            createTable(localConn, table, cloudSchema);
        } else {
            updateTableStructure(localConn, table, cloudSchema, localSchema);
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

    private static void createTable(Connection conn, String table, Map<String, String> schema) throws SQLException {
        StringBuilder createSQL = new StringBuilder("CREATE TABLE " + table + " (");
        for (Map.Entry<String, String> entry : schema.entrySet()) {
            createSQL.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
        }
        createSQL.setLength(createSQL.length() - 2);
        createSQL.append(")");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createSQL.toString());
        }
    }

    private static void updateTableStructure(Connection conn, String table, Map<String, String> cloudSchema, Map<String, String> localSchema) throws SQLException {
        for (String column : cloudSchema.keySet()) {
            if (!localSchema.containsKey(column)) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD COLUMN " + column + " " + cloudSchema.get(column));
                }
            }
        }
    }

    private static void syncTableData(Connection cloudConn, Connection localConn, String table) throws SQLException {
        try (Statement localStmt = localConn.createStatement()) {
            localStmt.executeUpdate("DELETE FROM " + table);
        }
        String selectSQL = "SELECT * FROM " + table;
        try (Statement cloudStmt = cloudConn.createStatement();
             ResultSet rs = cloudStmt.executeQuery(selectSQL);
             PreparedStatement insertStmt = createInsertStatement(localConn, table, rs)) {
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    insertStmt.setObject(i, rs.getObject(i));
                }
                insertStmt.executeUpdate();
            }
        }
    }

    private static PreparedStatement createInsertStatement(Connection conn, String table, ResultSet rs) throws SQLException {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + table + " VALUES (");
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            insertSQL.append("?, ");
        }
        insertSQL.setLength(insertSQL.length() - 2);
        insertSQL.append(")");
        return conn.prepareStatement(insertSQL.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
