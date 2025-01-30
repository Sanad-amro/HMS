package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddDoctor {
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
    ComboBox<String> sector;
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
    boolean wannaUpdate=false;
    boolean addhim=true;



    @FXML
    public void add_doctor() throws IOException {
        String dname= name.getText();
        String did= id.getText();
        String daddress= address.getText();
        String demail= email.getText();
        String dsector= sector.getValue();
        String dspeciality = speciality.getText();
        String dUsername= Username.getText();
        String dPassword= Password.getText();
        String dconfP= confP.getText();
        succsess.setText("Doctor added successfully!");
        addhim=true;

        if(!(dPassword.equals(dconfP))){
            System.out.println("i was here");
            fields_fill.setText("password doesn't match");
            fields_fill.setVisible(true);
            succsess.setVisible(false);


            addhim=false;
        }
        if((dname.isEmpty() || did.isEmpty() || dsector==null || daddress.isEmpty() || demail.isEmpty() || dsector.isEmpty() || dspeciality.isEmpty() || dUsername.isEmpty() || dPassword.isEmpty()) ){
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setText("Fill all the fields");
            fields_fill.setVisible(true);
            addhim=false;
        }
        if (dPassword.length()<8 || dUsername.length()<5) {
            succsess.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setText("Password<8 Or username<5 !!! ");
            fields_fill.setVisible(true);
            addhim=false;
        }
        if(!demail.contains("@") || !demail.contains(".") || demail.length()<8){
            fields_fill.setText("Enter a valid Email address");
            fields_fill.setVisible(true);
            addhim=false;
        }
        /*if(User.doesUsernameExists(dUsername) || (Doctor.doesUsernameExists(dUsername) && !wannaUpdate)){
            pass_match.setVisible(false);
            succsess.setVisible(false);
            fields_fill.setText("This Username already exists");
            fields_fill.setVisible(true);
            addhim=false;
        }*/
        if(Doctor.doesIdExists(did) && !wannaUpdate){
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("id already exits");
            alert.setHeaderText("this id already exits, do you wanna update it's data?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get()==ButtonType.OK){
                add.setText("Update");
                Doctor doctor=Doctor.getDoctorById(did);
                name.setText(doctor.getName());
                id.setText(String.valueOf(doctor.getId()));
                email.setText(doctor.getEmail());
                address.setText(doctor.getAddress());
                sector.setValue(doctor.getSector());
                speciality.setText(doctor.getSpeciality());
                Username.setText(doctor.getUsername());
                Password.setText(doctor.getPassword());
                confP.setText("");
                wannaUpdate=true;
            }
            addhim=false;
            fields_fill.setVisible(false);
        }

        if(addhim){
            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Integer> noWork = new ArrayList<>();
            noWork.addAll(List.of(0,0,0,0));
            List<Doctor> doctor = new ArrayList<>();
            Doctor doctor1 = new Doctor(dname, did, demail, daddress, true, false, dsector, false, true, false, dspeciality, dUsername, dPassword,"Doctor",noWork, noWork,noWork,noWork,noWork,noWork,noWork);
            doctor.add(doctor1);
            System.out.println(doctor1.getSpeciality());
            System.out.println(speciality.getText());
            if (wannaUpdate){
                Doctor.UpdateDoctorInfo(new ArrayList<>(doctor));
            }else {
                Doctor.addDoctor(new ArrayList<>(doctor));

            }
            System.out.println(doctor1.getId());
            if(wannaUpdate){
                succsess.setText("Doctor updated successfully ");
            }
            succsess.setVisible(true);
            name.setText("");
            id.setText("");
            email.setText("");
            address.setText("");
            sector.setPromptText("Sector");
            speciality.setText("");
            Username.setText("");
            Password.setText("");
            confP.setText("");
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
        email.setOnAction(e -> speciality.requestFocus());
        speciality.setOnAction(e -> Username.requestFocus());
        Username.setOnAction(e -> Password.requestFocus());
        Password.setOnAction(e -> confP.requestFocus());
        confP.setOnAction(e -> {
            try {
                add_doctor();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        id.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                id.setText(s);
            }

        }));
    }

}
