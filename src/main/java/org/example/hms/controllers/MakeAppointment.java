package org.example.hms.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hms.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeAppointment {


    Patient patient;
    Doctor doctor;


    @FXML
    TextField patient_id;
    @FXML
    TextField patient_name;
    @FXML
    TextField added_by ;
    @FXML
    TextField weightT;
    @FXML
    TextField blood_glucose;
    @FXML
    TextField fasting_blood_glucose;
    @FXML
    TextField random_blood_glucose;
    @FXML
    TextField hgbT;
    @FXML
    TextField heart_rate;
    @FXML
    TextField diastolic_blood_pressure;
    @FXML
    TextField systolic_blood_pressure;
    @FXML
    TextField bp1;
    @FXML
    TextField bp2;

    @FXML
    TextField search;
    @FXML
    TextField quantity;
    @FXML
    ComboBox<String> diagnosisC;
    @FXML
    ComboBox<String> cNote;
    @FXML
    TableView<Medecin> stock;
    @FXML
    TableView<Given> given;
    @FXML
    TextArea current_medications;
    @FXML
    TextArea notes;
    @FXML
    TableColumn<Medecin, String> sName;
    @FXML
    TableColumn<Medecin, Double> sQuantity;
    @FXML
    TableColumn<Given, String> gName;
    @FXML
    TableColumn<Given, Double> gQuantity;

    private int idOfSelectedMedecin =2;
    private String nameOfSelectedMedecin =null;

    ArrayList<Given> givens=new ArrayList<>();


    private String chief_complaint = "FREE";
    private String medical_history = "FREE";
    private String medical_and_surgical_history = "FREE";
    private String obstetric_history = "FREE";
    private String gynecological_history = "FREE";
    private String doctor_and_midwife_note = "FREE";
    private String nutritionist_note = "FREE";
    private String physiotherapist_note = "FREE";


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient,Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
        pop();
    }
    private void pop(){
        patient_name.setText(patient.getName());
        patient_id.setText(String.valueOf(patient.getPatientId()));
        added_by.setText(doctor.getName());
    }
////////////////////////////////////////////////////////////////////////////////initialize ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initialize() throws IOException {
        diagnosisC.setItems(Diagnosis.getAllItems());
        weightT.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                weightT.setText(s);
            }

        }));
        bp1.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                bp1.setText(s);
            }

        }));
        bp2.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                bp2.setText(s);
            }

        }));
        systolic_blood_pressure.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                systolic_blood_pressure.setText(s);
            }

        }));
        diastolic_blood_pressure.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                diastolic_blood_pressure.setText(s);
            }

        }));
        heart_rate.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                heart_rate.setText(s);
            }

        }));
        random_blood_glucose.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                random_blood_glucose.setText(s);
            }

        }));
        fasting_blood_glucose.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                fasting_blood_glucose.setText(s);
            }

        }));
        hgbT.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                hgbT.setText(s);
            }

        }));
        quantity.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                quantity.setText(oldValue);


            }
        });
        blood_glucose.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                blood_glucose.setText(s);
            }

        }));
        sName.setCellValueFactory(new PropertyValueFactory<>("name"));
        sQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        List<Medecin> medecins = Medecin.getAll();
        ObservableList<Medecin> observableMedecins = FXCollections.observableList(medecins);
        FilteredList<Medecin> filteredMedecins = new FilteredList<>(observableMedecins);
        search.textProperty().addListener((observable, oldValue, newValue)->{
            filteredMedecins.setPredicate(medecin -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerFilter = newValue.toLowerCase();

                if ( medecin.getName().toLowerCase().contains(lowerFilter))
                    return true;
                else return false;
            });
        });
        stock.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selection Changed");
            if (newValue != null) {
                idOfSelectedMedecin = newValue.getId();
                System.out.println(newValue.getName());
                System.out.println("Selected ID: " + idOfSelectedMedecin);
            } else {
                System.out.println("No selection made.");
            }
        });


        stock.setItems(filteredMedecins);

        if (givens.size()>0){
            gName.setCellValueFactory(new PropertyValueFactory<>("name"));
            gQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            ObservableList<Given> observableGivens = FXCollections.observableList(givens);
            given.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Given>() {
                @Override
                public void changed(ObservableValue<? extends Given> observableValue, Given given, Given storedGiven) {
                    if(storedGiven != null){
                        nameOfSelectedMedecin = storedGiven.getName();

                    }
                    else {
                        System.out.println("Selected Given doesn't have an id");
                    }
                }
            });
            given.setItems(observableGivens);

        }


        cNote.getItems().addAll(
                "chief complaint", "medical history", "medical and surgical history",
                "obstetric history", "gynecological history",
                "doctor and midwife note", "nutritionist note", "physiotherapist note"
        );
        cNote.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Save the current text to the variable associated with oldValue
            if (oldValue != null) {
                switch (oldValue) {
                    case "chief complaint":
                        chief_complaint = notes.getText();
                        System.out.println("nigga");
                        break;
                    case "medical history":
                        medical_history = notes.getText();
                        break;
                    case "medical and surgical history":
                        medical_and_surgical_history = notes.getText();
                        break;
                    case "obstetric history":
                        obstetric_history = notes.getText();
                        break;
                    case "gynecological history":
                        gynecological_history = notes.getText();
                        break;
                    case "doctor and midwife note":
                        doctor_and_midwife_note = notes.getText();
                        break;
                    case "nutritionist note":
                        nutritionist_note = notes.getText();
                        break;
                    case "physiotherapist note":
                        physiotherapist_note = notes.getText();
                        break;
                    default:
                        break;
                }
            }

            // Set the text area with the value associated with newValue
            if (newValue != null) {
                switch (newValue) {
                    case "chief complaint":
                        notes.setText(chief_complaint);
                        break;
                    case "medical history":
                        notes.setText(medical_history);
                        break;
                    case "medical and surgical history":
                        notes.setText(medical_and_surgical_history);
                        break;
                    case "obstetric history":
                        notes.setText(obstetric_history);
                        break;
                    case "gynecological history":
                        notes.setText(gynecological_history);
                        break;
                    case "doctor and midwife note":
                        notes.setText(doctor_and_midwife_note);
                        break;
                    case "nutritionist note":
                        notes.setText(nutritionist_note);
                        break;
                    case "physiotherapist note":
                        notes.setText(physiotherapist_note);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @FXML
    private void blankClicked(MouseEvent event){
        search.getParent().requestFocus();

    }
    public void close(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void set(ActionEvent actionEvent) {

         String givnesS="";
         for (Given given : givens) {
             givnesS=givnesS+given.getName()+", ";
         }
         int patientId=Integer.parseInt(patient_id.getText());
         int sessionId=Session.genId();
         int hgb = hgbT.getText().isEmpty()? 0:Integer.parseInt(hgbT.getText());
         int weight = weightT.getText().isEmpty()? 0:Integer.parseInt(weightT.getText());
         int bloodGlucose = blood_glucose.getText().isEmpty()? 0:Integer.parseInt(blood_glucose.getText());
         int fastingBloodGlucose = fasting_blood_glucose.getText().isEmpty()? 0:Integer.parseInt(fasting_blood_glucose.getText());
         int randomBloodGlucose=random_blood_glucose.getText().isEmpty()? 0:Integer.parseInt(random_blood_glucose.getText());
         int heartRate = heart_rate.getText().isEmpty()? 0:Integer.parseInt(heart_rate.getText());
         int diastolicBloodPressure= diastolic_blood_pressure.getText().isEmpty()? 0:Integer.parseInt(diastolic_blood_pressure.getText());
         int systolicBloodPressure= systolic_blood_pressure.getText().isEmpty()? 0:Integer.parseInt(systolic_blood_pressure.getText());
         String bloodPressure = bp1.getText()+"/"+bp2.getText();
         String chiefComplaint = chief_complaint;
         String medicalHistory = medical_history;
         String medicalAndSurgicalHistory= medical_and_surgical_history;
         String obstetricHistory = obstetric_history;
         String gynecologicalHistory = gynecological_history;
         String doctorAndMidwifeNote = doctor_and_midwife_note;
         String diagnosis = diagnosisC.getValue();
         String currentMedications =current_medications.getText().isEmpty()? null:current_medications.getText();
         String prescribedMedications = givnesS;
         String nutritionistNote = nutritionist_note;
         String physiotherapistNote = physiotherapist_note;
         String addedBy=added_by.getText();
         int day=LocalDate.now().getDayOfMonth();
         int month= LocalDate.now().getMonthValue();
         int year=LocalDate.now().getYear();
         String address= patient.getAddress();
        System.out.println(patient.getAddress());

        Session session = new Session(sessionId,patientId,hgb,weight,bloodGlucose,fastingBloodGlucose,randomBloodGlucose,heartRate,diastolicBloodPressure,systolicBloodPressure,bloodPressure,chiefComplaint,medicalHistory,medicalAndSurgicalHistory,obstetricHistory,gynecologicalHistory,doctorAndMidwifeNote,diagnosis,currentMedications,prescribedMedications,nutritionistNote,physiotherapistNote,addedBy,day,month,year,address);

        Session.addSession(session);
        for (Given given : givens) {
            Medecin.decrementQuantity(given.getId(),given.getQuantity());
        }
    }

    public void give(ActionEvent actionEvent) throws IOException {
        boolean give=true;
        Medecin medecin= Medecin.getMedecinById(idOfSelectedMedecin);

        if(medecin.getQuantity()<Double.parseDouble(quantity.getText())){
            give=false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("There is no enough quantity");
            alert.setTitle("Close alert");

            Optional<ButtonType> result = alert.showAndWait();

        }
        if (idOfSelectedMedecin !=0 && give){
            Given given1= new Given(medecin.getName(), Double.parseDouble(quantity.getText()));
            given1.setId(idOfSelectedMedecin);
            givens.add(given1);
            initialize();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please select a medicine first!!");
            alert.setTitle("Close alert");

            Optional<ButtonType> result = alert.showAndWait();

        }


    }


    public void deleteG(ActionEvent actionEvent) throws IOException {
        if(nameOfSelectedMedecin!=null){
            for (int i = 0; i < givens.size(); i++) {
                Given given=givens.get(i);
                if(given.getName().equals(nameOfSelectedMedecin)){
                    givens.remove(i);
                    initialize();
                    break;
                }
            }
            initialize();
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please select a medicine first!!");
            alert.setTitle("Close alert");

            Optional<ButtonType> result = alert.showAndWait();

        }
    }

    public void add(ActionEvent actionEvent) {
    }
}

