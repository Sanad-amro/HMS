package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hms.classes.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUser {
    Stage stage = new Stage();
    private User user;

    @FXML
    TextField name;
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



    public User getUser() {
        return user;
    }



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
    @FXML
    boolean updateHim = true;
    public void User(){}

    public void setUser(User User){
        this.user =User;
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
        if((name.getText().isEmpty() || id.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || sector.getValue().isEmpty() || speciality.getText().isEmpty() || Username.getText().isEmpty() || Password.getText().isEmpty())){
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
            String dsector= sector.getValue();
            String dspeciality = speciality.getText();
            String dUsername= Username.getText();
            String dPassword= Password.getText();
            String dconfP= confP.getText();
            fields_fill.setVisible(false);
            pass_match.setVisible(false);
            List<User> Users = new ArrayList<>();
            List<Integer> noWork = new ArrayList<>();
            noWork.addAll(List.of(0,0,0,0));
            User user1 = new User(dname, Integer.parseInt(did), demail, daddress, true, false, dsector, false, true, false, dspeciality, dUsername, dPassword, "User",noWork, noWork,noWork,noWork,noWork,noWork,noWork);
            Users.add(user1);
            User.UpdateUserInfo(new ArrayList<>(Users));
            succsess.setText("User updated successfully ");
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
    public void initialize(){

        role.getItems().addAll("Doctor","Nurse", "Reception", "Other");
        sector.getItems().addAll("Cardiology", "Orthopedics", "Neurology", "Pediatrics", "General Surgery", "Dermatology", "Radiology", "Psychiatry", "Emergency Medicine", "Internal Medicine", "Anesthesiology", "Ophthalmology", "Oncology", "Obstetrics & Gynecology", "Urology", "Endocrinology", "Nephrology", "Hematology", "Gastroenterology", "Pulmonology");
    }
    private void populateFields(){
        name.setText(user.getName());
        id.setText(String.valueOf(user.getId()));
        email.setText(user.getEmail());
        address.setText(user.getAddress());
        sector.setValue(user.getSector());
        speciality.setText(user.getSpeciality());
        Username.setText(user.getUsername());
        Password.setText(user.getPassword());
        confP.setText("");


    }
}
