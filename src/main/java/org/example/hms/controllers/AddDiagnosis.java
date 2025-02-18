package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Diagnosis;

import java.util.Optional;

public class AddDiagnosis {
    Stage stage;

    @FXML
    TextField diagnosis;
    @FXML
    Label Success;

    public void add(ActionEvent actionEvent) {
        boolean addIt=true;
        if(diagnosis.getText().isEmpty()){
            addIt=false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please fill the text field first!!");
            alert.setTitle("Close alert");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {

            Diagnosis diagnosist = new Diagnosis(diagnosis.getText());
            Diagnosis.addDiagnosis(diagnosist);
            Success.setVisible(true);
        }
        
    }

    public void close(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
