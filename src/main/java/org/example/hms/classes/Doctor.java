package org.example.hms.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.hms.controllers.UpdateDoctor;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private int roomId;



    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    private static final String FILE_PATH = "src/main/java/org/example/hms/dataBase/doctorsInfo.json";


    // Constructor to initialize the Doctor class using the User class's attributes
    public Doctor(String name, int id, String email, String address, boolean appointment_admin, boolean inventory_admin, String sector, boolean Doctors_admin, boolean Patients_admin, boolean staff_admin,String speciality, String userName, String password, String role,List<Integer> saturday, List<Integer> sunday, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday) {
        super(name, id, email, address, appointment_admin, inventory_admin, sector, Doctors_admin, Patients_admin, staff_admin,speciality, userName, password, role,saturday, sunday,monday, tuesday, wednesday, thursday, friday);
    }

    public static List<Doctor> getAllDoctors() throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        //check if file is empty or doesn't exists
        if (!Files.exists(filepath) || Files.size(filepath) == 0) {
            System.out.println("No doctors found in the file.");
        }
        //reads the file
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public static void addDcotro(ArrayList<Doctor> list) {
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
    public static void addOrUpdateDoctor(ArrayList<Doctor> updatedList) {
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
            ArrayList<Doctor> existingList = new ArrayList<>();
            if (Files.size(filepath) > 0) { // Check if file is not empty
                try (FileReader reader = new FileReader(filepath.toFile())) {
                    Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
                    existingList = gson.fromJson(reader, listType);
                }
            }

            // Update or add doctors based on the ID
            for (Doctor updatedDoctor : updatedList) {
                boolean found = false;
                for (int i = 0; i < existingList.size(); i++) {
                    if (existingList.get(i).getId() == updatedDoctor.getId()) {
                        existingList.set(i, updatedDoctor); // Update the existing doctor
                        found = true;
                        break;
                    }
                }

                // If the doctor is not found, add the updated doctor to the list
                if (!found) {
                    existingList.add(updatedDoctor);
                }
            }

            // Write the updated list back to the JSON file
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                gson.toJson(existingList, writer);
                System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error while handling the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while updating the doctor data.", e);
        }
    }



    public static Doctor getDoctor(String username) {
        try {
            Path filepath = Paths.get(FILE_PATH);

            // Ensure the file exists and is not empty
            if (!Files.exists(filepath) || Files.size(filepath) == 0) {
                System.out.println("No doctors found in the file.");
                return null; // Return null if the file doesn't exist or is empty
            }

            // Read the existing data from the file into an ArrayList of doctors
            Gson gson = new Gson();
            ArrayList<Doctor> existingList = new ArrayList<>();
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            }

            // Search for the doctor with the provided username
            for (Doctor doctor : existingList) {
                if (doctor.getUserName() != null && doctor.getUserName().equals(username)) {
                    return doctor; // Return the doctor object if found
                }
            }

            System.out.println("Doctor with username " + username + " not found.");
            return null; // If the doctor is not found, return null
        } catch (IOException e) {
            System.err.println("Error while reading the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while retrieving the doctor.", e);
        }
    }
    public static boolean doesUsernameExists(String username){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson =new Gson();
        ArrayList<Doctor> doctors = new ArrayList<>();
        try(FileReader reader= new FileReader(filepath.toFile())) {
            Type type= new TypeToken<ArrayList<Doctor>>(){}.getType();
            doctors=gson.fromJson(reader, type);
        }catch (IOException e){System.err.println(e);}

        for(Doctor doctor:doctors){
            if (doctor.getUserName()!=null && doctor.getUserName().equals(username) ){
                return true;
            }
        }
        return false;
    }
    public static boolean doesIdExists(int id){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson =new Gson();
        ArrayList<Doctor> doctors= new ArrayList<>();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
            doctors = gson.fromJson(reader, listType);
        }catch (IOException e){e.printStackTrace();}
        for (Doctor doctor : doctors){
            if(doctor.getId()!= 0 && doctor.getId()==id){
                return true;
            }
        }
        return false;
    }
    public static Doctor getDoctor(int id){
        Path filepath = Paths.get(FILE_PATH);
        Gson gson = new Gson();
        ArrayList<Doctor> existingList = new ArrayList<>();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
            existingList = gson.fromJson(reader, listType);
        }catch (IOException e){e.printStackTrace();}

        // Search for the doctor with the provided username
        for (Doctor doctor : existingList) {
            if (doctor.getId() != 0 && doctor.getId()==id) {
                return doctor;
            }
        }
        System.out.println("Doctor with Id " + id + " not found.");
        return null;
    }
    public static void setWorkTime(int id, List<Integer> saturday,List<Integer> sunday, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday ) throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        Gson gson = new Gson();
        ArrayList<Doctor> existingList = new ArrayList<>();
        System.out.println("i was here 1");

        // Read the JSON file into the existingList
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
            existingList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there's an error reading the file
        }
        System.out.println("i was here 2");
        boolean doctorFound = false; // Track if the doctor is found

        // Search for the doctor with the provided id and update their days
        for (Doctor doctor : existingList) {
            if (doctor.getId() == id) {
                doctorFound = true;

                if(doctor.getSaturday()!=null || doctor.getSunday()!=null || doctor.getMonday()!=null || doctor.getTuesday()!=null || doctor.getWednesday()!=null || doctor.getThursday()!=null || doctor.getFriday()!=null){
                    doctor.getSaturday().clear();
                    doctor.getSunday().clear();
                    doctor.getMonday().clear();
                    doctor.getTuesday().clear();
                    doctor.getWednesday().clear();
                    doctor.getThursday().clear();
                    doctor.getFriday().clear();
                }

                doctor.setSaturday(saturday);
                doctor.setSunday(sunday);
                doctor.setMonday(monday);
                doctor.setTuesday(tuesday);
                doctor.setWednesday(wednesday);
                doctor.setThursday(thursday);
                doctor.setFriday(friday);
                System.out.println("i was here many times");

                break; // Exit the loop once the doctor is found and updated
            }
        }

        // Check if doctor was found
        if (!doctorFound) {
            System.out.println("Doctor with ID " + id + " not found!");
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

    public boolean isHeWorking(int id){
        Doctor doctor = Doctor.getDoctor(id);
        int hours= LocalTime.now().getHour();
        int min=LocalTime.now().getMinute();
        String day = java.time.LocalDate.now().getDayOfWeek().name();
        switch (day){
            case "SATURDAY":
                if(!doctor.getSaturday().isEmpty()){
                    if(hours>doctor.getSaturday().get(0) && hours<doctor.getSaturday().get(2) ){
                        return true;
                    }
                    if(hours==doctor.getSaturday().get(0)|| hours==doctor.getSaturday().get(2)){
                        if ( min>=doctor.getSaturday().get(1) && min<doctor.getSaturday().get(3))
                            return true;
                    }
                    return false;

                }


            case "SUNDAY":
                if (!doctor.getSunday().isEmpty()){
                    if(hours>=doctor.getSunday().get(0) && hours<doctor.getSunday().get(2)){
                        return true;
                    }
                    if (hours==doctor.getSunday().get(0) || hours==doctor.getSunday().get(2)){
                        if ( min>=doctor.getSunday().get(1) && min<doctor.getSunday().get(3))
                            return true;
                    }
                    return false;
                }


            case "MONDAY":
                if (!doctor.getMonday().isEmpty()){
                    if(hours>doctor.getMonday().get(0) && hours<doctor.getMonday().get(2) )
                        return true;
                    if (hours==doctor.getMonday().get(0) && hours==doctor.getMonday().get(2)){
                        if ( min>=doctor.getMonday().get(1) && min<doctor.getMonday().get(3))
                            return  true;
                    }
                    return false;
                }

            case "TUESDAY":
                if(!doctor.getTuesday().isEmpty()){
                    if(hours>doctor.getTuesday().get(0) && hours<doctor.getTuesday().get(2) )
                        return  true;
                    if (hours==doctor.getTuesday().get(0) || hours==doctor.getTuesday().get(2)){
                        if (min>=doctor.getTuesday().get(1) && min<doctor.getTuesday().get(3))
                            return true;
                    }
                    return false;
                }

            case "THURSDAY":
                if (!doctor.getThursday().isEmpty()){
                    if(hours>=doctor.getThursday().get(0) && hours<doctor.getThursday().get(2) ){
                        return true;
                    }
                    if(hours==doctor.getThursday().get(0) && hours==doctor.getThursday().get(2)){
                        if(min>=doctor.getThursday().get(1) && min<doctor.getThursday().get(3))
                            return true;
                    }
                    return false;
                }

            case "WEDNESDAY":
                if (!doctor.getWednesday().isEmpty()){
                    if(hours>doctor.getWednesday().get(0) && hours<doctor.getWednesday().get(2) ){
                        return true;
                    }
                    if (hours==doctor.getWednesday().get(0) || hours==doctor.getWednesday().get(2)){
                        if (min>=doctor.getWednesday().get(1) && min<doctor.getWednesday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }

            case "FRIDAY":
                if (!doctor.getFriday().isEmpty()){
                    if(hours>=doctor.getFriday().get(0) && hours<doctor.getFriday().get(2) ){
                        return true;
                    }
                    if (hours==doctor.getFriday().get(0) || hours==doctor.getFriday().get(2)) {
                        if (min>=doctor.getFriday().get(1) && min<doctor.getFriday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }



            default:
                return false;
        }
    }



    public boolean isHeWorking(int id, int hours, int min, String day){
        Doctor doctor = Doctor.getDoctor(id);

        switch (day){
            case "SATURDAY":
                if(!doctor.getSaturday().isEmpty()){
                    if(hours>doctor.getSaturday().get(0) && hours<doctor.getSaturday().get(2) ){
                        return true;
                    }
                    if(hours==doctor.getSaturday().get(0)|| hours==doctor.getSaturday().get(2)){
                        if ( min>=doctor.getSaturday().get(1) && min<doctor.getSaturday().get(3))
                            return true;
                    }
                    return false;

                }

            case "SUNDAY":
                if (!doctor.getSunday().isEmpty()){
                    if(hours>=doctor.getSunday().get(0) && hours<doctor.getSunday().get(2)){
                        return true;
                    }
                    if (hours==doctor.getSunday().get(0) || hours==doctor.getSunday().get(2)){
                        if ( min>=doctor.getSunday().get(1) && min<doctor.getSunday().get(3))
                            return true;
                    }
                    return false;
                }

            case "MONDAY":
                if (!doctor.getMonday().isEmpty()){
                    if(hours>doctor.getMonday().get(0) && hours<doctor.getMonday().get(2) )
                        return true;
                    if (hours==doctor.getMonday().get(0) && hours==doctor.getMonday().get(2)){
                        if ( min>=doctor.getMonday().get(1) && min<doctor.getMonday().get(3))
                            return  true;
                    }
                    return false;
                }

            case "TUESDAY":
                if(!doctor.getTuesday().isEmpty()){
                    if(hours>doctor.getTuesday().get(0) && hours<doctor.getTuesday().get(2) )
                        return  true;
                    if (hours==doctor.getTuesday().get(0) || hours==doctor.getTuesday().get(2)){
                        if (min>=doctor.getTuesday().get(1) && min<doctor.getTuesday().get(3))
                            return true;
                    }
                    return false;
                }

            case "THURSDAY":
                if (!doctor.getThursday().isEmpty()){
                    if(hours>=doctor.getThursday().get(0) && hours<doctor.getThursday().get(2) ){
                        return true;
                    }
                    if(hours==doctor.getThursday().get(0) && hours==doctor.getThursday().get(2)){
                        if(min>=doctor.getThursday().get(1) && min<doctor.getThursday().get(3))
                            return true;
                    }
                    return false;
                }

            case "WEDNESDAY":
                if (!doctor.getWednesday().isEmpty()){
                    if(hours>doctor.getWednesday().get(0) && hours<doctor.getWednesday().get(2) ){
                        return true;
                    }
                    if (hours==doctor.getWednesday().get(0) || hours==doctor.getWednesday().get(2)){
                        if (min>=doctor.getWednesday().get(1) && min<doctor.getWednesday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }

            case "FRIDAY":
                if (!doctor.getFriday().isEmpty()){
                    if(hours>=doctor.getFriday().get(0) && hours<doctor.getFriday().get(2) ){
                        return true;
                    }
                    if (hours==doctor.getFriday().get(0) || hours==doctor.getFriday().get(2)) {
                        if (min>=doctor.getFriday().get(1) && min<doctor.getFriday().get(3)){
                            return true;
                        }
                    }
                    return false;
                }



            default:
                return false;
        }
    }


    public static void deleteDoctor(int id) throws IOException {
        List<Doctor> doctors=Doctor.getAllDoctors();
        for (int i = doctors.size() - 1; i >= 0; i--) {
            if (doctors.get(i).getId() == id) {
                doctors.remove(i);
                break;
            }
        }
        Doctor.addDcotro((ArrayList<Doctor>) doctors);
    }
    public static void UpdateDoctorInfo(ArrayList<Doctor> list) throws IOException {
        // Read the existing data from the file into an ArrayList
        Path filepath = Paths.get(FILE_PATH);

        Gson gson = new Gson();
        ArrayList<Doctor> existingList = new ArrayList<>();
        if (Files.size(filepath) > 0) { // Check if file is not empty
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<Doctor>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            }
        }
        Doctor doctor1;
        for (int i = 0; i< existingList.size();i++){
            doctor1=existingList.get(i);
            if(doctor1.getId()==list.get(0).getId()){
                existingList.get(i).setId(list.get(0).getId());
                existingList.get(i).setAddress(list.get(0).getAddress());
                existingList.get(i).setSector(list.get(0).getSector());
                existingList.get(i).setEmail(list.get(0).getEmail());
                existingList.get(i).setSpeciality(list.get(0).getSpeciality());
                existingList.get(i).setUsername(list.get(0).getUsername());
                existingList.get(i).setPassword(list.get(0).getPassword());
            }
        }


        try (FileWriter writer = new FileWriter(filepath.toFile())) {
            gson.toJson(existingList, writer);
            System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
        }
         catch (IOException e) {
            System.err.println("Error while handling the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while updating the doctor data.", e);
        }
    }
}
