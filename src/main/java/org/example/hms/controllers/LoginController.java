package org.example.hms.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

import java.io.IOException;

public class LoginController {
    private Stage stage;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label tilte;
    @FXML
    private TextField UTF;
    @FXML
    private PasswordField PTF;
    @FXML
    private Button loginButton;
    @FXML
    private Label error;
    private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() throws IOException {

        UTF.setOnAction(e -> PTF.requestFocus());
        PTF.setOnAction(e -> loginButton.fire());
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws IOException {
        if(User.doesUsernameExists(UTF.getText())){
            System.out.println("this is a user");
            User user = User.getUser(UTF.getText());

            if(user == null || !(user.getPassword().equals(PTF.getText())) || user.getUserName().isEmpty()|| user.getUserName().isBlank()){
                error.setVisible(true);
            }
            else{

                System.out.println("trying to login");
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
                root=loader.load();
                Doctors doctors=loader.getController();
                doctors.setUser(User.getUser(UTF.getText()));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("HMS-Main");
                stage.show();
            }
        }else {
            System.out.println("this is a doctor!!");
            Doctor doctor= Doctor.getDoctor(UTF.getText());
            if(doctor == null || !(doctor.getPassword().equals(PTF.getText())) || doctor.getUserName().isEmpty()|| doctor.getUserName().isBlank()){
                error.setVisible(true);
            }
            else{

                System.out.println("trying to login");
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
                root=loader.load();
                Doctors doctors=loader.getController();
                    doctors.setDoctor(Doctor.getDoctor(UTF.getText()));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("HMS-Main");
                stage.show();
            }

        }




    }

}
