package org.example.hms.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
    TextField yearT;
    @FXML
    TextField monthT;
    @FXML
    TextField dayT;
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
    Label success;
    @FXML
    TextField search;
    @FXML
    TextField quantity;
    @FXML
    ComboBox<String> diagnosisC;

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
    @FXML
    TextArea t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    @FXML
    ToggleButton to1,to2,to3,to4,to5,to6,to7,to8,to9,to10;

    int day=LocalDate.now().getDayOfMonth();
    int month= LocalDate.now().getMonthValue();
    int year=LocalDate.now().getYear();

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
    private String midWifeNote="FREE";
    private String psychologistNote = "FREE";


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
        to1.setOnAction(event -> {
            if(to1.isSelected()){
                to2.setSelected(false);
                to3.setSelected(false);
                to4.setSelected(false);
                to5.setSelected(false);
                to6.setSelected(false);
                to7.setSelected(false);
                to8.setSelected(false);
                to9.setSelected(false);
                to10.setSelected(false);

                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t1.setMinHeight(80);
            }
            else if(to2.isSelected()){
                t1.setMinHeight(0);
            }
        });
        to2.setOnAction(event -> {
            to1.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to2.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t2.setMinHeight(80);
            }
            else if(to2.isSelected()){
                t2.setMinHeight(0);
            }
        });
        to3.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to3.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t3.setMinHeight(80);
            }
            else if(to3.isSelected()){
                t3.setMinHeight(0);
            }
        });
        to4.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to4.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t4.setMinHeight(80);
            }
            else if(to4.isSelected()){
                t4.setMinHeight(0);
            }
        });
        to5.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to5.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t5.setMinHeight(80);
            }
            else if(to5.isSelected()){
                t5.setMinHeight(0);
            }
        });
        to6.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to6.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t6.setMinHeight(80);
            }
            else if(to2.isSelected()){
                t6.setMinHeight(0);
            }
        });
        to7.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to7.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t7.setMinHeight(80);
            }
            else if(to7.isSelected()){
                t7.setMinHeight(0);
            }
        });
        to8.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to9.setSelected(false);
            to10.setSelected(false);
            if(to8.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t8.setMinHeight(80);
            }
            else if(to8.isSelected()){
                t8.setMinHeight(0);
            }
        });
        to9.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to10.setSelected(false);
            if(to9.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t9.setMinHeight(80);
            }
            else if(to9.isSelected()){
                t9.setMinHeight(0);
            }
        });
        to10.setOnAction(event -> {
            to1.setSelected(false);
            to2.setSelected(false);
            to3.setSelected(false);
            to4.setSelected(false);
            to5.setSelected(false);
            to6.setSelected(false);
            to7.setSelected(false);
            to8.setSelected(false);
            to9.setSelected(false);
            if(to10.isSelected()){
                t1.setMinHeight(0);
                t2.setMinHeight(0);
                t3.setMinHeight(0);
                t4.setMinHeight(0);
                t5.setMinHeight(0);
                t6.setMinHeight(0);
                t7.setMinHeight(0);
                t8.setMinHeight(0);
                t9.setMinHeight(0);
                t10.setMinHeight(0);
                t10.setMinHeight(80);
            }
            else if(to10.isSelected()){
                t10.setMinHeight(0);
            }
        });
        yearT.setText(String.valueOf(year));
        monthT.setText(String.valueOf(month));
        dayT.setText(String.valueOf(day));
        success.setVisible(false);
        diagnosisC.setItems(Diagnosis.getAllItems());
        to1.setSelected(true);
        to2.setSelected(false);
        to3.setSelected(false);
        to4.setSelected(false);
        to5.setSelected(false);
        to6.setSelected(false);
        to7.setSelected(false);
        to8.setSelected(false);
        to9.setSelected(false);
        to10.setSelected(false);


        t1.setMinHeight(80);
        t2.setMinHeight(0);
        t3.setMinHeight(0);
        t4.setMinHeight(0);
        t5.setMinHeight(0);
        t6.setMinHeight(0);
        t7.setMinHeight(0);
        t8.setMinHeight(0);
        t9.setMinHeight(0);
        t10.setMinHeight(0);
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
            if (newValue != null) {
                idOfSelectedMedecin = newValue.getId();
            } else {
            }
        });
        yearT.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                yearT.setText(s);
            }

        }));
        monthT.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                monthT.setText(s);
            }

        }));
        dayT.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                dayT.setText(s);
            }

        }));


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
         ArrayList<Transactions> transactions = new ArrayList<>();
         for (Given given : givens) {
             givnesS=givnesS+given.getName()+ ": "+given.getQuantity()+ "\n";
             Transactions transactions1 =new Transactions(given.getQuantity(), given.getName(),doctor.getName(),year,month,day,patient.getName());
             transactions.add(transactions1);
         }

         Transactions.makeTransaciton(transactions);
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
         String chiefComplaint = t1.getText();
         String medicalHistory = t2.getText();
         String medicalAndSurgicalHistory= t3.getText();
         String obstetricHistory = t4.getText();
         String gynecologicalHistory = t5.getText();
         String doctorAndMidwifeNote = t6.getText();
         String diagnosis = diagnosisC.getValue();
         String currentMedications =current_medications.getText().isEmpty()? null:current_medications.getText();
         String prescribedMedications = givnesS;
         String nutritionistNote = t7.getText();
         String physiotherapistNote = t8.getText();
         String addedBy=added_by.getText();
         midWifeNote=t9.getText();
         psychologistNote=t10.getText();

         String address= patient.getAddress();

        String visit = day + "/" + month + "/"+year;


        if (!visit.equals(patient.getLastVisit())) {
            patient.setNumOfVisits(patient.getN_visits() + 1);
            patient.setLastVisit(visit);
             ArrayList<Patient> patients= new ArrayList<>();
            patients.add(patient);
            Patient.UpdatePatientInfo((patients));
        }
        Session session = new Session(sessionId,patientId,hgb,weight,bloodGlucose,fastingBloodGlucose,randomBloodGlucose,heartRate,diastolicBloodPressure,systolicBloodPressure,bloodPressure,chiefComplaint,medicalHistory,medicalAndSurgicalHistory,obstetricHistory,gynecologicalHistory,doctorAndMidwifeNote,diagnosis,currentMedications,prescribedMedications,nutritionistNote,physiotherapistNote,addedBy,Integer.parseInt(dayT.getText()),Integer.parseInt(monthT.getText()),Integer.parseInt(yearT.getText()),address,midWifeNote,psychologistNote,patient.getName(),0);
        success.setVisible(true);
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

    public void add(ActionEvent actionEvent) throws IOException {
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/addDiagnosis.fxml"));
        Parent root = window.load();
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-diagnosis");
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOnHidden(e -> {
            try {
                initialize();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage1.show();
    }

}

