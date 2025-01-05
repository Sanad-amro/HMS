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
import org.example.hms.classes.User;
//import org.example.hms.classes.User;
//import org.example.hms.classes.User;

import java.io.IOException;
import java.util.Optional;

public class Staff {
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public User getUser() {
        return user;
    }

    public Staff(){}
    private User user;
    public Staff(User user){
        this.user=user;
        System.out.println(this.user.getId());
    }
    private User userToSetworkUserFor;


    private int userid =0;
    public void setUserid(int userid) {
        this.userid = userid;
    }


    private String idOfSlectedUser ="0";
    private Parent root;
    
    private Stage stage;
    @FXML
    TableView<User> UsersTable;
    @FXML
    TableColumn<User, Integer> idC ;
    @FXML
    TableColumn<User, String> nameC;
    @FXML
    TableColumn<User, String> emailC;
    @FXML
    TextField searchField;
    @FXML
    Button SetworkUser;
    @FXML
    TableColumn<User, String> isWorking;
    @FXML
    AnchorPane anchB;



    @FXML
    private ImageView UserImageView;

    @FXML
    private VBox buttons;
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



        List<User> Users= User.getAllUsers();
        if (Users==null){
            System.out.println("no users json");
        }else{
            ObservableList<User> UsersList = FXCollections.observableArrayList(Users);
            FilteredList<User> filteredList = new FilteredList<>(UsersList, d -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(User1 -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerFilter = newValue.toLowerCase();

                    if (User1.getName().toLowerCase().contains(lowerFilter) || String.valueOf(User1.getId()).contains(newValue))
                        return true;
                    else return false;
                });
            });

            if(UsersTable!=null){
                UsersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observableValue, User User, User storedUser) {
                        if (storedUser != null) {
                            idOfSlectedUser.equals(storedUser.getId()) ;

                        } else {
                            System.out.println("Selected Usere doesn't have an id");
                        }
                    }
                });
                UsersTable.setItems(filteredList);

            }


        }


    }
    @FXML
    private void showButtons(MouseEvent event) {
        buttons.setOpacity(1);
        anchB.setOpacity(0.6);
        // KeyValue searchvalue= new KeyValue(searchField.prefWidthProperty(), 50);
        // KeyFrame searchFrame = new KeyFrame(Duration.millis(300),searchvalue);

        // Create a KeyValue to change the width property
        KeyValue widthValue = new KeyValue(buttons.prefWidthProperty(), 135.0);

        // Create a KeyFrame for the duration and the target value
        KeyFrame frame = new KeyFrame(Duration.millis(300), widthValue); // 300ms animation

        // Create a Timeline with the KeyFrame
        Timeline timeline = new Timeline(frame);
        //Timeline searchTime= new Timeline(searchFrame);
        //searchTime.play();
        timeline.play(); // Start the animation
    }
    @FXML
    private void blankClicked(MouseEvent event){
        searchField.getParent().requestFocus();

    }
    @FXML
    private void hideButtons(MouseEvent event) {

        // Create a KeyValue to change the width property
        KeyValue widthValue = new KeyValue(buttons.prefWidthProperty(), 0);

        // Create a KeyFrame for the duration and the target value
        KeyFrame frame = new KeyFrame(Duration.millis(300), widthValue); // 300ms animation

        Timeline timeline = new Timeline(frame);
        timeline.play(); // Start the animation
        anchB.setOpacity(0);

    }
    @FXML
    private void addUser(ActionEvent event) throws IOException{
        /*List<User> User=new ArrayList<>();
        User.add(new User("akram", 91230098, "akraram1973@yahoo.com","karn althor",true, false, "physiotherapy", false, true,false,"physiotherapy", "Akram2323", "nigggas"));
        User.addDcotro(new ArrayList<>(User));*/
        FXMLLoader window = new FXMLLoader(getClass().getResource("/org/example/hms/add_User.fxml"));
        Parent root = window.load();
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("HMS-add-User");
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

    public void setUser(User user){
        this.user=user;
    }

    public void setWork(ActionEvent event) throws IOException {
       /* UsersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String UserName = newValue.getName();
                *//**//*here iam supposed to start a new fxml loader which contains the User name and i will need to use the controller communication *//**//*
            }
        });*/
        if(idOfSlectedUser.equals("0")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("you must chose a User to set work for from the table first!!");
            alert.setTitle("no chose warning");
            System.out.println("I was here!! ");
            alert.showAndWait();

        }else {
            System.out.println(idOfSlectedUser);
            userToSetworkUserFor = User.getUser(idOfSlectedUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hms/SetworkUser.fxml"));
            Parent root = loader.load();
            SetworkUser UserToSet = loader.getController();
            UserToSet.setUser(userToSetworkUserFor);
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
        alert.setHeaderText("Are you sure you want to delete User "+ User.getUser(idOfSlectedUser).getName()+"?");
        alert.setTitle("Close Application");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            User.deleteUser(idOfSlectedUser);
            initialize();
        }

    }
    public void update(ActionEvent event) throws IOException {
        if(!idOfSlectedUser.equals("0")){
            User User1 = User.getUser(idOfSlectedUser);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/UpdateUser.fxml"));
            root=loader.load();
            UpdateUser updateUser = loader.getController();
            updateUser.setUser(User1);
            Scene scene = new Scene(root);
            Stage stage1 =new Stage();
            stage1.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setOnHidden(e -> {;
                try {
                    initialize();
                }catch (IOException exception){
                    System.out.println(exception);
                }
            });
            stage1.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You did not chose a User to update!!");
            alert.setTitle("no User selected Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                initialize();
            }

        }
    }

    ////////don't type anything under here plaese this must be the button////////////////////////

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
        stage.close();
        Stage stage1=new Stage();
        stage1.setTitle("HMS-Main-Doctors");
        stage1.setScene(scene);
        stage1.show();

    }


    public void patientClicked(MouseEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Patent.fxml"));
        Parent root = loader.load();

        // Create a new scene with the loaded FXML root
        Scene scene = new Scene(root);

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Pass data to the Patients controller
        Patients patients = loader.getController();
        if (doctor != null) {
            System.out.println("User is null");
            patients.setUser(user); // Assuming 'setUser' accepts the 'user' object
        } else {
            System.out.println("Doctor is null");
            patients.setDoctor(doctor); // Assuming 'setDoctor' accepts the 'doctor' object
        }

        // Set the new scene on the current stage
        stage.setScene(scene);
    }


    public void staff(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Staff.fxml"));
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

        stage.setScene(scene);

    }

    public void appointment(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/example/hms/HMS-Main-Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Appointments appointments = loader.getController();
        if (doctor!=null){
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
        if (doctor!=null){
            System.out.println("user is null");
            inventory.setUser(user);
        }else {
            System.out.println("doctor is null");
            inventory.setDoctor(doctor);
        }
        stage.setScene(scene);

    }
}
