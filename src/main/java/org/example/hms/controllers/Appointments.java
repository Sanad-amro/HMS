package org.example.hms.controllers;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.hms.classes.*;

import java.io.IOException;
import java.util.*;

public class Appointments {
    int idOfSelectedSession =0;
    Parent root;
    Stage stage;
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
    ////////////////////////////////////  pleas don't touch the upper part please ////////////////// ////////////////// ////////////////// ///////////////
    @FXML
    TableView<Session> appTable;
    @FXML
    TableColumn<Session, Integer> id ;
    @FXML
    TableColumn<Session, String> name;
    @FXML
    TableColumn<Session, String> diagnosis;
    @FXML
    TableColumn<Session, String> created_at;
    @FXML
    TableColumn<Session, String> added_by;

    @FXML
    TextField searchField;
    @FXML
    TextField searchField2;
    @FXML

    private Map<Integer, String> patientNamesMap = new HashMap<>();



    public void blankClicked(MouseEvent event) {
        searchField.getParent().requestFocus();

    }

    public void hideButtons(MouseEvent event) {


    }

    public void showButtons(MouseEvent event) {

    }



    public void close(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void Logout(MouseEvent event) {
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

    public void initialize() throws IOException {

        searchField.clear();
        searchField.getParent().requestFocus();
        id.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        name.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        added_by.setCellValueFactory(new PropertyValueFactory<>("addedBy"));


        ArrayList<Patient> patients= Patient.getAllPatients();
        for (Patient patient : patients) {
            patientNamesMap.put(patient.getPatientId(), patient.getName().toLowerCase());
        }
        List<Session> sessions= Session.getAllSessions();
        ObservableList<Session> sessionObservableList= FXCollections.observableArrayList(sessions);
        FilteredList<Session> filteredList=new FilteredList<>(sessionObservableList, d -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(session -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();
                String patientName = patientNamesMap.get(session.getPatientId());


                return (patientName != null && patientName.contains(lowerFilter)) ||
                        String.valueOf(session.getPatientId()).contains(newValue);
            });
        });
        boolean nigga=true;
        searchField2.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(session -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                for (Patient patient : patients) {
                    if (patient.getName().toLowerCase().contains(lowerFilter)){
                        return true;

                    }
                } return false;
//                if(String.valueOf(session.getPatientId()).contains(newValue))
//                    return true;
//                else return false;
            });
        });

        appTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Session>() {
            @Override
            public void changed(ObservableValue<? extends Session> observableValue, Session session, Session storedSession) {
                if(storedSession != null){
                    idOfSelectedSession = storedSession.getSessionId();

                }
                else {
                    System.out.println("Selected Appointment doesn't have an id");
                }
            }
        });

        appTable.setItems(filteredList);

    }

    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this appointment!");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Appointment.deleteAppointment(idOfSelectedSession);
            initialize();
        }
    }

    public void update(ActionEvent event) {
    }

    public void createBill(ActionEvent event) throws IOException {
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/addBill.fxml"));
        Parent root = window.load();
        AddBill addBill=window.getController();
        addBill.setAppointment(Appointment.getAppointment(idOfSelectedSession));
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-bill");
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


    //////////////////////don't touch the upper part its important please don't okayyyyyyyy///////////




    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    ////////don't type anything under here plaese this must be the button////////////////////////

    @FXML
    private void doctorClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Doctors doctors = loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            doctors.setUser(user);
        }else {
            System.out.println("doctor is null");
            doctors.setDoctor(doctor);
        }
        stage.close();
        Stage stage1=new Stage();
        stage1.setTitle("HMS-Main-Doctors");
        stage1.setScene(scene);
        stage1.show();

    }


    public void patientClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Patent.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Patients patients= loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            patients.setUser(user);
        }else {
            System.out.println("doctor is null");
            patients.setDoctor(doctor);
        }
        stage.close();
        Stage stage1=new Stage();
        stage1.setTitle("HMS-Main-Patients");
        stage1.setScene(scene);
        stage1.show();
    }

    public void staff(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Staff.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Staff staff = loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            staff.setUser(user);
        }else {
            System.out.println("doctor is null");
            staff.setDoctor(doctor);
        }
        stage.close();
        Stage stage1=new Stage();
        stage1.setTitle("HMS-Main-Staff");
        stage1.setScene(scene);
        stage1.show();

    }

    public void appointment(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Appointments appointments = loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            appointments.setUser(user);
        }else {
            System.out.println("doctor is null");
            appointments.setDoctor(doctor);
        }
        stage.close();
        Stage stage1=new Stage();
        stage1.setTitle("HMS-Main-Appointments.fxml");
        stage1.setScene(scene);
        stage1.show();


    }

    public void inventory(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Inventory.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1=new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Inventory inventory=loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            inventory.setUser(user);
        }else {
            System.out.println("doctor is null");
            inventory.setDoctor(doctor);
        }
        stage.close();
        stage1.setTitle("HMS-Main-Inventory");
        stage1.setScene(scene);
        stage1.show();
    }


    
}
