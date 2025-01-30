package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hms.classes.Diagnosis;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.Patient;

import java.io.IOException;
import java.time.LocalDate;
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
    TextField addedBy;

    @FXML
    CheckBox medical;
    @FXML
    TextField d;
    @FXML
    TextField m;
    @FXML
    TextField y;

    @FXML
    TextField phone;
    @FXML
    TextField height;
    @FXML
    Label fields_fill;
    @FXML
    Label pass_match;
    @FXML
    Label succsess;
    @FXML
    Button add;
    @FXML
    ComboBox<String>diagnosisC;
    boolean wannaUpdate=false;
    boolean addhim=true;
    Doctor doctor;
    public AddPatient(){}
    public void setDoctor(Doctor doctor){this.doctor=doctor; nigga();}
    private void nigga(){

        addedBy.setText(doctor.getName());
    }

    @FXML
    public void add_doctor() throws IOException {
        String dname= name.getText();
        String did= id.getText();
        String daddress= address.getText();
        String dsector= phone.getText();

        succsess.setText("Patient added successfully!");
        addhim=true;


        if((dname.isEmpty() || did.isEmpty() || daddress.isEmpty() || dsector.isEmpty() || y.getText().isEmpty() || m.getText().isEmpty() || d.getText().isEmpty())){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            addhim=false;
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
                id.setText(String.valueOf(patient.getPatientId()));
                address.setText(patient.getAddress());
                d.setText(String.valueOf(patient.getdB()));
                m.setText(String.valueOf(patient.getmB()));
                y.setText(String.valueOf(patient.getyB()));
                medical.setText(String.valueOf(patient.isMedicalDay()));
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

            int day= LocalDate.now().getDayOfMonth();
            int month= LocalDate.now().getMonthValue();
            int year=LocalDate.now().getYear();
            String lastVisit = String.valueOf(day)+'/' + String.valueOf(month) + '/' +String.valueOf(year);
            patient = new Patient( Integer.parseInt(did),dname,dsector, daddress, doctor.getName(),Double.parseDouble(height.getText()),year, month, day,Integer.parseInt(d.getText()), Integer.parseInt(m.getText()),Integer.parseInt(y.getText()),1,lastVisit,medical.isSelected(),diagnosisC.getValue());

            patients.add(patient);
            Patient.addPatient(new ArrayList<>(patients));
            if(wannaUpdate){
                succsess.setText("Patient updated successfully ");
            }
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            address.setText("");
            phone.setText("");
            y.setText("");
            m.setText("");
            d.setText("");
            medical.setSelected(false);
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
        diagnosisC.setItems(Diagnosis.getAllItems());

        name.setOnAction(e -> id.requestFocus());
        id.setOnAction(e -> address.requestFocus());
        address.setOnAction(e -> phone.requestFocus());
        phone.setOnAction(e -> height.requestFocus());
        height.setOnAction(event -> y.requestFocus());
        y.setOnAction(event -> m.requestFocus());
        m.setOnAction(event -> d.requestFocus());



        id.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                id.setText(s);
            }

        }));
        y.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                y.setText(s);
            }

        }));
        m.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                m.setText(s);
            }

        }));
        d.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                d.setText(s);
            }

        }));
        phone.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                phone.setText(s);
            }

        }));

        height.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                height.setText(s);
            }

        }));
    }

}
