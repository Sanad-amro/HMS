package org.example.hms.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.hms.classes.Appointment;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeAppointment {


    Patient patient;
    @FXML
    TextField oparation;
    @FXML
    TextField name;
    @FXML
    TextField id;
    @FXML
    TableView<Doctor> doctorsTable;
    @FXML
    TableColumn<Doctor, Integer> idC ;
    @FXML
    TableColumn<Doctor, String> nameC;

    @FXML
    TextField searchField;
    @FXML
    TableColumn<Doctor, String> isWorking;
    @FXML
    private Spinner<Integer> spinnerSH;
    @FXML
    private Spinner<Integer> spinnerSM;
    @FXML
    private Spinner<Integer> spinnerFH;
    @FXML
    private Spinner<Integer> spinnerFM;
    @FXML
    Label error;
    @FXML
    Label succes;
    @FXML
    ComboBox<String> dayComBox;


    private int idOfSelectedDoctor =0;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        pop();
    }
    private void pop(){
        name.setText(patient.getName());
        id.setText(String.valueOf(patient.getPatientId()));
    }

    public void initialize() throws IOException {
        dayComBox.getItems().addAll("saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday");
        searchField.clear();
        searchField.getParent().requestFocus();
        idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        isWorking.setCellValueFactory(data ->{
            if(data.getValue() !=null)
                return new SimpleStringProperty(data.getValue().isHeWorking(data.getValue().getId()) ? "Yes":"No");

            else return new SimpleStringProperty("No");
        });
        isWorking.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(String isWorking, boolean empty) {
                super.updateItem(isWorking, empty);

                if (empty || isWorking == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text(isWorking);
                    text.setFill("Yes".equals(isWorking) ? Color.GREEN : Color.RED);
                    setGraphic(text);
                }
            }
        });
        List<Doctor> doctors= Doctor.getAllDoctors();
        ObservableList<Doctor> doctorsList= FXCollections.observableArrayList(doctors);
        FilteredList<Doctor> filteredList=new FilteredList<>(doctorsList, d -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(doctor1 -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                if ( doctor1.getName().toLowerCase().contains(lowerFilter) ||  String.valueOf(doctor1.getId()).contains(newValue))
                    return true;
                else return false;
            });
        });

        doctorsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doctor>() {
            @Override
            public void changed(ObservableValue<? extends Doctor> observableValue, Doctor doctor, Doctor storedDoctor) {
                if(storedDoctor != null){
                    idOfSelectedDoctor = storedDoctor.getId();

                }
                else {
                    System.out.println("Selected doctore doesn't have an id");
                }
            }
        });

        doctorsTable.setItems(filteredList);


        spinnerSH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerFH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));
        spinnerSM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));
        spinnerFM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));

        spinnerSH.setEditable(true);
        spinnerFH.setEditable(true);
        spinnerSM.setEditable(true);
        spinnerFM.setEditable(true);

        TextField textField1 = spinnerSH.getEditor();
        TextField textField2 = spinnerSM.getEditor();
        TextField textField3 = spinnerFH.getEditor();
        TextField textField4 = spinnerFM.getEditor();


        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");


        spinnerSH.setOnKeyPressed(e-> spinnerSM.requestFocus());
        spinnerSM.setOnKeyPressed(e-> spinnerFH.requestFocus());
        spinnerFH.setOnKeyPressed(e-> spinnerFM.requestFocus());


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


    }
    public void set(ActionEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Doctor doctor=Doctor.getDoctor(idOfSelectedDoctor);
        boolean add=true;
        if (idOfSelectedDoctor==0 ||  dayComBox.getValue()==null || dayComBox.getValue().isEmpty()  || oparation.getText().isEmpty() ){
            error.setText("pleas fill all the required fields!!");
            error.setVisible(true);
            succes.setVisible(false);
            add=false;
        }
        List<Appointment> appointments= new ArrayList<>();
        Appointment appointment = new Appointment(doctor.getName(), patient.getName(), dayComBox.getValue(), oparation.getText(), doctor.getId(),patient.getPatientId(), spinnerSH.getValue(), spinnerSM.getValue(), spinnerFH.getValue(), spinnerFM.getValue());

       /* if(!Appointment.isTheDoctorAvailable(idOfSelectedDoctor,appointment)){
            error.setText("the doctor that time either doesn't work or already have an operation!!");
            error.setVisible(true);
            succes.setVisible(false);
            add=false;
        }*/
        if (add){
            appointments.add(appointment);
            Appointment.addOrUpdateAppointment(new ArrayList<>(appointments));
            error.setVisible(false);
            succes.setVisible(true);
            System.out.println("it was added successfully!! ");

        }

    }
    @FXML
    private void blankClicked(MouseEvent event){
        searchField.getParent().requestFocus();

    }
    public void close(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
