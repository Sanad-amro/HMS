package org.example.hms.classes;

import java.sql.*;
import java.util.ArrayList;

public class Transactions {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double quantity;
    private String name;
    private String d_name;
    private int yy,mm,dd;
    private String patient_name;

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public Transactions(double quantity, String name, String d_name, int yy, int mm, int dd, String patient_name) {
        this.quantity = quantity;
        this.name = name;
        this.d_name = d_name;
        this.yy = yy;
        this.mm = mm;
        this.dd = dd;
        this.patient_name=patient_name;
    }
    public Transactions(int id, double quantity, String name, String d_name, int yy, int mm, int dd, String patient_name) {
        this.id=id;
        this.quantity = quantity;
        this.name = name;
        this.d_name = d_name;
        this.yy = yy;
        this.mm = mm;
        this.dd = dd;
        this.patient_name=patient_name;
    }


    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/akram";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public static void makeTransaciton(ArrayList<Transactions> transactions){
        for(Transactions transaction: transactions){
            String sql = "insert into transactions (quantity, name , d_name, yy,mm,dd,p_name) values (?, ?, ?,?,?,?,?) ";
            try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, transaction.getQuantity());
                statement.setString(2, transaction.getName());
                statement.setString(3,transaction.getD_name());
                statement.setInt(4,transaction.getYy());
                statement.setInt(5, transaction.getMm());
                statement.setInt(6,transaction.getDd());
                statement.setString(7, transaction.getPatient_name());
                statement.executeUpdate();
                System.out.println("the executeUpdate was performed!!");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("transactions were made successfully! ");
    }
    public static ArrayList<Transactions> getAllTransactions(){
        ArrayList<Transactions> transactions= new ArrayList<>();
        String sql =  "select * from transactions";
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()){
                Transactions transactions1 = new Transactions(resultSet.getInt("id"), resultSet.getDouble("quantity"), resultSet.getString("name"), resultSet.getString("d_name"), resultSet.getInt("yy"), resultSet.getInt("mm"), resultSet.getInt("dd"),resultSet.getString("p_name"));
                transactions.add(transactions1);
            }
            System.out.println("I was fucking here okey!!!!,?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }
    public String getDate(){
        return String.valueOf(yy)+"/"+String.valueOf(mm)+"/"+String.valueOf(dd);
    }
}
