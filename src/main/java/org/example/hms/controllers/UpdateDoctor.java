package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDoctor {
    Stage stage = new Stage();
    private Doctor doctor;

    @FXML
    TextField name;
    @FXML
    TextField id ;
    @FXML
    TextField address;
    @FXML
    TextField email;
    @FXML
    TextField sector;
    @FXML
    TextField speciality;
    @FXML
    TextField Username;
    @FXML
    PasswordField Password;
    @FXML
    PasswordField confP;
    @FXML
    Label fields_fill;
    @FXML
    Label pass_match;
    @FXML
    Label succsess;
    @FXML
    Button add;
    @FXML
    boolean updateHim = true;
    public void Doctor(){}
    public void setDoctor(Doctor doctor){
        this.doctor=doctor;
        populateFields();
    }

    public void close(ActionEvent event){

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void update(ActionEvent event) throws IOException {
        updateHim =true;
        if(Password.getText().equals(confP.getText())){
            pass_match.setText("Passwords doesn't match!!");

            updateHim =false;
        }

        if(!email.getText().contains("@") || !email.getText().contains(".")){
            pass_match.setText("Enter a valid email address!!");
            succsess.setVisible(false);
            pass_match.setVisible(true);
            fields_fill.setVisible(false);
            updateHim =false;
        }
        if((name.getText().isEmpty() || id.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || sector.getText().isEmpty() || speciality.getText().isEmpty() || Username.getText().isEmpty() || Password.getText().isEmpty())){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            updateHim =false;
            System.out.println("i was here!!");
        }
        if(updateHim) {

            String dname= name.getText();
            String did= id.getText();
            String daddress= address.getText();
            String demail= email.getText();
            String dsector= sector.getText();
            String dspeciality = speciality.getText();
            String dUsername= Username.getText();
            String dPassword= Password.getText();
            String dconfP= confP.getText();
            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Doctor> doctors = new ArrayList<>();
            List<Integer> noWork= new ArrayList<>();
            noWork.addAll(List.of(0,0,0,0));
            Doctor doctor=new Doctor(dname, did , demail, daddress, true, false, dsector, false, true, false, dspeciality, dUsername, dPassword,"Doctor",noWork, noWork,noWork,noWork,noWork,noWork,noWork);

            doctors.add(doctor);
            Doctor.UpdateDoctorInfo(new ArrayList<>(doctors));
            succsess.setText("Doctor updated successfully ");
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            email.setText("");
            address.setText("");
            sector.setText("");
            speciality.setText("");
            Username.setText("");
            Password.setText("");
            confP.setText("");
        }


    }
    private void populateFields(){
        name.setText(doctor.getName());
        id.setText(String.valueOf(doctor.getId()));
        email.setText(doctor.getEmail());
        address.setText(doctor.getAddress());
        sector.setText(doctor.getSector());
        speciality.setText(doctor.getSpeciality());
        Username.setText(doctor.getUsername());
        Password.setText(doctor.getPassword());
        confP.setText("");
    }
}
