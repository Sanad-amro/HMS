package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Medecin;

import java.util.Optional;

public class Increase {

    @FXML
    TextField diagnosis;
    @FXML
    TextField Q;
    @FXML
    Label Success;
    Medecin medecin;

    public Increase() {

    }
    public Increase(Medecin medecin) {
        this.medecin = medecin;
        pup();
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
        pup();
    }

    public void pup() {
        diagnosis.setText(medecin.getName());
        Q.setText(String.valueOf(medecin.getQuantity()));

    }
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
            System.out.println("the id is: "  + medecin.getId());

            Medecin.decrementQuantity(medecin.getId(), Double.parseDouble(Q.getText()) *-1);
            Success.setText("You Added: "+ Q.getText()+ " To "+ medecin.getName());
            Success.setVisible(true);

        }
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
