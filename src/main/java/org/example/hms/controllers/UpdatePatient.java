package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hms.classes.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatePatient {
    @FXML
    TextField name;
    @FXML
    TextField id ;
    @FXML
    TextField address;
    @FXML
    TextField email;
    @FXML
    TextField phone;
    @FXML
    TextField height;
    @FXML
    TextField age;
    @FXML
    Label fields_fill;
    @FXML
    Label pass_match;
    @FXML
    Label succsess;
    @FXML
    Button add;
    @FXML
    TextArea medical;

    Stage stage;
    public UpdatePatient(){}
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        populateFields();
    }
    public void update(ActionEvent event) throws IOException {
        boolean updateHim =true;

        if(!email.getText().isEmpty()){
            if(!email.getText().contains("@") || !email.getText().contains(".")){
                pass_match.setText("Enter a valid email address!!");
                succsess.setVisible(false);
                pass_match.setVisible(true);
                fields_fill.setVisible(false);
                updateHim =false;
            }
        }

        if((name.getText().isEmpty() || id.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || phone.getText().isEmpty() || age.getText().isEmpty() || medical.getText().isEmpty())){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            updateHim =false;
        }
        if(updateHim) {
            String dname= name.getText();
            String did= id.getText();
            String daddress= address.getText();
            String demail= email.getText();
            String dsector= phone.getText();

            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Patient> patients = new ArrayList<>();
            Patient patient1;
            patient1 = new Patient( Integer.parseInt(did),dname,dsector, daddress, Integer.parseInt(age.getText()), patient.getAddedBy(), Double.parseDouble(height.getText()));

            patients.add(patient1);
            Patient.UpdatePatientInfo(new ArrayList<>(patients));
            succsess.setText("Patient updated successfully ");
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            email.setText("");
            address.setText("");
            phone.setText("");
            age.setText("");
            medical.setText("");

        }


    }
    public void populateFields(){
        System.out.println(patient.getName());
        name.setText(patient.getName());
        id.setText(String.valueOf(patient.getPatientId()));
        address.setText(patient.getAddress());
        age.setText(String.valueOf(patient.getAge()));



    }

    public void close(ActionEvent event){

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void initialize(){
        height.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                height.setText(oldValue);

            }
        });
    }
}
