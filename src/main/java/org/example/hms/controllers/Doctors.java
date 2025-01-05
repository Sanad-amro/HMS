package org.example.hms.controllers;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;
//import org.example.hms.classes.Doctor;
//import org.example.hms.classes.User;

import java.io.IOException;
import java.util.Optional;

public class Doctors {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctors(){}
    private Doctor doctor;
    public Doctors(Doctor doctor){
        this.doctor=doctor;
        System.out.println(this.doctor.getId());
    }
    private Doctor doctorToSetWorkFor;





    private int docotrId=0;
    public void setDocotrId(int docotrId) {
        this.docotrId = docotrId;
    }

    private String idOfSlectedDoctor ="0";
    private Parent root;
    private Stage stage;
    @FXML
    TableView<Doctor> doctorsTable;
    @FXML
    TableColumn<Doctor, String > idC ;
    @FXML
    TableColumn<Doctor, String> nameC;
    @FXML
    TableColumn<Doctor, String> emailC;
    @FXML
    TextField searchField;
    @FXML
    Button setWork;
    @FXML
    AnchorPane anchB;



    @FXML
    private ImageView doctorImageView;

    @FXML
    private VBox buttons;

    /**
     * Logout and return to the login screen.
     */


    @FXML
    private void Logout(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/hms/Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("HMS-LogIn");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load the login page.");
        }
    }


    @FXML
    private void close(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }



    @FXML
    public void initialize() throws IOException {
        searchField.clear();
        searchField.getParent().requestFocus();
        buttons.setOpacity(0);
        idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailC.setCellValueFactory(new PropertyValueFactory<>("email"));



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
                    idOfSlectedDoctor = storedDoctor.getId();

                }
                else {
                    System.out.println("Selected doctor doesn't have an id");
                }
            }
        });

        doctorsTable.setItems(filteredList);


    }
    @FXML
    private void showButtons(MouseEvent event) {
        buttons.setOpacity(1);
        anchB.setOpacity(0.6);

        KeyValue widthValue = new KeyValue(buttons.prefWidthProperty(), 135.0);

        KeyFrame frame = new KeyFrame(Duration.millis(300), widthValue); // 300ms animation

        Timeline timeline = new Timeline(frame);
        timeline.play();
    }
    @FXML
    private void blankClicked(MouseEvent event){
        searchField.getParent().requestFocus();

    }
    @FXML
    private void hideButtons(MouseEvent event) {

        KeyValue widthValue = new KeyValue(buttons.prefWidthProperty(), 0);

        KeyFrame frame = new KeyFrame(Duration.millis(300), widthValue); // 300ms animation

        Timeline timeline = new Timeline(frame);
        timeline.play();
        anchB.setOpacity(0);

    }
    @FXML
    private void addDoctor(ActionEvent event) throws IOException{
        /*List<Doctor> doctor=new ArrayList<>();
        doctor.add(new Doctor("akram", 91230098, "akraram1973@yahoo.com","karn althor",true, false, "physiotherapy", false, true,false,"physiotherapy", "Akram2323", "nigggas"));
        Doctor.addDcotro(new ArrayList<>(doctor));*/
       FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/add_doctor.fxml"));
       Parent root = window.load();
       Scene scene = new Scene(root);
       Stage stage1 = new Stage();
       stage1.setScene(scene);
       stage1.setTitle("HMS-add-doctor");
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

    /**
     * Show an error alert.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setDoctor(Doctor doctor){
        this.doctor=doctor;
    }

    public void setWork(ActionEvent event) throws IOException {

        if(idOfSlectedDoctor.equals("0")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("you must chose a doctor to set work for from the table first!!");
            alert.setTitle("no chose warning");
            System.out.println("I was here!! ");
            alert.showAndWait();

        }else {
            System.out.println(idOfSlectedDoctor);
            doctorToSetWorkFor = Doctor.getDoctorById(idOfSlectedDoctor);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hms/setWork.fxml"));
            Parent root = loader.load();
            SetWork doctorToSet = loader.getController();
            doctorToSet.setDoctor(doctorToSetWorkFor);
            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {
                try {
                    initialize();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            stage1.setScene(scene);
            stage1.show();
        }
    }
    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete Doctor "+ Doctor.getDoctorById(idOfSlectedDoctor).getName()+"?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Doctor.deleteDoctor(idOfSlectedDoctor);
            initialize();
        }

    }
    public void update(ActionEvent event) throws IOException {
        if(!idOfSlectedDoctor.equals("0")){
            Doctor doctor1 = Doctor.getDoctorById(idOfSlectedDoctor);
            System.out.println(doctor1.getSpeciality());
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/updateDoctor.fxml"));
            root=loader.load();
            UpdateDoctor updateDoctor = loader.getController();
            updateDoctor.setDoctor(doctor1);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {
                try {
                    initialize();
                }catch (IOException exception){
                    System.out.println(exception);
                }
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a doctor to update!!");
            alert.setTitle("no doctor selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }


    @FXML
    private void doctorClicked(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Doctors.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Doctors doctors = loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            doctors.setUser(user);
        }else {
            System.out.println("doctor is null");
            doctors.setDoctor(doctor);
        }
        stage.setScene(scene);

    }


    public void patientClicked(MouseEvent event) throws IOException {
        System.out.println(doctor.getName());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Patent.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Patients patients= loader.getController();
        if (doctor==null){
            System.out.println("this is a user ");
            patients.setUser(user);
        }else {
            System.out.println("this is a docotr");
            patients.setDoctor(doctor);
        }
        stage.setScene(scene);
    }

    public void staff(MouseEvent event) throws IOException {
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Staff.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Staff staff = loader.getController();
        if (doctor!=null){
            System.out.println("user is null");
            staff.setUser(user);
        }else {
            System.out.println("doctor is null");
            staff.setDoctor(doctor);
        }
        stage.setScene(scene);*/


    }

    public void appointment(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Appointments appointments = loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            appointments.setUser(user);
        }else {
            System.out.println("doctor is null");
            appointments.setDoctor(doctor);
        }
        stage.setScene(scene);



    }

    public void inventory(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Inventory.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1=new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Inventory inventory=loader.getController();
        if (doctor==null){
            System.out.println("user is null");
            inventory.setUser(user);
        }else {
            System.out.println("doctor is null");
            inventory.setDoctor(doctor);
        }
        stage.setScene(scene);

    }
}
