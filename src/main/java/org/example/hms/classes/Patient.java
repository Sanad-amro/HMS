package org.example.hms.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.hms.controllers.Patients;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Patient {
        private String name;
        private int id;
        private String email;
        private String address;
        private String sector;
        private String medicalRecord;
        private int age;

        public Patient(){}
        public Patient(String name, int id, String email, String address, String sector, String medicalRecord, int age) {
                this.name = name;
                this.id = id;
                this.email = email;
                this.address = address;
                this.sector = sector;
                this.medicalRecord = medicalRecord;
                this.age = age;
        }
        public Patient(String name, int id, String address, String sector, String medicalRecord, int age) {
                this.name = name;
                this.id = id;
                this.address = address;
                this.sector = sector;
                this.medicalRecord = medicalRecord;
                this.age = age;
        }


        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getSector() {
                return sector;
        }

        public void setSector(String sector) {
                this.sector = sector;
        }

        public String getMedicalRecord() {
                return medicalRecord;
        }

        public void setMedicalRecord(String medicalRecord) {
                this.medicalRecord = medicalRecord;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }


        private static final String FILE_PATH = "src/main/java/org/example/hms/dataBase/patientsInfo.json";

        public static void addOrUpdatePatient(ArrayList<Patient> updatedList) {
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
                        ArrayList<Patient> existingList = new ArrayList<>();
                        if (Files.size(filepath) > 0) { // Check if file is not empty
                                try (FileReader reader = new FileReader(filepath.toFile())) {
                                        Type listType = new TypeToken<ArrayList<Patient>>() {}.getType();
                                        existingList = gson.fromJson(reader, listType);
                                }
                        }

                        // Update or add Patients based on the ID
                        for (Patient updatedPatient : updatedList) {
                                boolean found = false;
                                for (int i = 0; i < existingList.size(); i++) {
                                        if (existingList.get(i).getId() == updatedPatient.getId()) {
                                                existingList.set(i, updatedPatient); // Update the existing Patient
                                                found = true;
                                                break;
                                        }
                                }

                                // If the Patient is not found, add the updated Patient to the list
                                if (!found) {
                                        existingList.add(updatedPatient);
                                }
                        }

                        // Write the updated list back to the JSON file
                        try (FileWriter writer = new FileWriter(filepath.toFile())) {
                                gson.toJson(existingList, writer);
                                System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
                        }

                } catch (IOException e) {
                        System.err.println("Error while handling the file: " + e.getMessage());
                        throw new RuntimeException("An error occurred while updating the Patient data.", e);
                }
        }

        public static Patient getPatient(int id){
                Path filepath = Paths.get(FILE_PATH);
                Gson gson = new Gson();
                ArrayList<Patient> existingList = new ArrayList<>();
                try (FileReader reader = new FileReader(filepath.toFile())) {
                        Type listType = new TypeToken<ArrayList<Patient>>() {}.getType();
                        existingList = gson.fromJson(reader, listType);
                }catch (IOException e){e.printStackTrace();}

                for (Patient patient : existingList) {
                        if (patient.getId() != 0 && patient.getId()==id) {
                                return patient;
                        }
                }
                System.out.println("Patient with Id " + id + " not found.");
                return null;
        }

        public static List<Patient> getAllPatients() throws IOException {
                Path filepath = Paths.get(FILE_PATH);
                //check if file is empty or doesn't exists
                if (!Files.exists(filepath) || Files.size(filepath) == 0) {
                        System.out.println("No patients found in the file.");
                }
                //reads the file
                Gson gson = new Gson();
                try (FileReader reader = new FileReader(filepath.toFile())) {
                        Type listType = new TypeToken<ArrayList<Patient>>() {}.getType();
                        return gson.fromJson(reader, listType);
                } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }


        public static boolean doesIdExists(int id){
                Path filepath = Paths.get(FILE_PATH);
                Gson gson =new Gson();
                ArrayList<Patient> patients= new ArrayList<>();
                try (FileReader reader = new FileReader(filepath.toFile())) {
                        Type listType = new TypeToken<ArrayList<Patient>>() {}.getType();
                        patients = gson.fromJson(reader, listType);
                }catch (IOException e){e.printStackTrace();}
                for (Patient patient : patients){
                        if(patient.getId()!= 0 && patient.getId()==id){
                                return true;
                        }
                }
                return false;
        }
        public static void UpdatePatientInfo(ArrayList<Patient> list) throws IOException {
                // Read the existing data from the file into an ArrayList
                Path filepath = Paths.get(FILE_PATH);

                Gson gson = new Gson();
                ArrayList<Patient> existingList = new ArrayList<>();
                if (Files.size(filepath) > 0) { // Check if file is not empty
                        try (FileReader reader = new FileReader(filepath.toFile())) {
                                Type listType = new TypeToken<ArrayList<Patient>>() {}.getType();
                                existingList = gson.fromJson(reader, listType);
                        }
                }
                Patient patient;
                for (int i = 0; i< existingList.size();i++){
                        patient=existingList.get(i);
                        if(patient.getId()==list.get(0).getId()){
                                existingList.get(i).setName(list.get(0).getName());
                                existingList.get(i).setId(list.get(0).getId());
                                existingList.get(i).setAddress(list.get(0).getAddress());
                                existingList.get(i).setSector(list.get(0).getSector());
                                existingList.get(i).setEmail(list.get(0).getEmail());
                                existingList.get(i).setMedicalRecord(list.get(0).getMedicalRecord());
                                existingList.get(i).setAge(list.get(0).getAge());

                        }
                }


                try (FileWriter writer = new FileWriter(filepath.toFile())) {
                        gson.toJson(existingList, writer);
                        System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
                }
                catch (IOException e) {
                        System.err.println("Error while handling the file: " + e.getMessage());
                        throw new RuntimeException("An error occurred while updating the Patinet data.", e);
                }
        }

        public static void deletePatient(int id) throws IOException {
                List<Patient> patient=Patient.getAllPatients();
                for (int i = patient.size() - 1; i >= 0; i--) {
                        if (patient.get(i).getId() == id) {
                                patient.remove(i);
                                break;
                        }
                }
                Patient.addPatient((ArrayList<Patient>) patient);

        }
        public static void addPatient(ArrayList<Patient> list) {
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


        
        
}
