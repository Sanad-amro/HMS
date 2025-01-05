package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Diagnosis;

import java.util.Optional;

public class AddDiagnosis {
    Stage stage;

    @FXML
    TextField AddDiagnosis;

    public void add(ActionEvent actionEvent) {
        boolean addIt=true;
        if(AddDiagnosis.getText().isEmpty()){
            addIt=false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please fill the text field first!!");
            alert.setTitle("Close alert");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Diagnosis diagnosis = new Diagnosis(AddDiagnosis.getText());
            Diagnosis.addDiagnosis(diagnosis);
        }
        
    }

    public void close(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
