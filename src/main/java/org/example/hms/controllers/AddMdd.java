package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Medecin;

import java.util.Optional;

public class AddMdd {
    Stage stage;

    @FXML
    TextField diagnosis;
    @FXML
    TextField Q;

    public void initialize(){
        Q.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                Q.setText(oldValue);


            }
        });
    }


    public void add(ActionEvent actionEvent) {
        boolean addIt=true;
        if(diagnosis.getText().isEmpty() || Q.getText().isEmpty()){
            addIt=false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please fill the text field first!!");
            alert.setTitle("Close alert");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {

            Medecin.add(diagnosis.getText(), Double.parseDouble(Q.getText()));

        }
        
    }

    public void close(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
