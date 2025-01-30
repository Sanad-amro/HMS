package org.example.hms.classes;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String name;
    private String id;
    private String email;
    private String address;
    private boolean appointment_admin ;
    private boolean Users_admin;
    private boolean Patients_admin;
    private boolean Doctors_admin;
    private boolean inventory_admin;
    private boolean staff_admin;
    private String sector;
    private String Username;
    private String Password;
    private String role;
    private String speciality;

    private Date appointment;

    private List<Integer> saturday = new ArrayList<>();
    private List<Integer> sunday = new ArrayList<>();
    private List<Integer> monday = new ArrayList<>();
    private List<Integer> tuesday = new ArrayList<>();
    private List<Integer> wednesday = new ArrayList<>();
    private List<Integer> thursday= new ArrayList<>();
    private List<Integer> friday = new ArrayList<>();
    public User(){}
    public User(String name, String id, String email, String address, boolean appointment_admin, boolean inventory_admin, String sector, boolean Doctors_admin, boolean Patients_admin, boolean staff_admin, String Username, String Password, String role) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.address = address;
        this.inventory_admin = inventory_admin;
        this.sector = sector;
        this.Doctors_admin = Doctors_admin;
        this.Patients_admin = Patients_admin;
        this.appointment_admin = appointment_admin;
        this.staff_admin = staff_admin;
        this.Username=Username;
        this.Password=Password;
        this.role=role;
    }
    public User(String name, String id, String email, String address, boolean appointment_admin, boolean inventory_admin, String sector, boolean Doctors_admin, boolean Patients_admin, boolean staff_admin,String speciality , String Username, String Password, String role, List<Integer> saturday, List<Integer> sunday, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.address = address;
        this.inventory_admin = inventory_admin;
        this.sector = sector;
        this.Doctors_admin = Doctors_admin;
        this.Patients_admin = Patients_admin;
        this.appointment_admin = appointment_admin;
        this.staff_admin = staff_admin;
        this.Username = Username;
        this.Password = Password;
        this.role = role;
        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.speciality=speciality;
    }
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setAppointment(Date appointment){
        this.appointment=appointment;
    }
    public String getId(){
        return id;
    }
    public String getUserName(){
        return Username;
    }
    public String getPassword(){
        return Password;
    }
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getAddress(){return address;}
    public String getSector(){return  sector;}
    public String getUsername(){return Username;}
    public String getRole(){return role;}

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAppointment_admin() {
        return appointment_admin;
    }

    public void setAppointment_admin(boolean appointment_admin) {
        this.appointment_admin = appointment_admin;
    }

    public boolean isUsers_admin() {
        return Users_admin;
    }

    public void setUsers_admin(boolean Users_admin) {
        Users_admin = Users_admin;
    }

    public boolean isPatients_admin() {
        return Patients_admin;
    }

    public void setPatients_admin(boolean patients_admin) {
        Patients_admin = patients_admin;
    }

    public boolean isInventory_admin() {
        return inventory_admin;
    }

    public void setInventory_admin(boolean inventory_admin) {
        this.inventory_admin = inventory_admin;
    }

    public boolean isStaff_admin() {
        return staff_admin;
    }

    public void setStaff_admin(boolean staff_admin) {
        this.staff_admin = staff_admin;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getAppointment() {
        return appointment;
    }


    public List<Integer> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Integer> saturday) {
        this.saturday = saturday;
    }

    public List<Integer> getSunday() {
        return sunday;
    }

    public void setSunday(List<Integer> sunday) {
        this.sunday = sunday;
    }

    public List<Integer> getMonday() {
        return monday;
    }

    public void setMonday(List<Integer> monday) {
        this.monday = monday;
    }

    public List<Integer> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Integer> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Integer> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Integer> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Integer> getThursday() {
        return thursday;
    }

    public void setThursday(List<Integer> thursday) {
        this.thursday = thursday;
    }

    public List<Integer> getFriday() {
        return friday;
    }

    public void setFriday(List<Integer> friday) {
        this.friday = friday;
    }
    public boolean isDoctors_admin() {
        return Doctors_admin;
    }

    public void setDoctors_admin(boolean doctors_admin) {
        Doctors_admin = doctors_admin;
    }

    private static final String FILE_PATH = "src/main/java/org/example/hms/dataBase/usersInfo.json";
/*

    public static List<User> getAllUsers() throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        //check if file is empty or doesn't exists
        if (!Files.exists(filepath) || Files.size(filepath) == 0) {
            System.out.println("No Users found in the file.");
            return null;
        }
        //reads the file
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public static void adduser(ArrayList<User> list) {
        try {
            Path filepath = Paths.get(FILE_PATH);

            if (!Files.exists(filepath.getParent())) {
                Files.createDirectories(filepath.getParent());
                System.out.println("Directories created at: " + filepath.getParent());
            }

            if (!Files.exists(filepath)) {
                Files.createFile(filepath);
                System.out.println("File created at: " + filepath);
            }
            //read the file
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                gson.toJson(list, writer);
                System.out.println("ArrayList has been written to " + filepath + " successfully.");
            } catch (IOException e) {
                System.err.println("Error while writing to file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("An error occurred while handling the file.", e);
        }
    }



    public static void addOrUpdateUser(ArrayList<User> updatedList) {
        try {
            // Create the Path object
            Path filepath = Paths.get(FILE_PATH);

            // Ensure the parent directories exist
            if (!Files.exists(filepath.getParent())) {
                Files.createDirectories(filepath.getParent());
                System.out.println("Directories created at: " + filepath.getParent());
            }

            // Check if the file exists, if not, create it
            if (!Files.exists(filepath)) {
                Files.createFile(filepath);
                System.out.println("File created at: " + filepath);
            }

            // Read the existing data from the file into an ArrayList
            Gson gson = new Gson();
            ArrayList<User> existingList = new ArrayList<>();
            if (Files.size(filepath) > 0) { // Check if file is not empty
                try (FileReader reader = new FileReader(filepath.toFile())) {
                    Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                    existingList = gson.fromJson(reader, listType);
                }
            }

            // Update or add Users based on the ID
            for (User updatedUser: updatedList) {
                boolean found = false;
                for (int i = 0; i < existingList.size(); i++) {
                    if (existingList.get(i).getId() == updatedUser.getId()) {
                        existingList.set(i, updatedUser); // Update the existing User
                        found = true;
                        break;
                    }
                }

                // If the Useris not found, add the updated Userto the list
                if (!found) {
                    existingList.add(updatedUser);
                }
            }

            // Write the updated list back to the JSON file
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                gson.toJson(existingList, writer);
                System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error while handling the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while updating the Userdata.", e);
        }
    }



    public static User getUser(String username) {
        try {
            Path filepath = Paths.get(FILE_PATH);

            // Ensure the file exists and is not empty
            if (!Files.exists(filepath) || Files.size(filepath) == 0) {
                System.out.println("No Users found in the file.");
                return null; // Return null if the file doesn't exist or is empty
            }

            // Read the existing data from the file into an ArrayList of Users
            Gson gson = new Gson();
            ArrayList<User> existingList = new ArrayList<>();
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            }

            // Search for the Userwith the provided username
            for (User user: existingList) {
                if (user.getUserName() != null && user.getUserName().equals(username)) {
                    return user; // Return the Userobject if found
                }
            }

            System.out.println("Userwith username " + username + " not found.");
            return null; // If the Useris not found, return null
        } catch (IOException e) {
            System.err.println("Error while reading the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while retrieving the User.", e);
        }
    }
    public static boolean doesUsernameExists(String username){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson =new Gson();
        ArrayList<User> Users = new ArrayList<>();
        try(FileReader reader= new FileReader(filepath.toFile())) {
            Type type= new TypeToken<ArrayList<User>>(){}.getType();
            Users=gson.fromJson(reader, type);
        }catch (IOException e){System.err.println(e);}

        for(User user:Users){
            if (user.getUserName()!=null && user.getUserName().equals(username) ){
                return true;
            }
        }
        return false;
    }
    public static boolean doesIdExists(String id){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson =new Gson();
        ArrayList<User> Users= new ArrayList<>();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            Users = gson.fromJson(reader, listType);
        }catch (IOException e){e.printStackTrace();}
        for (User user: Users){
            if(!(user.getId().equals("0")) && user.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public static User getUserById(String id){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson = new Gson();
        ArrayList<User> existingList = new ArrayList<>();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            existingList = gson.fromJson(reader, listType);
        }catch (IOException e){e.printStackTrace();}

        // Search for the User with the provided username
        for (User user: existingList) {
            if (!(user.getId().equals("0")) && user.getId().equals(id)) {
                return user;
            }
        }
        System.out.println("Userwith Id " + id + " not found.");
        return null;
    }
    public static void setWorkTime(String id, List<Integer> saturday,List<Integer> sunday, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday ) throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        Gson gson = new Gson();
        ArrayList<User> existingList = new ArrayList<>();
        System.out.println("i was here 1");

        // Read the JSON file into the existingList
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            existingList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there's an error reading the file
        }
        System.out.println("i was here 2");
        boolean UserFound = false; // Track if the Useris found

        // Search for the User with the provided id and update their days
        for (User user: existingList) {
            if (!(user.getId().equals(0)) && user.getId().equals(id)) {
                UserFound = true;

                if(user.getSaturday()!=null || user.getSunday()!=null || user.getMonday()!=null || user.getTuesday()!=null || user.getWednesday()!=null || user.getThursday()!=null || user.getFriday()!=null){
                    user.getSaturday().clear();
                    user.getSunday().clear();
                    user.getMonday().clear();
                    user.getTuesday().clear();
                    user.getWednesday().clear();
                    user.getThursday().clear();
                    user.getFriday().clear();
                }

                user.setSaturday(saturday);
                user.setSunday(sunday);
                user.setMonday(monday);
                user.setTuesday(tuesday);
                user.setWednesday(wednesday);
                user.setThursday(thursday);
                user.setFriday(friday);
                System.out.println("i was here many times");

                break; // Exit the loop once the Useris found and updated
            }
        }

        // Check if Userwas found
        if (!UserFound) {
            System.out.println("Userwith ID " + id + " not found!");
            return;
        }

        // Write the updated list back to the file
        try (FileWriter writer = new FileWriter(filepath.toFile())) {
            gson.toJson(existingList, writer);
            System.out.println("Days Were Set Successfully!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean isHeWorking(String id){
        User user= User.getUser(id);
        int hours= LocalTime.now().getHour();
        int min=LocalTime.now().getMinute();
        String day = java.time.LocalDate.now().getDayOfWeek().name();
        switch (day){
            case "SATURDAY":
                if(!user.getSaturday().isEmpty()){
                    if(hours>user.getSaturday().get(0) && hours<user.getSaturday().get(2) ){
                        return true;
                    }
                    if(hours==user.getSaturday().get(0)|| hours==user.getSaturday().get(2)){
                        if ( min>=user.getSaturday().get(1) && min<user.getSaturday().get(3))
                            return true;
                    }
                    return false;

                }


            case "SUNDAY":
                if (!user.getSunday().isEmpty()){
                    if(hours>=user.getSunday().get(0) && hours<user.getSunday().get(2)){
                        return true;
                    }
                    if (hours==user.getSunday().get(0) || hours==user.getSunday().get(2)){
                        if ( min>=user.getSunday().get(1) && min<user.getSunday().get(3))
                            return true;
                    }
                    return false;
                }


            case "MONDAY":
                if (!user.getMonday().isEmpty()){
                    if(hours>user.getMonday().get(0) && hours<user.getMonday().get(2) )
                        return true;
                    if (hours==user.getMonday().get(0) && hours==user.getMonday().get(2)){
                        if ( min>=user.getMonday().get(1) && min<user.getMonday().get(3))
                            return  true;
                    }
                    return false;
                }

            case "TUESDAY":
                if(!user.getTuesday().isEmpty()){
                    if(hours>user.getTuesday().get(0) && hours<user.getTuesday().get(2) )
                        return  true;
                    if (hours==user.getTuesday().get(0) || hours==user.getTuesday().get(2)){
                        if (min>=user.getTuesday().get(1) && min<user.getTuesday().get(3))
                            return true;
                    }
                    return false;
                }

            case "THURSDAY":
                if (!user.getThursday().isEmpty()){
                    if(hours>=user.getThursday().get(0) && hours<user.getThursday().get(2) ){
                        return true;
                    }
                    if(hours==user.getThursday().get(0) && hours==user.getThursday().get(2)){
                        if(min>=user.getThursday().get(1) && min<user.getThursday().get(3))
                            return true;
                    }
                    return false;
                }

            case "WEDNESDAY":
                if (!user.getWednesday().isEmpty()){
                    if(hours>user.getWednesday().get(0) && hours<user.getWednesday().get(2) ){
                        return true;
                    }
                    if (hours==user.getWednesday().get(0) || hours==user.getWednesday().get(2)){
                        if (min>=user.getWednesday().get(1) && min<user.getWednesday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }

            case "FRIDAY":
                if (!user.getFriday().isEmpty()){
                    if(hours>=user.getFriday().get(0) && hours<user.getFriday().get(2) ){
                        return true;
                    }
                    if (hours==user.getFriday().get(0) || hours==user.getFriday().get(2)) {
                        if (min>=user.getFriday().get(1) && min<user.getFriday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }



            default:
                return false;
        }
    }
    public static void deleteUser(String id) throws IOException {
        List<User> Users=User.getAllUsers();
        for (int i = Users.size() - 1; i >= 0; i--) {
            if (Users.get(i).getId().equals(id)) {
                Users.remove(i);
                break;
            }
        }
        User.adduser((ArrayList<User>) Users);
    }



    public static void UpdateUserInfo(ArrayList<User> list) throws IOException {
        // Read the existing data from the file into an ArrayList
        Path filepath = Paths.get(FILE_PATH);

        Gson gson = new Gson();
        ArrayList<User> existingList = new ArrayList<>();
        if (Files.size(filepath) > 0) { // Check if file is not empty
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            }
        }
        User user1;
        for (int i = 0; i< existingList.size();i++){
            user1=existingList.get(i);
            if(user1.getId()==list.get(0).getId()){
                existingList.get(i).setName(list.get(0).getName());
                existingList.get(i).setId(list.get(0).getId());
                existingList.get(i).setAddress(list.get(0).getAddress());
                existingList.get(i).setSector(list.get(0).getSector());
                existingList.get(i).setEmail(list.get(0).getEmail());
                existingList.get(i).setSpeciality(list.get(0).getSpeciality());
                existingList.get(i).setUsername(list.get(0).getUsername());
                existingList.get(i).setPassword(list.get(0).getPassword());
                existingList.get(i).setRole(list.get(0).getRole());
            }
        }


        try (FileWriter writer = new FileWriter(filepath.toFile())) {
            gson.toJson(existingList, writer);
            System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
        }
        catch (IOException e) {
            System.err.println("Error while handling the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while updating the Userdata.", e);
        }
    }
    public List<Integer> getDay(String day) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField(day);
        field.setAccessible(true);
        return (List<Integer>) field.get(this);
    }*/
}
