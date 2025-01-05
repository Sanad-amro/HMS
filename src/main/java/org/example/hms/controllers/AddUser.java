package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddUser {
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
    Label success;
    @FXML
    Button add;
    boolean wannaUpdate=false;
    boolean addHim=true;
    @FXML
    CheckBox apnmt;
    @FXML
    CheckBox inventory;
    @FXML
    CheckBox doctors;
    @FXML
    CheckBox patients;
    @FXML
    CheckBox staff;
    @FXML
    ComboBox<String> role;



    @FXML
    public void add_User() throws IOException {
        String dName= name.getText();
        String did= id.getText();
        String dAddress= this.address.getText();
        String dEmail= email.getText();
        String dSpeciality = speciality.getText();
        String dUsername= Username.getText();
        String dPassword= Password.getText();
        String dconfP= confP.getText();
        success.setText("User added successfully!");
        addHim=true;

        if(!(dPassword.equals(dconfP))){
            System.out.println("i was here");
            fields_fill.setText("password doesn't match");
            fields_fill.setVisible(true);
            success.setVisible(false);


            addHim=false;
        }
        if((dName.isEmpty() || did.isEmpty() || dAddress.isEmpty() || dEmail.isEmpty() || sector.getValue()==null || dSpeciality.isEmpty() || dUsername.isEmpty() || dPassword.isEmpty() || role.getValue()==null)){
            success.setVisible(false);
            pass_match.setVisible(false);
            fields_fill.setVisible(true);
            addHim=false;
        }
        if(!dEmail.contains("@") || !dEmail.contains(".") || dEmail.length()<8){
            fields_fill.setText("Enter a valid Email dAddress");
            fields_fill.setVisible(true);
            addHim=false;
        }
        if(User.doesUsernameExists(dUsername) && !wannaUpdate || (Doctor.doesUsernameExists(dUsername))){
            pass_match.setVisible(false);
            success.setVisible(false);
            fields_fill.setText("This Username already exists");
            fields_fill.setVisible(true);
            addHim=false;
        }
        if(User.doesIdExists(did) && !wannaUpdate){
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("id already exits");
            alert.setHeaderText("this id already exits, do you wanna update it's data?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get()==ButtonType.OK){
                add.setText("Update");
                User user=User.getUser(did);
                assert user != null;
                name.setText(user.getName());
                id.setText(String.valueOf(user.getId()));
                email.setText(user.getEmail());
                this.address.setText(user.getAddress());
                sector.setValue(user.getSector());
                speciality.setText(user.getSpeciality());
                Username.setText(user.getUsername());
                Password.setText(user.getPassword());
                confP.setText("");
                wannaUpdate=true;
            }
            addHim=false;
            fields_fill.setVisible(false);
        }

        if(addHim){
            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<Integer> noWork = new ArrayList<>(List.of(0, 0, 0, 0));
            List<User> users = new ArrayList<>();
            User User1 = new User(dName, did, dEmail, dAddress, apnmt.isSelected(), inventory.isSelected(), sector.getValue(), doctors.isSelected(), patients.isSelected(), staff.isSelected(), dSpeciality, dUsername, dPassword,role.getValue(),noWork, noWork,noWork,noWork,noWork,noWork,noWork);
            users.add(User1);
            if (wannaUpdate){
                User.UpdateUserInfo(new ArrayList<>(users));
            }else {
                User.addOrUpdateUser(new ArrayList<>(users));

            }
            System.out.println(User1.getId());
            if(wannaUpdate){
                success.setText("User updated successfully ");
            }
            success.setVisible(true);
            name.setText("");
            id.setText("");
            email.setText("");
            this.address.setText("");
            sector.setPromptText("Sector");
            speciality.setText("");
            Username.setText("");
            Password.setText("");
            confP.setText("");
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
        role.getItems().addAll("Doctor","Nurse", "Reception", "Other");
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
                add_User();
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
