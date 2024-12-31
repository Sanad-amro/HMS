package org.example.hms.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    private Stage stage;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label tilte;
    @FXML
    private TextField UTF;
    @FXML
    private PasswordField PTF;
    @FXML
    private Button loginButton;
    @FXML
    private Label error;
    private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() throws IOException {

        UTF.setOnAction(e -> PTF.requestFocus());
        PTF.setOnAction(e -> loginButton.fire());
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws IOException {
        if(User.doesUsernameExists(UTF.getText())){
            System.out.println("this is a user");
            User user = User.getUser(UTF.getText());

            if(user == null || !(user.getPassword().equals(PTF.getText())) || user.getUserName().isEmpty()|| user.getUserName().isBlank()){
                error.setVisible(true);
            }
            else{

                System.out.println("trying to login");
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
                root=loader.load();
                Doctors doctors=loader.getController();
                doctors.setUser(User.getUser(UTF.getText()));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("HMS-Main");
                stage.show();
            }
        }else {
            System.out.println("this is a doctor!!");
            Doctor doctor= Doctor.getDoctor(UTF.getText());
            if(doctor == null || !(doctor.getPassword().equals(PTF.getText())) || doctor.getUserName().isEmpty()|| doctor.getUserName().isBlank()){
                error.setVisible(true);
            }
            else{

                System.out.println("trying to login");
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
                root=loader.load();
                Doctors doctors=loader.getController();
                    doctors.setDoctor(Doctor.getDoctor(UTF.getText()));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.setTitle("HMS-Main");
                stage.show();
            }

        }




    }






    private static final String DB_URL = "jdbc:mysql://195.123.166.125:3306/akram";
    private static final String DB_USER = "sanad";
    private static final String DB_PASSWORD = "sanad";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void checkConnection() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();

            // Check if the database exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS akram");

            // Check if the required tables exist and create them if not
            String[] tables = {"doctors", "patients", "inventory", "sessions"};
            for (String table : tables) {
                String createTableSQL = getCreateTableSQL(table);
                stmt.executeUpdate(createTableSQL);
            }
            System.out.println("Connected to database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the SQL to create a table if it doesn't exist
    private static String getCreateTableSQL(String table) {
        switch (table) {
            case "sessions":
                return "CREATE TABLE IF NOT EXISTS sessions (" +
                        "session_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "patient_id INT NOT NULL, " +
                        "hgb INT, height INT, weight INT, blood_glucose INT, fasting_blood_glucose INT, random_blood_glucose INT, " +
                        "heart_rate INT, diastolic_blood_pressure INT, systolic_blood_pressure INT, blood_pressure VARCHAR(10), " +
                        "chief_complaint VARCHAR(255), medical_history TEXT, medical_and_surgical_history TEXT, " +
                        "obstetric_history TEXT, gynecological_history TEXT, doctor_and_midwife_note TEXT, " +
                        "diagnosis TEXT, current_medications TEXT, prescribed_medications TEXT, " +
                        "nutritionist_note TEXT, physiotherapist_note TEXT, added_by VARCHAR(100), " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " + // Auto-generated
                        "day INT, month INT, year INT, " +
                        "FOREIGN KEY (patient_id) REFERENCES patients(id))";
            case "doctors":
                return "CREATE TABLE doctors (\n" +
                        "    id INT PRIMARY KEY,                 -- Doctor ID (manual entry)\n" +
                        "    name VARCHAR(255),                   -- Name of the doctor\n" +
                        "    email VARCHAR(255),                  -- Email address of the doctor\n" +
                        "    address VARCHAR(255),                -- Address of the doctor\n" +
                        "    appointment_admin BOOLEAN,           -- Whether the doctor has appointment admin rights\n" +
                        "    Users_admin BOOLEAN,                 -- Whether the doctor has user admin rights\n" +
                        "    Patients_admin BOOLEAN,              -- Whether the doctor has patient admin rights\n" +
                        "    Doctors_admin BOOLEAN,               -- Whether the doctor has doctor admin rights\n" +
                        "    inventory_admin BOOLEAN,             -- Whether the doctor has inventory admin rights\n" +
                        "    staff_admin BOOLEAN,                 -- Whether the doctor has staff admin rights\n" +
                        "    sector VARCHAR(255),                 -- Sector the doctor works in\n" +
                        "    Username VARCHAR(255),               -- Username of the doctor\n" +
                        "    Password VARCHAR(255),               -- Password for the doctor\n" +
                        "    role VARCHAR(255),                   -- Role of the doctor (e.g., 'Surgeon', 'GP')\n" +
                        "    speciality VARCHAR(255)              -- Speciality of the doctor (e.g., 'Cardiology', 'Neurology')\n" +
                        ");\n";
            case "patients":
                return "CREATE TABLE IF NOT EXISTS patients (" +
                        "id INT PRIMARY KEY, " +
                        "name VARCHAR(100), " +
                        "phone_number VARCHAR(15), " +
                        "address VARCHAR(255), " +
                        "age INT,"+"added_By VARCHAR(255));";
            case "inventory":
                return "CREATE TABLE IF NOT EXISTS inventory (" +
                        "item_id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(100), quantity INT, expire_date VARCHAR(10))";
            default:
                return "";
        }
    }
    public void tryC(ActionEvent actionEvent) {
        checkConnection();
    }
}
