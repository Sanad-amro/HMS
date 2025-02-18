package org.example.hms.classes;

import java.sql.*;
import java.util.ArrayList;

public class Patient {

        // Database connection details
        private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/akram";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "";

        private static final String CL_URL = "jdbc:mysql://195.123.166.125:3306/akram";
        private static final String CL_USER = "sanad";
        private static final String CL_PASSWORD = "sanad";
        private static Connection cloud() throws SQLException {
                return DriverManager.getConnection(CL_URL, CL_USER, CL_PASSWORD);
        }

        // Patient fields
        private int patientId;
        private String name;
        private String phoneNumber;
        private String address;
        private String addedBy;
        private double height ;
        private double weight;
        private int yy;
        private int mm;
        private int dd;
        private int dB;
        private int mB;
        private int yB;
        private boolean medicalDay;
        private String lastVisit;
        private int numOfVisits;
        private String cause;
        private boolean exists;

        public boolean isExists() {
                return exists;
        }

        public void setExists(boolean exists) {
                this.exists = exists;
        }

        public int getdB() {
                return dB;
        }

        public void setdB(int dB) {
                this.dB = dB;
        }

        public int getmB() {
                return mB;
        }

        public void setmB(int mB) {
                this.mB = mB;
        }

        public int getyB() {
                return yB;
        }

        public void setyB(int yB) {
                this.yB = yB;
        }

        public boolean isMedicalDay() {
                return medicalDay;
        }

        public void setMedicalDay(boolean medicalDay) {
                this.medicalDay = medicalDay;
        }

        public String getLastVisit() {
                return lastVisit;
        }

        public void setLastVisit(String lastVisit) {
                this.lastVisit = lastVisit;
        }

        public int getN_visits() {
                return numOfVisits;
        }

        public void setNumOfVisits(int numOfVisits) {
                this.numOfVisits = numOfVisits;
        }

        public int getYy() {
                return yy;
        }

        public void setYy(int yy) {
                this.yy = yy;
        }

        public int getMm() {
                return mm;
        }

        public void setMm(int mm) {
                this.mm = mm;
        }

        public int getDd() {
                return dd;
        }

        public void setDd(int dd) {
                this.dd = dd;
        }

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


        // Constructor
        public Patient(int patientId, String name, String phoneNumber, String address,String addedBy, double height,int yy, int mm, int dd, int dB, int mB, int yB, int numOfVisits, String lastVisit, boolean medicalDay, String cause, boolean exists) {
                this.patientId = patientId;
                this.name = name;
                this.phoneNumber = phoneNumber;
                this.address = address;
                this.addedBy = addedBy;
                this.height = height;
                this.yy = yy;
                this.mm = mm;
                this.dd = dd;
                this.dB = dB;
                this.mB = mB;
                this.yB = yB;
                this.numOfVisits = numOfVisits;
                this.lastVisit = lastVisit;
                this.medicalDay = medicalDay;
                this.cause = cause;
                this.exists = exists;
        }

        public Patient(int patientId, String name, String phoneNumber, String address,String addedBy, double height, int dB, int mB, int yB, int numOfVisits, boolean medicalDay,String cause,boolean exists) {
                this.patientId = patientId;
                this.name = name;
                this.phoneNumber = phoneNumber;
                this.address = address;
                this.addedBy = addedBy;
                this.height = height;
                this.dB = dB;
                this.mB = mB;
                this.yB = yB;
                this.numOfVisits = numOfVisits;
                this.medicalDay = medicalDay;
                this.cause = cause;
                this.exists = exists;

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

        public String getCause() {
                return cause;
        }

        public void setCause(String cause) {
                this.cause = cause;
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
                addPatientC(patients);
                Patient patient=patients.get(0);
                checkConnection();
                String query = "INSERT INTO patients (id, name, phone_number, address,added_By, height,yy,mm,dd,yb,mb,db, n_visits, last_visit , medDay,cause,ramcos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patient.getPatientId());
                        stmt.setString(2, patient.getName());
                        stmt.setString(3, patient.getPhoneNumber());
                        stmt.setString(4, patient.getAddress());
                        stmt.setString(5, patient.getAddedBy());
                        stmt.setDouble(6, patient.getHeight());
                        stmt.setInt(7, patient.getYy());
                        stmt.setInt(8, patient.getMm());
                        stmt.setInt(9, patient.getDd());
                        stmt.setInt(10,patient.getyB());
                        stmt.setInt(11, patient.getmB());
                        stmt.setInt(12,patient.getdB());
                        stmt.setInt(13, patient.getN_visits());
                        stmt.setString(14,patient.getLastVisit());
                        stmt.setBoolean(15,patient.isMedicalDay());
                        stmt.setString(16, patient.getCause());
                        stmt.setBoolean(17, patient.isExists());



                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public static void addPatientC(ArrayList<Patient> patients) {
                Patient patient=patients.get(0);
                checkConnection();
                String query = "INSERT INTO patients (id, name, phone_number, address,added_By, height,yy,mm,dd,yb,mb,db, n_visits, last_visit , medDay,cause, ramcos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
                try (Connection conn = cloud(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patient.getPatientId());
                        stmt.setString(2, patient.getName());
                        stmt.setString(3, patient.getPhoneNumber());
                        stmt.setString(4, patient.getAddress());
                        stmt.setString(5, patient.getAddedBy());
                        stmt.setDouble(6, patient.getHeight());
                        stmt.setInt(7, patient.getYy());
                        stmt.setInt(8, patient.getMm());
                        stmt.setInt(9, patient.getDd());
                        stmt.setInt(10,patient.getyB());
                        stmt.setInt(11, patient.getmB());
                        stmt.setInt(12,patient.getdB());
                        stmt.setInt(13, patient.getN_visits());
                        stmt.setString(14,patient.getLastVisit());
                        stmt.setBoolean(15,patient.isMedicalDay());
                        stmt.setString(16, patient.getCause());
                        stmt.setBoolean(17, patient.isExists());


                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Delete a patient by ID
        public static void deletePatient(int patientId) {
                deletePatientC(patientId);
                checkConnection();
                String query = "DELETE FROM patients WHERE id = ?";
                try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patientId);
                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public static void deletePatientC(int patientId) {
                checkConnection();
                String query = "DELETE FROM patients WHERE id = ?";
                try (Connection conn = cloud(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, patientId);
                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        // Update a patient's information
        public static void UpdatePatientInfo(ArrayList<Patient> patients) {
                UpdatePatientInfoC(patients);
                Patient patient=patients.get(0);
                checkConnection();
                String query = "UPDATE patients SET name = ?, phone_number = ?, address = ?, added_By = ?, height = ?, yy = ?, mm = ?, dd = ?, yb = ?, mb = ?, db = ?, n_visits = ?, last_visit = ?, medDay = ?, cause = ?, ramcos = ? WHERE id = ?";
                try (Connection conn = cloud(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, patient.getName());
                        stmt.setString(2, patient.getPhoneNumber());
                        stmt.setString(3, patient.getAddress());
                        stmt.setString(4, patient.getAddedBy());
                        stmt.setDouble(5, patient.getHeight());
                        stmt.setInt(6, patient.getYy());
                        stmt.setInt(7, patient.getMm());
                        stmt.setInt(8, patient.getDd());
                        stmt.setInt(9,patient.getyB());
                        stmt.setInt(10, patient.getmB());
                        stmt.setInt(11,patient.getdB());
                        stmt.setInt(12, patient.getN_visits());
                        stmt.setString(13,patient.getLastVisit());
                        stmt.setBoolean(14,patient.isMedicalDay());
                        stmt.setString(15, patient.getCause());
                        stmt.setBoolean(16, patient.isExists());
                        stmt.setInt(17, patient.getPatientId());


                        stmt.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }


        public static void UpdatePatientInfoC(ArrayList<Patient> patients) {
                Patient patient=patients.get(0);
                checkConnection();
                String query = "UPDATE patients SET name = ?, phone_number = ?, address = ?, added_By = ?, height = ?, yy = ?, mm = ?, dd = ?, yb = ?, mb = ?, db = ?, n_visits = ?, last_visit = ?, medDay = ?, cause = ?, ramcos = ? WHERE id = ?";
                try (Connection conn = cloud(); PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, patient.getName());
                        stmt.setString(2, patient.getPhoneNumber());
                        stmt.setString(3, patient.getAddress());
                        stmt.setString(4, patient.getAddedBy());
                        stmt.setDouble(5, patient.getHeight());
                        stmt.setInt(6, patient.getYy());
                        stmt.setInt(7, patient.getMm());
                        stmt.setInt(8, patient.getDd());
                        stmt.setInt(9,patient.getyB());
                        stmt.setInt(10, patient.getmB());
                        stmt.setInt(11,patient.getdB());
                        stmt.setInt(12, patient.getN_visits());
                        stmt.setString(13,patient.getLastVisit());
                        stmt.setBoolean(14,patient.isMedicalDay());
                        stmt.setString(15, patient.getCause());
                        stmt.setBoolean(16, patient.isExists());
                        stmt.setInt(17, patient.getPatientId());


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
                                        rs.getString("phone_number"), rs.getString("address"),rs.getString("added_By"),rs.getDouble("height"),rs.getInt("yy"), rs.getInt("mm"), rs.getInt("dd"),rs.getInt("db"),rs.getInt("mb"), rs.getInt("yb"), rs.getInt("n_visits"), rs.getString("last_visit"),rs.getBoolean("medDay"), rs.getString("cause"),rs.getBoolean("ramcos"));
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
                                return new  Patient(rs.getInt("id"), rs.getString("name"),
                                        rs.getString("phone_number"), rs.getString("address"),rs.getString("added_By"),rs.getDouble("height"),rs.getInt("yy"), rs.getInt("mm"), rs.getInt("dd"),rs.getInt("db"),rs.getInt("mb"), rs.getInt("yb"), rs.getInt("n_visits"), rs.getString("last_visit"),rs.getBoolean("medDay"),rs.getString("cause"),rs.getBoolean("ramcos"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null; // Return null if patient does not exist
        }
        public static boolean searchInColumn(String table, String column, String searchString) {
                String query = "SELECT 1 FROM " + table + " WHERE " + column + " LIKE ?";
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                        // Use prepared statement to avoid SQL injection
                        stmt.setString(1, "%" + searchString + "%");

                        // Execute the query
                        ResultSet rs = stmt.executeQuery();

                        // If a result is returned, it means the string is found
                        return rs.next();
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;  // In case of an error, return false
                }
        }

        public static boolean incrementVisits(int patientId) {
                String updateQuery = "UPDATE patients SET n_visits = n_visits + 1 WHERE id = ?";

                try (Connection connection = getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                        preparedStatement.setInt(1, patientId);
                        int rowsUpdated = preparedStatement.executeUpdate();

                        // Return true if at least one row was updated
                        return rowsUpdated > 0;

                } catch (SQLException e) {
                        e.printStackTrace();
                        return false; // Return false if an error occurs
                }
        }

}
