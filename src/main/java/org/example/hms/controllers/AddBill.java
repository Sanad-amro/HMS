/*
package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hms.classes.Appointment;
import org.example.hms.classes.Bill;
import org.example.hms.classes.Doctor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBill {

    private Stage stage;

    public AddBill() {
    }

    private Appointment appointment;
    public AddBill(Appointment appointment){
        this.appointment=appointment;
    }
    @FXML
    TextField insuranceName;
    @FXML
    TextField amount;
    @FXML
    TextField insuranceDiscount;
    @FXML
    TextField billDescreption;
    @FXML
    TextField billAfterInsurance;
    @FXML
    Label fields_fill;
    @FXML
    Label error;
    @FXML
    Label succsess;

    public void initialize(){
        amount.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                amount.setText(oldValue);
                if(insuranceDiscount.getText() !=null)
                    billAfterInsurance.setText(String.valueOf(Double.parseDouble(amount.getText())-((Double.parseDouble(amount.getText()))*(Double.parseDouble(insuranceDiscount.getText())/100))));
            }
        });
        insuranceDiscount.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                insuranceDiscount.setText(oldValue);
                System.out.println("I was Here!");
                billAfterInsurance.setText(String.valueOf(Double.parseDouble(amount.getText()) - ((Double.parseDouble(amount.getText())) * (Double.parseDouble(insuranceDiscount.getText()) / 100))));


            }
        });

        amount.textProperty().addListener((observable, oldValue, newValue)->{
           if (insuranceDiscount.getText()!=null){
               billAfterInsurance.setText(amount.getText());
           }
           else {
               billAfterInsurance.setText(String.valueOf(Double.parseDouble(amount.getText())*(Double.parseDouble(insuranceDiscount.getText())/100)));
               succsess.setText("Added successfully!!");
               succsess.setVisible(true);
           }
        });
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void add(ActionEvent event) throws IOException {
        boolean addIt=true;
        if(amount.getText().isEmpty() || billDescreption.getText().isEmpty()){
            addIt=false;
            fields_fill.setVisible(true);
        }
        if(insuranceName.getText().isEmpty() && !insuranceDiscount.getText().isEmpty()){
            error.setText("if you wanna add discount you have to add the insurance name!!");
            fields_fill.setVisible(false);
            succsess.setVisible(false);
            error.setVisible(true);
            addIt=false;
        }
        if (!insuranceName.getText().isEmpty() && insuranceDiscount.getText().isEmpty()){
            error.setText("if you wanna add discount you have to add the insurance name!!");
            error.setVisible(true);
            succsess.setVisible(false);
            fields_fill.setVisible(false);
            addIt=false;
        }
        if (addIt){
            if(insuranceName.getText()==null){
                List<Bill> bill = new ArrayList<>();
                Bill bill1=new Bill(Double.parseDouble(amount.getText()),insuranceName.getText(),Double.parseDouble(insuranceDiscount.getText()),Double.parseDouble(billAfterInsurance.getText()),0, Double.parseDouble(billAfterInsurance.getText()),appointment.getPatientId(),appointment.getPatientName(),Bill.genarateId(),billDescreption.getText(),false);
                bill.add(bill1);
                Bill.addOrUpdateBill(new ArrayList<>(bill));
            }
            else {
                List<Bill> bill = new ArrayList<>();
                Bill bill1=new Bill(Double.parseDouble(amount.getText()),insuranceName.getText(),Double.parseDouble(insuranceDiscount.getText()),Double.parseDouble(billAfterInsurance.getText()),0, Double.parseDouble(billAfterInsurance.getText()),appointment.getPatientId(),appointment.getPatientName(),Bill.genarateId(),billDescreption.getText(),false);
                bill.add(bill1);
                Bill.addOrUpdateBill(new ArrayList<>(bill));

            }

        }

    }
    public void close(ActionEvent event){

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
*/
