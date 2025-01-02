package org.example.hms.classes;

import java.sql.*;
import java.util.ArrayList;

public class Patient {

        // Database connection details
        private static final String DB_URL = "jdbc:mysql://195.123.166.125:3306/akram";
        private static final String DB_USER = "sanad";
        private static final String DB_PASSWORD = "sanad";

        // Patient fields
        private int patientId;
        private String name;
        private String phoneNumber;
        private String address;
        private String addedBy;
        private double height ;
        private double weight;

        public double getWeight() {
                return weight;
        }

        public void setWeight(double weight) {
                this.weight = weight;
        }

        public double getHeight() {
                return height;
        }

        public void setHeight(double height) {
                this.height = height;
        }

        private int age;

        // Constructor
        public Patient(int patientId, String name, String phoneNumber, String address, int age,String addedBy, double height) {
                this.patientId = patientId;
                this.name = name;
                this.phoneNumber = phoneNumber;
                this.address = address;
                this.age = age;
                this.addedBy = addedBy;
                this.height = height;
        }

        // Getter and Setter methods
        public int getPatientId() {
                return patientId;
        }

        public void setPatientId(int patientId) {
                this.patientId = patientId;
        }

        public String getName() {
                return name;
        }

        public String getAddedBy() {
                return addedBy;
        }

        public void setAddedBy(String addedBy) {
                this.addedBy = addedBy;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        // Function to check the connection and ensure necessary tables exist
        private static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }

        // Method to create the 'patients' table if it doesn't exist
        private static void checkConnection() {
                try (Connection conn = getConnection()) {
                        Statement stmt = conn.createStatement();

                        // Check if the database exists
                        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS akram");

                        // Create the 'patients' table if it doesn't exist
                        String createTableSQL = "CREATE TABLE IF NOT EXISTS patients (" +
                                "id INT PRIMARY KEY, " +
                                "name VARCHAR(100), " +
                                "phone_number VARCHAR(15), " +
                                "address VARCHAR(255), " +
                                "age INT,"+"added_By VARCHAR(255),"+
                                "height DOUBLE);";
                        stmt.executeUpdate(createTableSQL);

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Add a new patient
        public static void addPatient(ArrayList<Patient> patients) {
                Patient patient=patients.get(0);
                checkConnection();
                String query = "INSERT INTO patients (id, name, phone_number, address,added_By, age, height) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patient.getPatientId());
                        stmt.setString(2, patient.getName());
                        stmt.setString(3, patient.getPhoneNumber());
                        stmt.setString(4, patient.getAddress());
                        stmt.setString(5, patient.getAddedBy());
                        stmt.setInt(6, patient.getAge());
                        stmt.setDouble(7, patient.getHeight());
                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Delete a patient by ID
        public static void deletePatient(int patientId) {
                checkConnection();
                String query = "DELETE FROM patients WHERE id = ?";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patientId);
                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Update a patient's information
        public static void UpdatePatientInfo(ArrayList<Patient> patients) {
                Patient patient=patients.get(0);
                checkConnection();
                String query = "UPDATE patients SET name = ?, phone_number = ?, address = ?, added_By=?, age = ?, height = ? WHERE id = ?";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, patient.getName());
                        stmt.setString(2, patient.getPhoneNumber());
                        stmt.setString(3, patient.getAddress());
                        stmt.setInt(4, patient.getAge());
                        stmt.setString(5, patient.getAddedBy());
                        stmt.setDouble(6, patient.getHeight());
                        stmt.setInt(7, patient.getPatientId());
                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Get all patients
        public static ArrayList<Patient> getAllPatients() {
                checkConnection();
                ArrayList<Patient> patientsList = new ArrayList<>();
                String query = "SELECT * FROM patients";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                                Patient patient = new Patient(rs.getInt("id"), rs.getString("name"),
                                        rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getString("added_By"),rs.getDouble("height"));
                                patientsList.add(patient);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return patientsList;
        }

        // Check if a patient ID exists
        public static boolean doesIdExists(int patientId) {
                checkConnection();
                String query = "SELECT COUNT(*) FROM patients WHERE id = ?";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patientId);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return rs.getInt(1) > 0; // If count is greater than 0, ID exists
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

        // Get a patient by ID
        public static Patient getPatient(int patientId) {
                checkConnection();
                String query = "SELECT * FROM patients WHERE id = ?";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patientId);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return new Patient(rs.getInt("id"), rs.getString("name"),
                                        rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getString("added_By"),rs.getDouble(("height")));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null; // Return null if patient does not exist
        }
}
