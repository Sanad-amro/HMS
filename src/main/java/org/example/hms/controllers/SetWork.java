package org.example.hms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetWork {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private CheckBox saturdayBox;
    @FXML
    private CheckBox sundayBox;
    @FXML
    private CheckBox mondayBox;
    @FXML
    private CheckBox tuesdayBox;
    @FXML
    private CheckBox wednesdayBox;
    @FXML
    private CheckBox thursdayBox;
    @FXML
    private CheckBox fridayBox;
    @FXML
    private CheckBox saveForEachDay;
    @FXML
    private Spinner<Integer> spinnerSH;
    @FXML
    private Spinner<Integer> spinnerSM;
    @FXML
    private Spinner<Integer> spinnerFH;
    @FXML
    private Spinner<Integer> spinnerFM;
    @FXML
    private Spinner<Integer> spinnerSH2;
    @FXML
    private Spinner<Integer> spinnerSM2;
    @FXML
    private Spinner<Integer> spinnerFH2;
    @FXML
    private Spinner<Integer> spinnerFM2;
    @FXML
    private ComboBox<String> dayComBox;
    @FXML
    private Label hours;
    @FXML
    private Label minuets;
    @FXML
    private Label hours2;
    @FXML
    private Label minuets2;
    @FXML
    private Label to;
    @FXML
    private Label from;
    @FXML
    Label success;


    private List<Integer> alldays = new ArrayList<>();
    private List<Integer> saturday = new ArrayList<>();
    private List<Integer> sunday = new ArrayList<>();
    private List<Integer> monday = new ArrayList<>();
    private List<Integer> tuesday = new ArrayList<>();
    private List<Integer> wednesday = new ArrayList<>();
    private List<Integer> thursday = new ArrayList<>();
    private List<Integer> friday = new ArrayList<>();



    public SetWork(){}

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        populateFields();

    }

    private Doctor doctor;
    public SetWork(Doctor doctor){
        this.doctor=doctor;
    }

    @FXML
    private void initialize(){
        saturday.addAll(List.of(0,0,0,0));
        monday.addAll(List.of(0,0,0,0));
        sunday.addAll(List.of(0,0,0,0));
        tuesday.addAll(List.of(0,0,0,0));
        wednesday.addAll(List.of(0,0,0,0));
        thursday.addAll(List.of(0,0,0,0));
        friday.addAll(List.of(0,0,0,0));


        spinnerFH2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerSH2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerSH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerFH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerSM2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));
        spinnerSM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));
        spinnerFM2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));
        spinnerFM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));

        spinnerFH2.setEditable(true);
        spinnerSH2.setEditable(true);
        spinnerSH.setEditable(true);
        spinnerFH.setEditable(true);
        spinnerSM2.setEditable(true);
        spinnerSM.setEditable(true);
        spinnerFM2.setEditable(true);
        spinnerFM.setEditable(true);

        TextField textField1 = spinnerSH.getEditor();
        TextField textField2 = spinnerSM.getEditor();
        TextField textField3 = spinnerFH.getEditor();
        TextField textField4 = spinnerFM.getEditor();
        TextField textField5 = spinnerSH2.getEditor();
        TextField textField6 = spinnerSM2.getEditor();
        TextField textField7 = spinnerFH2.getEditor();
        TextField textField8 = spinnerFM2.getEditor();

         textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
        textField8.setText("");

        spinnerSH.setOnKeyPressed(e-> spinnerSM.requestFocus());
        spinnerSM.setOnKeyPressed(e-> spinnerFH.requestFocus());
        spinnerFH.setOnKeyPressed(e-> spinnerFM.requestFocus());
        spinnerSH2.setOnKeyPressed(e-> spinnerSM2.requestFocus());
        spinnerSM2.setOnKeyPressed(e-> spinnerFH2.requestFocus());
        spinnerFH2.setOnKeyPressed(e->spinnerFM2.requestFocus());


        textField1.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField2.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField3.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField4.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField5.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField6.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField7.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        textField8.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });


        dayComBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!spinnerSH2.getValue().toString().isBlank() && !spinnerSM2.getValue().toString().isBlank() && !spinnerFH2.getValue().toString().isBlank() && !spinnerFM2.getValue().toString().isBlank() && oldValue!=null){
                System.out.println(oldValue.toString());
                if(oldValue.toString().equals("saturday")){
                    saturday.clear();
                    saturday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("sunday")){
                    sunday.clear();
                    sunday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("monday")){
                    monday.clear();
                    monday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("tuesday")){
                    tuesday.clear();
                    tuesday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("wednesday")){
                    wednesday.clear();
                    wednesday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("thursday")){
                    thursday.clear();
                    thursday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }
                if(oldValue.toString().equals("friday")){
                    friday.clear();
                    friday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
                }

            }

        });


        dayComBox.getItems().addAll("saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday");
        dayComBox.setValue("saturday");
        saveForEachDay.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                dayComBox.setDisable(false);
                to.setDisable(false);
                from.setDisable(false);
                hours.setDisable(false);
                hours2.setDisable(false);
                minuets.setDisable(false);
                minuets2.setDisable(false);
                spinnerFH2.setDisable(false);
                spinnerSH2.setDisable(false);
                spinnerFM2.setDisable(false);
                spinnerSM2.setDisable(false);
            }
            else {
                dayComBox.setDisable(true);
                to.setDisable(true);
                from.setDisable(true);
                hours.setDisable(true);
                hours2.setDisable(true);
                minuets.setDisable(true);
                minuets2.setDisable(true);
                spinnerFH2.setDisable(true);
                spinnerSH2.setDisable(true);
                spinnerFM2.setDisable(true);
                spinnerSM2.setDisable(true);
            }
        });
        dayComBox.placeholderProperty().addListener(((observableValue, node, t1) ->{

        } ));
    }
    private void populateFields(){
        name.setText(doctor.getName());
        id.setText(String.valueOf(doctor.getId()));


    }
    @FXML
    public void dayChanged(ActionEvent event) {
    }
    public void daySetChosen(ActionEvent event){

    }
    @FXML
    public void close(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void set(ActionEvent event) throws IOException {

        List<Doctor> doctors=Doctor.getAllDoctors();
        alldays.addAll(List.of(spinnerSH.getValue(),spinnerSM.getValue(),spinnerFH.getValue(),spinnerFM.getValue()));


        if(!saveForEachDay.isSelected()){

            System.out.println("its selected!!");
            alldays.addAll(List.of(spinnerSH.getValue(),spinnerSM.getValue(),spinnerFH.getValue(),spinnerFM.getValue()));
            List<Integer> noWork =new ArrayList<>();
            noWork.addAll(List.of(0,0,0,0));
            Doctor.setWorkTime(doctor.getId(),saturdayBox.isSelected()? alldays:noWork,sundayBox.isSelected()? alldays:noWork,mondayBox.isSelected()? alldays:noWork,tuesdayBox.isSelected()? alldays:noWork,wednesdayBox.isSelected()? alldays:noWork,thursdayBox.isSelected()? alldays:noWork,fridayBox.isSelected()? alldays:noWork);

        }
        else{
            if (dayComBox.getValue().contains("sunday")){
                sunday.clear();
                sunday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("monday")){
                monday.clear();
                monday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("tuesday")){
                tuesday.clear();
                tuesday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("wednesday")){
                wednesday.clear();
                wednesday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("thursday")){
                thursday.clear();
                thursday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("friday")){
                friday.clear();
                friday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            if (dayComBox.getValue().contains("saturday")){
                saturday.clear();
                saturday.addAll(List.of(spinnerSH2.getValue(),spinnerSM2.getValue(),spinnerFH2.getValue(),spinnerFM2.getValue()));
            }
            Doctor.setWorkTime(doctor.getId(),saturday,sunday,monday,tuesday,wednesday,thursday,friday);
            success.setVisible(true);
        }

    }
}