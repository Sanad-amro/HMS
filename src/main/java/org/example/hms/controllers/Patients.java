package org.example.hms.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.Patient;
import org.example.hms.classes.Patient;
import org.example.hms.classes.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Patients {
    private User user;
    private Doctor doctor;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    Parent root;
    Stage stage = new Stage();
    @FXML
    TableView<Patient> patientsTable;

    @FXML
    TableColumn<Patient, String> idC ;
    @FXML
    TableColumn<Patient, String> nameC;
    @FXML
    TableColumn<Patient, String> doctorC;
    @FXML
    TextField searchField;
    @FXML
    TextField searchField1;
    @FXML
    private VBox buttons;

    private int idOfSlectedPatient=0;
    public void blankClicked(MouseEvent event) {
        searchField.getParent().requestFocus();
        searchField1.getParent().requestFocus();


    }

    public void hideButtons(MouseEvent event) {
    }

    public void showButtons(MouseEvent event) {
    }


    public void addPatient(ActionEvent event) throws IOException {
        System.out.println(doctor.getName());
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/add_patient.fxml"));
        Parent root = window.load();
        AddPatient addPatient= window.getController();
        addPatient.setDoctor(doctor);
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-Patient");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            try {
                initialize();
                System.out.println("closed!");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage1.show();

    }

    public void setWork(ActionEvent event) {
    }

    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete Mr."+ Patient.getPatient(idOfSlectedPatient).getName()+"?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Patient.deletePatient(idOfSlectedPatient);
            initialize();
        }
    }


    @FXML
    public void Logout(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/hms/Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("HMS-LogIn");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load the login page.");
        }

    }
    @FXML
    private void close(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    public void initialize() throws IOException {
        System.out.println("i was herer 1");
        searchField.clear();
        searchField.getParent().requestFocus();
        buttons.setOpacity(0);
        idC.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorC.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
        System.out.println("i was here 2");


        System.out.println("Heeeeyyy!!! (:");
        List<Patient> patients= Patient.getAllPatients();
        ObservableList<Patient> patientsList= FXCollections.observableArrayList(patients);
        FilteredList<Patient> filteredList=new FilteredList<>(patientsList, d -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(patient1 -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                if ( patient1.getName().toLowerCase().contains(lowerFilter) ||  String.valueOf(patient1.getPatientId()).contains(newValue))
                    return true;
                else return false;
            });
        });

        searchField1.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(patient1 -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                if ( patient1.getAddedBy().toLowerCase().contains(lowerFilter))
                    return true;
                else return false;
            });
        });

        patientsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
            @Override
            public void changed(ObservableValue<? extends Patient> observableValue, Patient patient, Patient storedpatient) {
                if(storedpatient != null){
                    idOfSlectedPatient = storedpatient.getPatientId();

                }
                else {
                    System.out.println("Selected patiente doesn't have an id");
                }
            }
        });
        System.out.println("i was here 3");
        patientsTable.setItems(filteredList);
        System.out.println("i was here 4");



    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void update(ActionEvent event) throws IOException {
        System.out.println("I am here!!");
        if(idOfSlectedPatient!=0){
            Patient Patient1 = Patient.getPatient(idOfSlectedPatient);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/updatePatient.fxml"));
            root=loader.load();
            UpdatePatient updatePatient = loader.getController();
            updatePatient.setPatient(Patient1);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {;
                try {
                    initialize();
                }catch (IOException exception){
                    System.out.println(exception);
                }
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a Patient to update!!");
            alert.setTitle("no Patient selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }

    ////////don't type anything under here plaese this must be the button////////////////////////

    @FXML
    private void doctorClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Doctors doctors = loader.getController();
        if (doctor==null){
            System.out.println("doctor is null");
            doctors.setUser(user);
        }else {
            System.out.println("user is null");
            doctors.setDoctor(doctor);
        }
        stage.setScene(scene);


    }


    public void patientClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Patent.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Patients patients= loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            patients.setUser(user);
        }else {
            System.out.println("doctor is null");
            patients.setDoctor(doctor);
        }
        stage.setScene(scene);

    }

    public void staff(MouseEvent event) throws IOException {
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Staff.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Staff staff = loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            staff.setUser(user);
        }else {
            System.out.println("doctor is null");
            staff.setDoctor(doctor);
        }
        stage.setScene(scene);

*/
    }

    public void appointment(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Appointments appointments = loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            appointments.setUser(user);
        }else {
            System.out.println("doctor is null");
            appointments.setDoctor(doctor);
        }
        stage.setScene(scene);



    }

    public void inventory(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Inventory.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1=new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Inventory inventory=loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            inventory.setUser(user);
        }else {
            System.out.println("doctor is null");
            inventory.setDoctor(doctor);
        }
        stage.setScene(scene);

    }

    public void makeAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hms/make-Appointment.fxml"));
        Parent root = loader.load();
        MakeAppointment makeAppointment = loader.getController();
        makeAppointment.setPatient(Patient.getPatient(idOfSlectedPatient), doctor);
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-Make-Appointment");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            try {
                initialize();
                System.out.println("closed!");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage1.show();

    }
}
