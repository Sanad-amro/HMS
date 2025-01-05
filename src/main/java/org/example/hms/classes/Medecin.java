package org.example.hms.classes;

import java.sql.*;
import java.util.ArrayList;

public class Medecin {
    private int id;
    private Double quantity;
    private String name;


    public Medecin(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public Medecin(int id, String name, Double quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Database connection details
    private static final String URL = "jdbc:mysql://195.123.166.125:3306/akram";
    private static final String USERNAME = "sanad";
    private static final String PASSWORD = "sanad";

    // Static method to add a Medecin
    public static void add(String name, double quantity) {
        String query = "INSERT INTO medecin (name, quantity) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setDouble(2, quantity);
            statement.executeUpdate();

            System.out.println("Medecin added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding Medecin: " + e.getMessage());
        }
    }

    // Static method to delete a Medecin
    public static void delete(int id) {
        String query = "DELETE FROM medecin WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Medecin deleted successfully.");
            } else {
                System.out.println("No Medecin found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting Medecin: " + e.getMessage());
        }
    }

    // Static method to decrement the quantity of a Medecin
    public static void decrementQuantity(int id, double decrementValue) {
        String query = "UPDATE medecin SET quantity = quantity - ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, decrementValue);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Medecin quantity decremented successfully.");
            } else {
                System.out.println("No Medecin found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error decrementing quantity: " + e.getMessage());
        }
    }


    public static ArrayList<Medecin> getAll() {
        String query = "SELECT * FROM medecin";
        ArrayList<Medecin> Medecins = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double quantity = resultSet.getDouble("quantity");
                Medecins.add(new Medecin(id,  name, quantity));
            }

            System.out.println("Medecins retrieved successfully.");
        } catch (SQLException e) {
            System.err.println("Error retrieving Medecins: " + e.getMessage());
        }

        return Medecins;
    }
    public static Medecin getMedecinById(int id) {
        String query = "SELECT * FROM medecin WHERE id = ?";
        Medecin medecin = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getDouble("quantity");
                    medecin = new Medecin( id,name, quantity);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Medecin by ID: " + e.getMessage());
        }

        if (medecin == null) {
            System.out.println("No Medecin found with the given ID.");
        }
        return medecin;
    }
    public static void updateQuantity(int id, double newQuantity) {
        String query = "UPDATE medecin SET quantity = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, newQuantity);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Medecin quantity updated successfully.");
            } else {
                System.out.println("No Medecin found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating quantity: " + e.getMessage());
        }
    }
    

    // Main method for testing
  
}
