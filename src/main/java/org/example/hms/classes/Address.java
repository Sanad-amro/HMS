package org.example.hms.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class Address {
    private String name;

    public Address(String name) {
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


    public static void addDiagnosis(Address address) {
        addDiagnosisC(address);
        String sql = "INSERT INTO address (name) VALUES (?)";
        try(Connection connection =getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address.getName());
            preparedStatement.executeUpdate();
            System.out.println("address added !!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addDiagnosisC(Address address) {
        String sql = "INSERT INTO address (name) VALUES (?)";
        try(Connection connection =cloud()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address.getName());
            preparedStatement.executeUpdate();
            System.out.println("address added !!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<String> getAllItems() {
        ObservableList<String> diagnosisList = FXCollections.observableArrayList();
        String sql = "SELECT name FROM address";

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
