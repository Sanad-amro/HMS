package org.example.hms.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private String doctorName;
    private String patientName;
    private String day;
    private String operation;
    private int doctorId;
    private int PatientId;
    private int sHour;
    private int sMin;
    private int fHour;
    private int fMin;
    private int id;



    public Appointment(String doctorName, String patientName, String day, String operation, int doctorId, int patientId, int sHour, int sMin, int fHour, int fMin) throws IOException {
        if(Appointment.getAllAppointments()==null){
            this.id=0;
        }else this.id=Appointment.getAllAppointments().size()+1;;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.day = day;
        this.operation = operation;
        this.doctorId = doctorId;
        this.PatientId = patientId;
        this.sHour = sHour;
        this.sMin = sMin;
        this.fHour = fHour;
        this.fMin = fMin;
    }
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getsHour() {
        return sHour;
    }
    public int getSHour() {
        return sHour;
    }

    public void setsHour(int sHour) {
        this.sHour = sHour;
    }

    public int getsMin() {
        return sMin;
    }
    public int getSMin() {
        return sMin;
    }

    public void setsMin(int sMin) {
        this.sMin = sMin;
    }

    public int getfHour() {
        return fHour;
    }

    public void setfHour(int fHour) {
        this.fHour = fHour;
    }

    public int getfMin() {
        return fMin;
    }

    public void setfMin(int fMin) {
        this.fMin = fMin;
    }

    private static final String FILE_PATH = "src/main/java/org/example/hms/dataBase/appointmentsInfo.json";


    public static boolean isTheDoctorAvailable(int doctorId,Appointment newAppointment) throws NoSuchFieldException, IllegalAccessException, IOException {
        Doctor doctor1 = Doctor.getDoctor(doctorId);
        List<Appointment> appointments = Appointment.getAllAppointments();

        if (doctor1.isHeWorking(doctorId,newAppointment.getsHour(), newAppointment.getsMin(),newAppointment.getDay().toUpperCase())){
            if (appointments==null){
                return true;

            }
            for(Appointment appointment : appointments){
                if (newAppointment.getDay().equals(appointment.getDay())){
                    if(newAppointment.getsHour()<appointment.getfHour()||newAppointment.getfHour()>appointment.getsHour()){
                        System.out.println("seems like the doctor is not valid !!");
                        return false;
                    }
                    if((newAppointment.getsHour()==appointment.getfHour() && newAppointment.getsMin()>appointment.getfMin() )|| newAppointment.getfHour()==appointment.getsHour() && newAppointment.getfMin()<appointment.getsMin() ){
                        System.out.println("Seems like the doctor is valid");
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public static List<Appointment> getAllAppointments() throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        //check if file is empty or doesn't exists
        if (!Files.exists(filepath) || Files.size(filepath) == 0) {
            System.out.println("No appointment found in the file.");
            return null;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Type listType = new TypeToken<ArrayList<Appointment>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void addOrUpdateAppointment(ArrayList<Appointment> updatedList) {
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
            ArrayList<Appointment> existingList = new ArrayList<>();
            if (Files.size(filepath) > 0) { // Check if file is not empty
                try (FileReader reader = new FileReader(filepath.toFile())) {
                    Type listType = new TypeToken<ArrayList<Appointment>>() {}.getType();
                    existingList = gson.fromJson(reader, listType);
                }
            }

            // Update or add doctors based on the ID
            for (Appointment updatedDoctor : updatedList) {
                boolean found = false;
                for (int i = 0; i < existingList.size(); i++) {
                    if (existingList.get(i).getId() == updatedDoctor.getId()) {
                        existingList.set(i, updatedDoctor); // Update the existing doctor
                        found = true;
                        break;
                    }
                }

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
    public static void deleteAppointment(int id) throws IOException {
        List<Appointment> doctors=Appointment.getAllAppointments();
        for (int i = doctors.size() - 1; i >= 0; i--) {
            if (doctors.get(i).getId() == id) {
                doctors.remove(i);
                break;
            }
        }
        Appointment.addAppointment((ArrayList<Appointment>) doctors);
    }
    public static void addAppointment(ArrayList<Appointment> list) {
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
    public static Appointment getAppointment(int id) throws IOException {
        Path filepath = Paths.get(FILE_PATH);

        Gson gson = new Gson();
        ArrayList<Appointment> existingList = new ArrayList<>();
        if (Files.size(filepath) > 0) { // Check if file is not empty
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<Appointment>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            }
        }
        for (Appointment appointment: existingList){
            if(appointment.getId()==id)
                return appointment;
        }
        return null;

    }

}
