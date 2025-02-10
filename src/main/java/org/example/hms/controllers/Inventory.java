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
import org.example.hms.classes.Doctor;
import org.example.hms.classes.Medecin;
import org.example.hms.classes.Patient;
import org.example.hms.classes.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Inventory {
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

    @FXML
    TableView<Medecin> inv;
    @FXML
    TableColumn<Medecin, String> nameC;
    @FXML
    TableColumn<Medecin, Double> QC;
    @FXML
    TableColumn<Medecin, Integer> id;
    @FXML
    TextField searchField;
    int idOfSlectedPatient =0;
    Parent root;

    Stage stage;

    public void initialize(){

        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        QC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        QC.setCellFactory(column -> new TableCell<Medecin, Double>() {
            @Override
            protected void updateItem(Double quantity, boolean empty) {
                super.updateItem(quantity, empty);

                if (empty || quantity == null) {
                    setText(null);
                    setStyle(""); // Clear styling
                } else {
                    setText(quantity.toString());

                    // Apply styling based on the quantity value
                    if (quantity <= 10) {
                        setStyle("-fx-text-fill: red;"); // Red for 10 or less
                    } else if (quantity <= 15) {
                        setStyle("-fx-text-fill: orange;"); // Orange for 15 or less
                    } else {
                        setStyle(""); // Default styling
                    }
                }
            }
        });
        List<Medecin> Medecins= Medecin.getAll();
        ObservableList<Medecin> MedecinsList= FXCollections.observableArrayList(Medecins);
        FilteredList<Medecin> filteredList=new FilteredList<>(MedecinsList, d -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(Medecin1 -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                if ( Medecin1.getName().toLowerCase().contains(lowerFilter) )
                    return true;
                else return false;
            });
        });

        inv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Medecin>() {
            @Override
            public void changed(ObservableValue<? extends Medecin> observableValue, Medecin Medecin, Medecin storedMedecin) {
                if(storedMedecin != null){
                    idOfSlectedPatient = storedMedecin.getId();
                    System.out.println("the id is: " + idOfSlectedPatient);
                }
                else {
                    System.out.println("Selected Medecine doesn't have an id");
                }
            }
        });
        System.out.println("i was here 3");
        inv.setItems(filteredList);
    }


    public void deleteMidicen(ActionEvent actionEvent) throws IOException {
        if(idOfSlectedPatient!=0){
            Medecin medecin = Medecin.getMedecinById(idOfSlectedPatient);
            System.out.println("the medecin id is: " + medecin.getId());
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/UpMed.fxml"));
            root = loader.load();
            UpMed upMed = loader.getController();
            upMed.setMedecin(medecin);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {;
                initialize();
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a Medicine to update!!");
            alert.setTitle("no Medicine selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }

    public void addMidicen(ActionEvent actionEvent) throws IOException {
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/addMed.fxml"));
        Parent root = window.load();
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-doctor");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            initialize();
            System.out.println("closed!");
        });
        stage1.show();
    }
    public void dec(ActionEvent actionEvent) throws IOException {
        if(idOfSlectedPatient!=0){
            Medecin medecin = Medecin.getMedecinById(idOfSlectedPatient);
            System.out.println("the medecin id is: " + medecin.getId());
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/decMed.fxml"));
            root = loader.load();
            DecMed decMed = loader.getController();
            decMed.setMedecin(medecin);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {;
                initialize();
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a Medicine to update!!");
            alert.setTitle("no Medicine selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }



    public void patientClicked(MouseEvent event) throws IOException {
        System.out.println(doctor.getName());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Patent.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Patients patients= loader.getController();
        if (doctor==null){
            System.out.println("this is a user ");
            patients.setUser(user);
        }else {
            System.out.println("this is a docotr");
            patients.setDoctor(doctor);
        }
        stage.setScene(scene);
    }

    public void staff(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Transactions.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Transaction transactions  = loader.getController();
        transactions.setDoctor(doctor);
        stage.setScene(scene);



    }

    public void appointment(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Appointments appointments = loader.getController();
        if (doctor==null){
            appointments.setUser(user);
        }else {
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
            inventory.setUser(user);
        }else {
            inventory.setDoctor(doctor);
        }
        stage.setScene(scene);

    }

    public void showButtons(MouseEvent mouseEvent) {
    }

    public void hideButtons(MouseEvent mouseEvent) {
    }

    public void Logout(MouseEvent mouseEvent) {
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

    @FXML
    private void doctorClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Doctors doctors = loader.getController();
        if (doctor==null){
            doctors.setUser(user);
        }else {
            doctors.setDoctor(doctor);
        }
        stage.setScene(scene);
    }
    @FXML
    private void blankClicked(MouseEvent event){
        searchField.getParent().requestFocus();

    }

    public void inc(ActionEvent event) throws IOException {
        if(idOfSlectedPatient!=0){
            Medecin medecin = Medecin.getMedecinById(idOfSlectedPatient);
            System.out.println("the medecin id is: " + medecin.getId());
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/increase.fxml"));
            root = loader.load();
            Increase decMed = loader.getController();
            decMed.setMedecin(medecin);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {;
                initialize();
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a Medicine to update!!");
            alert.setTitle("no Medicine selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }

    public void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete "+ Medecin.getMedecinById(idOfSlectedPatient).getName()+"?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Medecin.delete(idOfSlectedPatient);
            initialize();
        }
    }
}
