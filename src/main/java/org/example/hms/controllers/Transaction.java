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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.hms.classes.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Transaction {


    @FXML
    TextField yy;
    @FXML
    TextField mm;
    @FXML
    TextField dd;
    @FXML
    TextField tyy;
    @FXML
    TextField tmm;
    @FXML
    TextField tdd;
    @FXML
    Label n;
    @FXML
    TextField searchField;
    @FXML
    TextField searchField1;
    @FXML
    TextField searchField2;
    @FXML
    TableColumn<Transaction, String> medicen_name;
    @FXML
    TableColumn<Transactions, Double> quantity;
    @FXML
    TableColumn<Transactions, String> doctor_name;
    @FXML
    TableColumn<Transactions, String> patient_name;
    @FXML
    TableView<Transactions> transactions;
    @FXML
    TableColumn<Transactions, String> date;
    double num=0;

    List<Transactions> transactions1= Transactions.getAllTransactions();
    ObservableList<Transactions> transactionsList= FXCollections.observableArrayList(transactions1);
    FilteredList<Transactions> filteredList=new FilteredList<>(transactionsList, d -> true);



    Parent root;
    Stage stage;
    User user;
    Doctor doctor;
    public Transaction(){}
    public void setDoctor(Doctor doctor){
        this.doctor=doctor;

    }



    public void initialize(){
        n.setText(String.valueOf(filteredList.size()));
        yy.setText("2025");
        tyy.setText("2025");
        mm.setText("1");
        dd.setText("1");
        tmm.setText("12");
        tdd.setText("31");

        medicen_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        doctor_name.setCellValueFactory(new PropertyValueFactory<>("d_name"));
        patient_name.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

         transactions1= Transactions.getAllTransactions();
         transactionsList= FXCollections.observableArrayList(transactions1);
         filteredList=new FilteredList<>(transactionsList, d -> true);




        /*transactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doctor>() {
            @Override
            public void changed(ObservableValue<? extends Doctor> observableValue, Doctor doctor, Doctor storedDoctor) {
                if(storedDoctor != null){
                    idOfSlectedDoctor = storedDoctor.getId();

                }
                else {
                    System.out.println("Selected doctor doesn't have an id");
                }
            }
        });*/

        transactions.setItems(filteredList);
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

    @FXML
    private void s(ActionEvent event) throws IOException {
        num=0;

        String newValue = searchField.getText();
        String newValue2 = searchField1.getText();

        // Combine predicates to filter the list once
        filteredList.setPredicate(transactionsl -> {
            // Check the first search field
            boolean matchesFirstField = (newValue == null || newValue.isEmpty()) ||
                    transactionsl.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                    transactionsl.getName().toLowerCase().contains(newValue.toLowerCase());

            // Check the second search field
            boolean matchesSecondField = (newValue2 == null || newValue2.isEmpty()) ||
                    transactionsl.getPatient_name().contains(newValue2) ;

            boolean medpat = (newValue2 == null || newValue2.isEmpty()) ||
                    transactionsl.getD_name().contains(newValue2) ;

            LocalDate fromDate = parseDate(yy.getText(), mm.getText(), dd.getText()); // Input range start
            LocalDate toDate = parseDate(tyy.getText(), tmm.getText(), tdd.getText());         // Input range end

            // Get the transactionsl's date using getters
            LocalDate patientDate = getPatientDate(transactionsl);

            // Check if the transactionsl's date is within range
            boolean matchesDateRange = true; // Default to true if no date filtering is needed
            if (fromDate != null && toDate != null && patientDate != null) {
                matchesDateRange = !patientDate.isBefore(fromDate) && !patientDate.isAfter(toDate);
            } else if (fromDate != null && patientDate != null) {
                matchesDateRange = !patientDate.isBefore(fromDate);
            } else if (toDate != null && patientDate != null) {
                matchesDateRange = !patientDate.isAfter(toDate);
            }

            // Combine all conditions
            return matchesFirstField && matchesSecondField && matchesDateRange  && medpat;
        });

        // Update the table view
        transactions.setItems(filteredList);
        for (Transactions t : filteredList) {
            num += t.getQuantity();
        }
        n.setText(String.valueOf(num));
    }
    private LocalDate getPatientDate(Transactions patient) {
        try {
            int year = patient.getYy(); // Get the year
            int month = patient.getMm(); // Get the month
            int day = patient.getDd(); // Get the day

            // Only construct a date if all parts are valid
            if (year > 0 && month > 0 && day > 0) {
                return LocalDate.of(year, month, day);
            }
        } catch (Exception e) {
            System.err.println("Error getting patient date: " + e.getMessage());
        }
        return null; // Return null if date is incomplete
    }


    private LocalDate parseDate(String year, String month, String day) {
        try {
            int y = (year != null && !year.isEmpty()) ? Integer.parseInt(year) : 0;
            int m = (month != null && !month.isEmpty()) ? Integer.parseInt(month) : 1; // Default to January
            int d = (day != null && !day.isEmpty()) ? Integer.parseInt(day) : 1;     // Default to 1st of the month
            if (y > 0) {
                return LocalDate.of(y, m, d);
            }
        } catch (Exception e) {
            System.err.println("Invalid date input: " + e.getMessage());
        }
        return null;
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
            System.out.println("doctor is null");
            doctors.setUser(user);
        }else {
            System.out.println("user is null");
            doctors.setDoctor(doctor);
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

    @FXML
    private void blankClicked(MouseEvent event){
        searchField.getParent().requestFocus();

    }
}
