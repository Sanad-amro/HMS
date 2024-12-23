package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.Patient;
import org.example.hms.classes.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddPatient {
    public Stage stage;
    @FXML
    TextField name;
    @FXML
    TextField id ;
    @FXML
    TextField address;
    @FXML
    TextField email;
    @FXML
    TextArea medical;
    @FXML
    TextField age;
    @FXML
    ComboBox<String> sector;


    @FXML
    Label fields_fill;
    @FXML
    Label pass_match;
    @FXML
    Label succsess;
    @FXML
    Button add;
    boolean wannaUpdate=false;
    boolean addhim=true;



    @FXML
    public void add_doctor() throws IOException {
        String dname= name.getText();
        String did= id.getText();
        String daddress= address.getText();
        String demail= email.getText();
        String dsector=sector.getValue();

        succsess.setText("Patient added successfully!");
        addhim=true;


        if((dname.isEmpty() || did.isEmpty() || daddress.isEmpty() || dsector.isEmpty() || age.getText().isEmpty() || medical.getText().isEmpty())){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            addhim=false;
        }
        if(!email.getText().isEmpty()){
            System.out.println("i was here ");
            if(!demail.contains("@") || !demail.contains(".") || demail.length()<8){
                fields_fill.setText("Enter a valid Email address");
                fields_fill.setVisible(true);
                addhim=false;
            }

        }


        if(Patient.doesIdExists(Integer.parseInt(did)) && !wannaUpdate){
            System.out.println(wannaUpdate);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("id already exits");
            alert.setHeaderText("this id already exits, do you wanna update it's data?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get()==ButtonType.OK){
                add.setText("Update");
                Patient patient=Patient.getPatient((Integer.parseInt(did)));
                name.setText(patient.getName());
                id.setText(String.valueOf(patient.getId()));
                email.setText(patient.getEmail());
                address.setText(patient.getAddress());
                sector.setValue(patient.getSector());
                age.setText(String.valueOf(patient.getAge()));
                medical.setText(patient.getMedicalRecord());
                wannaUpdate=true;
            }
            addhim=false;
            fields_fill.setVisible(false);



        }
        if(addhim){
            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Patient> patients = new ArrayList<>();
            Patient patient;

            if (email.getText().isEmpty()){
                patient=new Patient(dname, Integer.parseInt(did), daddress, dsector,medical.getText(), Integer.parseInt(age.getText()) );

            }else {
                patient=new Patient(dname, Integer.parseInt(did),email.getText(), daddress, dsector,medical.getText(), Integer.parseInt(age.getText()) );
            }
            patients.add(patient);
            Patient.addOrUpdatePatient(new ArrayList<>(patients));
            if(wannaUpdate){
                succsess.setText("Patient updated successfully ");
            }
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            email.setText("");
            address.setText("");
            sector.setPromptText("Sector");
            age.setText("");
            medical.setText("");
            add.setText("Add");
        }
    }
    @FXML
    public void close(ActionEvent event){

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void initialize(){
        sector.getItems().addAll("Cardiology", "Orthopedics", "Neurology", "Pediatrics", "General Surgery", "Dermatology", "Radiology", "Psychiatry", "Emergency Medicine", "Internal Medicine", "Anesthesiology", "Ophthalmology", "Oncology", "Obstetrics & Gynecology", "Urology", "Endocrinology", "Nephrology", "Hematology", "Gastroenterology", "Pulmonology");
        name.setOnAction(e -> id.requestFocus());
        id.setOnAction(e -> address.requestFocus());
        address.setOnAction(e -> sector.requestFocus());
        sector.setOnAction(e -> email.requestFocus());
        email.setOnAction(e -> age.requestFocus());
        age.setOnAction(event -> medical.requestFocus());

        id.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                id.setText(s);
            }

        }));
        age.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                age.setText(s);
            }

        }));
    }

}
