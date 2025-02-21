package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.hms.classes.Address;
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
    ComboBox<String> address;
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
    CheckBox ramcos;
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
    Button add11;
    @FXML
    ComboBox<String>diagnosisC;
    @FXML
            CheckBox gyna;
    @FXML
            CheckBox hyper;
    @FXML
            CheckBox abd;
    @FXML
            CheckBox psychology;
    @FXML
            CheckBox nutritionist;
    @FXML
            CheckBox physiotherapy;
    @FXML
            TextField otherT;
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
        String daddress= address.getValue();
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
                address.setValue(patient.getAddress());
                d.setText(String.valueOf(patient.getdB()));
                m.setText(String.valueOf(patient.getmB()));
                y.setText(String.valueOf(patient.getyB()));
                medical.setText(String.valueOf(patient.isMedicalDay()));
                phone.setText(String.valueOf(patient.getPhoneNumber()));
                diagnosisC.setValue(patient.getCause());
                String nigga = String.valueOf(patient.getHeight());
                System.out.println(nigga);
                medical.setSelected(patient.isMedicalDay());
                ramcos.setSelected(patient.isExists());
                height.setText(nigga);

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
            String nigga = (gyna.isSelected() ? "Gyna, " : "") +
                    (hyper.isSelected() ? "Hypertension, " : "") +
                    (physiotherapy.isSelected() ? "Physiotherapy, " : "") +
                    (abd.isSelected() ? "Abdomin Pain, " : "") +
                    (nutritionist.isSelected() ? "Nutritionist, " : "") +
                    (psychology.isSelected() ? "Psychology, " : "")+
                    otherT.getText();

// Remove the trailing comma and space if any
            if (nigga.endsWith(", ")) {
                nigga = nigga.substring(0, nigga.length() - 2);
            }
            patient = new Patient( Integer.parseInt(did),dname,dsector, daddress, doctor.getName(),Double.parseDouble(height.getText()),year, month, day,Integer.parseInt(d.getText()), Integer.parseInt(m.getText()),Integer.parseInt(y.getText()),1,lastVisit,medical.isSelected(),nigga,ramcos.isSelected());

            patients.add(patient);
            Patient.addPatient(new ArrayList<>(patients));
            if(wannaUpdate){
                succsess.setText("Patient updated successfully ");
            }
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            address.setValue("");
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
        address.setItems(Address.getAllItems());


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
            if (!t1.matches("-?\\d*(\\.\\d*)?")) {
                height.setText(s);
            }

        }));
    }

    public void add(ActionEvent event) throws IOException {
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/addDiagnosis.fxml"));
        Parent root = window.load();
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-diagnosis");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            initialize();
        });
        stage1.show();
    }
    public void add1(ActionEvent event) throws IOException {
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/add_address.fxml"));
        Parent root = window.load();
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-diagnosis");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            initialize();
        });
        stage1.show();
    }
}
