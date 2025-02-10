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
    ComboBox<String> address;
    @FXML
    TextField email;
    @FXML
    TextField phone;
    @FXML
    TextField height;
    @FXML
    TextField y;
    @FXML
    TextField m;
    @FXML
    TextField d;

    @FXML
    Label fields_fill;
    @FXML
    Label pass_match;
    @FXML
    Label succsess;
    @FXML
    Button add;
    @FXML
    CheckBox medical;
    @FXML
    TextField date;
    @FXML
    ComboBox<String> diagnosisC;
    @FXML
    CheckBox ramcos;
    @FXML
    Button add11;

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



        if((name.getText().isEmpty() || id.getText().isEmpty() || address.getValue().isEmpty() ||  phone.getText().isEmpty() || y.getText().isEmpty() || m.getText().isEmpty() || d.getText().isEmpty())){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            updateHim =false;
        }
        if(updateHim) {
            String dname= name.getText();
            String did= id.getText();
            String daddress= address.getValue();
            String dsector= phone.getText();

            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Patient> patients = new ArrayList<>();
            Patient patient1;
            patient1 = new Patient( Integer.parseInt(did),dname,dsector, daddress, patient.getAddedBy(), Double.parseDouble(height.getText()),Integer.parseInt(d.getText()), Integer.parseInt(m.getText()),Integer.parseInt( y.getText()),patient.getN_visits(), medical.isSelected(),diagnosisC.getValue(),ramcos.isSelected() );
            patients.add(patient1);
            Patient.UpdatePatientInfo(new ArrayList<>(patients));
            succsess.setText("Patient updated successfully ");
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            address.setValue("");
            phone.setText("");
            y.setText("");
            m.setText("");
            d.setText("");
            medical.setSelected(false);
            medical.setText("");

        }


    }
    public void populateFields(){
        diagnosisC.setItems(Diagnosis.getAllItems());
        System.out.println(patient.getName());
        name.setText(patient.getName());
        id.setText(String.valueOf(patient.getPatientId()));
        address.setValue(patient.getAddress());
        y.setText(String.valueOf(patient.getyB()));
        m.setText(String.valueOf(patient.getmB()));
        d.setText(String.valueOf(patient.getdB()));
        medical.setSelected(patient.isMedicalDay());
        height.setText(String.valueOf(patient.getHeight()));
        phone.setText(String.valueOf(patient.getPhoneNumber()));
        date.setText(patient.getYy()+"/"+patient.getMm()+"/"+patient.getDd());
        diagnosisC.setValue(patient.getCause());
        ramcos.setSelected(patient.isExists());


    }

    public void close(ActionEvent event){

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void initialize(){
        address.setItems(Address.getAllItems());
        height.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                height.setText(oldValue);

            }
        });
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
