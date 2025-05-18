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

public class View {


    Session session;

    @FXML
    TableView<Session> appTable;
    @FXML
    TableColumn<Session, String> created_at;
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
    private String midWifeNote="FREE";
    private String psychologistNote = "FREE";


    int patientId;
    Double hgb ;
    int weight ;
    int bloodGlucose;
    int fastingBloodGlucose;
    int randomBloodGlucose;
    int heartRate ;
    int diastolicBloodPressure;
    int systolicBloodPressure;
    String bloodPressure;
    String chiefComplaint;
    String medicalHistory;
    String medicalAndSurgicalHistory;
    String obstetricHistory ;
    String gynecologicalHistory ;
    String doctorAndMidwifeNote ;
    String diagnosis ;
    String currentMedications ;
    String prescribedMedications ;
    String nutritionistNote ;
    String physiotherapistNote ;
    String addedBy;
    int day;
    int month;
    int year;
    String address;
    String patientName;

    List<Session> sessions= Session.getAllSessions();
    ObservableList<Session> sessionObservableList= FXCollections.observableArrayList(sessions);
    FilteredList<Session> filteredList=new FilteredList<>(sessionObservableList, d -> true);

    private void pop(){
        filteredList=new FilteredList<>(sessionObservableList, d -> true);
        filteredList=new FilteredList<>(sessionObservableList, d -> d.getPatientId()==session.getPatientId());
        appTable.setItems(filteredList                 );




        chief_complaint = session.getChiefComplaint();
        medical_history = session.getMedicalHistory();
        medical_and_surgical_history = session.getMedicalAndSurgicalHistory();
        obstetric_history = session.getObstetricHistory();
        gynecological_history = session.getGynecologicalHistory();
        doctor_and_midwife_note = session.getDoctorAndMidwifeNote();
        nutritionist_note = session.getNutritionistNote();
        physiotherapist_note = session.getPhysiotherapistNote();
        midWifeNote=session.getMidWifeNote();
        psychologistNote = session.getPsychologistNote();

        notes.setText(chief_complaint);


        current_medications.setText(session.getPrescribedMedications()+"\n"+session.getCurrentMedications());


        patient_name.setText(session.getPatientName());
        patient_id.setText(String.valueOf(session.getPatientId()));
        added_by.setText(session.getAddedBy());




        String givnesS="";
        for (Given given : givens) {
            givnesS=givnesS+given.getName()+", ";
        }
         patientId=session.getPatientId();
         hgb = session.getHgb();
         weight = session.getWeight();
         bloodGlucose = session.getBloodGlucose();
         fastingBloodGlucose = session.getFastingBloodGlucose();
         randomBloodGlucose=session.getRandomBloodGlucose();
         heartRate = session.getHeartRate();
         diastolicBloodPressure= session.getDiastolicBloodPressure();
         systolicBloodPressure= session.getSystolicBloodPressure();


         bloodPressure = session.getBloodPressure();
         chiefComplaint = session.getChiefComplaint();
         medicalHistory = session.getMedicalHistory();
         medicalAndSurgicalHistory= session.getMedicalAndSurgicalHistory();
         obstetricHistory = session.getObstetricHistory();
         gynecologicalHistory = session.getGynecologicalHistory();
         doctorAndMidwifeNote = session.getDoctorAndMidwifeNote();
         diagnosis = session.getDiagnosis();
         currentMedications =session.getPrescribedMedications();
         prescribedMedications = session.getPrescribedMedications();
         nutritionistNote = session.getNutritionistNote();
         physiotherapistNote = session.getPhysiotherapistNote();
         addedBy=added_by.getText();

        String[] parts = bloodPressure.split("/");

         if (bloodPressure.isEmpty() || bloodPressure.isBlank() || !bloodPressure.contains("/") || bloodPressure.length()<3){
             bp1.setText("");
             bp2.setText("");
         }
         else {

             bp1.setText(String.valueOf(parts[0]));
             bp2.setText(String.valueOf(parts[1]));
         }

        diagnosisC.setValue(session.getDiagnosis());


        patient_id.setText(String.valueOf(patientId));
        hgbT.setText(String.valueOf(hgb));
        blood_glucose.setText(String.valueOf(bloodGlucose));
        fasting_blood_glucose.setText(String.valueOf(fastingBloodGlucose));
        random_blood_glucose.setText(String.valueOf(randomBloodGlucose));
        heart_rate.setText(String.valueOf(heartRate));
        diastolic_blood_pressure.setText(String.valueOf(diastolicBloodPressure));
        systolic_blood_pressure.setText(String.valueOf(systolicBloodPressure));
        added_by.setText(session.getAddedBy());




         chiefComplaint=session.getChiefComplaint();
         medicalHistory= session.getMedicalHistory();
         medicalAndSurgicalHistory=session.getMedicalAndSurgicalHistory();
         obstetricHistory=session.getObstetricHistory() ;
         gynecologicalHistory=session.getGynecologicalHistory() ;
         doctorAndMidwifeNote=session.getDoctorAndMidwifeNote() ;
         diagnosis=session.getDiagnosis() ;
         prescribedMedications=session.getPrescribedMedications() ;
         nutritionistNote=session.getNutritionistNote() ;
         physiotherapistNote=session.getPhysiotherapistNote() ;
         weightT.setText(String.valueOf(session.getWeight()));


    }
    ////////////////////////////////////////////////////////////////////////////////initialize ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initialize() throws IOException {
        created_at.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        appTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Session>() {
            @Override
            public void changed(ObservableValue<? extends Session> observableValue, Session session1, Session storedSession) {
                if(storedSession != null){
                     session= storedSession;
                    System.out.println("Selected Nigga ");

                    pop();

                }
                else {
                    session= storedSession;

                    System.out.println("Selected Appointment doesn't have an id");
                }
            }
        });

        success.setVisible(false);
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

        if (givens.size()>=0){
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
                "General Doctor", "nutritionist note", "physiotherapist note", "psychologist Note","midWife Note"
        );
        cNote.setValue("chief complaint");
        cNote.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Save the current text to the variable associated with oldValue
            if (oldValue != null) {
                switch (oldValue) {
                    case "chief complaint":
                        chief_complaint = notes.getText();
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
                    case "General Doctor":
                        doctor_and_midwife_note = notes.getText();
                        break;
                    case "nutritionist note":
                        nutritionist_note = notes.getText();
                        break;
                    case "physiotherapist note":
                        physiotherapist_note = notes.getText();
                        break;
                    case "midWife Note":
                        midWifeNote = notes.getText();
                        break;
                    case "psychologist Note":
                        psychologistNote = notes.getText();
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
                    case "General Doctor":
                        notes.setText(doctor_and_midwife_note);
                        break;
                    case "nutritionist note":
                        notes.setText(nutritionist_note);
                        break;
                    case "physiotherapist note":
                        notes.setText(physiotherapist_note);
                        break;
                    case "midWife Note":
                        notes.setText(midWifeNote);
                        break;
                    case "psychologist Note":
                        notes.setText(psychologistNote);
                        break;
                    default:
                        break;
                }
            }
        });
        appTable.setItems(filteredList);
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
            System.out.println(given.getName()+ ": "+given.getQuantity()+ "\n");
            givnesS=givnesS+given.getName()+ ": "+given.getQuantity()+ "\n";

        }


        int patientId=Integer.parseInt(patient_id.getText());
        int sessionId=session.getSessionId();
        int hgb = hgbT.getText().isEmpty()? 0:Integer.parseInt(hgbT.getText());
        int weight = weightT.getText().isEmpty()? 0:Integer.parseInt(weightT.getText());
        int bloodGlucose = blood_glucose.getText().isEmpty()? 0:Integer.parseInt(blood_glucose.getText());
        int fastingBloodGlucose = fasting_blood_glucose.getText().isEmpty()? 0:Integer.parseInt(fasting_blood_glucose.getText());
        int randomBloodGlucose=random_blood_glucose.getText().isEmpty()? 0:Integer.parseInt(random_blood_glucose.getText());
        int heartRate = heart_rate.getText().isEmpty()? 0:Integer.parseInt(heart_rate.getText());
        int diastolicBloodPressure= diastolic_blood_pressure.getText().isEmpty()? 0:Integer.parseInt(diastolic_blood_pressure.getText());
        int systolicBloodPressure= systolic_blood_pressure.getText().isEmpty()? 0:Integer.parseInt(systolic_blood_pressure.getText());
         bloodPressure = bp1.getText()+"/"+bp2.getText();
         chiefComplaint = chief_complaint;
         medicalHistory = medical_history;
         medicalAndSurgicalHistory= medical_and_surgical_history;
         obstetricHistory = obstetric_history;
         gynecologicalHistory = gynecological_history;
         doctorAndMidwifeNote = doctor_and_midwife_note;
         diagnosis = diagnosisC.getValue();
         currentMedications =current_medications.getText();
         prescribedMedications = givnesS;
         nutritionistNote = nutritionist_note;
         physiotherapistNote = physiotherapist_note;
        String addedBy = added_by.getText();
        int day=LocalDate.now().getDayOfMonth();
        int month= LocalDate.now().getMonthValue();
        int year=LocalDate.now().getYear();
        String address= Patient.getPatient(session.getPatientId()).getAddress();
        saveComb();


        Session session1 = new Session(sessionId,patientId,hgb,weight,bloodGlucose,fastingBloodGlucose,randomBloodGlucose,heartRate,diastolicBloodPressure,systolicBloodPressure,bloodPressure,chiefComplaint,medicalHistory,medicalAndSurgicalHistory,obstetricHistory,gynecologicalHistory,doctorAndMidwifeNote,diagnosis,currentMedications,givnesS,nutritionistNote,physiotherapistNote,addedBy,day,month,year,address,midWifeNote,psychologistNote,session.getPatientName(),0);
        success.setVisible(true);
        System.out.println(session1.getHgb());
        Session.updateSessionById(session1);
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
                Given given1=givens.get(i);
                if(given1.getName().equals(nameOfSelectedMedecin)){
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
                System.out.println("closed!");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage1.show();
    }
    private void saveComb(){
        switch (cNote.getValue()) {
            case "chief complaint":
                chief_complaint=notes.getText();
                chiefComplaint=notes.getText();
                physiotherapistNote=notes.getText();
                break;
            case "medical history":
                medical_history=notes.getText();
                medicalHistory=notes.getText();
                physiotherapistNote=notes.getText();
                break;
            case "medical and surgical history":
                medical_and_surgical_history=notes.getText();
                medicalAndSurgicalHistory=notes.getText();
                medicalAndSurgicalHistory=notes.getText();
                break;
            case "obstetric history":
                obstetric_history=notes.getText();
                obstetricHistory=notes.getText();


                break;
            case "gynecological history":
                gynecological_history=notes.getText();
                gynecologicalHistory=notes.getText();
                break;
            case "General Doctor":
                doctor_and_midwife_note=notes.getText();
                doctorAndMidwifeNote=notes.getText();
                break;
            case "nutritionist note":
                nutritionist_note=notes.getText();
                nutritionistNote=notes.getText();
                break;
            case "physiotherapist note":
                System.out.println("I was here!!!!!!!!");
                physiotherapist_note=notes.getText();
                physiotherapistNote=notes.getText();
                System.out.println("this is the akram note: " + physiotherapistNote);
                break;
            case "midWife Note":
                midWifeNote=notes.getText();
                break;
            case "psychologist Note":
                psychologistNote=notes.getText();
                break;
            default:
                break;
        }

    }

    public void setSession(Session sessionById) {
        this.session = sessionById;
        pop();
    }

}

