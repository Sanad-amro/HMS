package org.example.hms.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Bill {
    private double bill;
    private String insuranceName;
    private double insuranceDiscount;
    private double billAfterInsurance;
    private double payed;
    private double remaining;
    private int patientId;
    private String patientName;
    private int BillId;
    private String BillName;
    private boolean paid;

    public Bill( double bill, String insuranceName, double insuranceCover, double billAfterInsurance, double payed, double remaining, int patientId, String patientName, int BillId, String BillName, boolean paid) {
        this.bill = bill;
        this.insuranceName = insuranceName;
        this.insuranceDiscount = insuranceCover;
        this.billAfterInsurance = billAfterInsurance;
        this.payed = payed;
        this.remaining = remaining;
        this.patientId = patientId;
        this.patientName = patientName;
        this.BillId = BillId;
        this.BillName = BillName;
        this.paid = paid;
    }


    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public double getInsuranceDiscount() {
        return insuranceDiscount;
    }

    public void setInsuranceDiscount(double insuranceDiscount) {
        this.insuranceDiscount = insuranceDiscount;
    }

    public double getBillAfterInsurance() {
        return billAfterInsurance;
    }

    public void setBillAfterInsurance(double billAfterInsurance) {
        this.billAfterInsurance = billAfterInsurance;
    }

    public double getPayed() {
        return payed;
    }

    public void setPayed(double payed) {
        this.payed = payed;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getBillId() {
        return BillId;
    }

    public void setBillId(int BillId) {
        this.BillId = BillId;
    }

    public String getBillName() {
        return BillName;
    }

    public void setBillName(String BillName) {
        this.BillName = BillName;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    private static final String FILE_PATH = "src/main/java/org/example/hms/dataBase/billsInfo.json";


    public static void addOrUpdateBill(ArrayList<Bill> updatedList) {
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
            ArrayList<Bill> existingList = new ArrayList<>();
            if (Files.size(filepath) > 0) { // Check if file is not empty
                try (FileReader reader = new FileReader(filepath.toFile())) {
                    Type listType = new TypeToken<ArrayList<Bill>>() {}.getType();
                    existingList = gson.fromJson(reader, listType);
                }
            }

            // Update or add Bills based on the ID
            for (Bill updatedBill : updatedList) {
                boolean found = false;
                for (int i = 0; i < existingList.size(); i++) {
                    if (existingList.get(i).getBillId() == updatedBill.getBillId()) {
                        existingList.set(i, updatedBill); // Update the existing Bill
                        found = true;
                        break;
                    }
                }

                // If the Bill is not found, add the updated Bill to the list
                if (!found) {
                    existingList.add(updatedBill);
                }
            }

            // Write the updated list back to the JSON file
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                gson.toJson(existingList, writer);
                System.out.println("Updated ArrayList has been written to " + filepath + " successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error while handling the file: " + e.getMessage());
            throw new RuntimeException("An error occurred while updating the Bill data.", e);
        }
    }

    public static int genarateId() throws IOException {

        Path filepath = Paths.get(FILE_PATH);
        if (!Files.exists(filepath)) {
            Files.createFile(filepath);
            return 1;
        }
        Gson gson = new Gson();
        ArrayList<Bill> existingList = new ArrayList<>();
        if (Files.size(filepath) > 0) { // Check if file is not empty
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<Bill>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
                return existingList.size()+1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return 1;
    }

    public void payBill(int id,double amount) throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        Gson gson= new Gson();
        ArrayList<Bill> bills= new ArrayList<>();
        if (Files.size(filepath)>0){
            try (FileReader reader = new FileReader(filepath.toFile())){
                Type listType = new TypeToken<ArrayList<Bill>>(){}.getType();
                bills= gson.fromJson(reader, listType);
            }
        }
        int i=0;
        for(Bill bill: bills){

            if(bill.getBillId()==id){
                bills.get(i).setRemaining(bills.get(i).getRemaining()- amount);
                if (bills.get(i).getRemaining()==0)
                    bills.get(i).setPaid(true);

            }
            i++;
        }
    }

    public static Bill getBill(int id) throws IOException {
        Path filepath = Paths.get(FILE_PATH);
        Gson gson = new Gson();
        ArrayList<Bill> existingList = new ArrayList<>();
        if (Files.size(filepath) > 0) { // Check if file is not empty
            try (FileReader reader = new FileReader(filepath.toFile())) {
                Type listType = new TypeToken<ArrayList<Bill>>() {}.getType();
                existingList = gson.fromJson(reader, listType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(Bill bill: existingList){

            if(bill.getBillId()==id){
                return bill;
            }

        }
        return null;
    }
}
