package org.example.hms.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class Diagnosis {
    private String name;

    public Diagnosis(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/akram";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final String CL_URL = "jdbc:mysql://195.123.166.125:3306/akram";
    private static final String CL_USER = "sanad";
    private static final String CL_PASSWORD = "sanad";
    private static Connection cloud() throws SQLException {
        return DriverManager.getConnection(CL_URL, CL_USER, CL_PASSWORD);
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public static void addDiagnosis(Diagnosis diagnosis) {
        addDiagnosisC(diagnosis);
        String sql = "INSERT INTO diagnosis (name) VALUES (?)";
        try(Connection connection =getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, diagnosis.getName());
            preparedStatement.executeUpdate();
            System.out.println("diagnosis added !!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addDiagnosisC(Diagnosis diagnosis) {
        String sql = "INSERT INTO diagnosis (name) VALUES (?)";
        try(Connection connection =cloud()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, diagnosis.getName());
            preparedStatement.executeUpdate();
            System.out.println("diagnosis added !!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<String> getAllItems() {
        ObservableList<String> diagnosisList = FXCollections.observableArrayList();
        String sql = "SELECT name FROM diagnosis";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                diagnosisList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return diagnosisList;
    }

}
