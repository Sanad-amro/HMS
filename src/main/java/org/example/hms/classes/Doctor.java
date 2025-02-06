package org.example.hms.classes;


import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private int roomId;

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/akram";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    private static final String CL_URL = "jdbc:mysql://195.123.166.125:3306/akram";
    private static final String CL_USER = "sanad";
    private static final String CL_PASSWORD = "sanad";
    private static Connection cloud() throws SQLException {
        return DriverManager.getConnection(CL_URL, CL_USER, CL_PASSWORD);
    }



    private static Connection getConnection() throws SQLException {
        System.out.println("Nigga");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public Doctor(String name, String id, String email, String address, boolean appointment_admin, boolean inventory_admin, String sector, boolean Doctors_admin, boolean Patients_admin, boolean staff_admin, String speciality, String userName, String password, String role, List<Integer> saturday, List<Integer> sunday, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday) {
        super(name, id, email, address, appointment_admin, inventory_admin, sector, Doctors_admin, Patients_admin, staff_admin, speciality, userName, password, role, saturday, sunday, monday, tuesday, wednesday, thursday, friday);
    }





    private static final String LOCAL_DB_URL = "jdbc:mysql://localhost:3306/akram";
    private static final String LOCAL_DB_USER = "root";
    private static final String LOCAL_DB_PASSWORD = "";

    // Get connection to the remote database
    private static Connection getRemoteConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Get connection to the local database
    private static Connection getLocalConnection() throws SQLException {
        return DriverManager.getConnection(LOCAL_DB_URL, LOCAL_DB_USER, LOCAL_DB_PASSWORD);
    }








    public static List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors";

        try (Connection conn = getRemoteConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getBoolean("appointment_admin"),
                        rs.getBoolean("inventory_admin"),
                        rs.getString("sector"),
                        rs.getBoolean("Doctors_admin"),
                        rs.getBoolean("Patients_admin"),
                        rs.getBoolean("staff_admin"),
                        rs.getString("speciality"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("role"),
                        getWorkDays(rs, "saturday"),
                        getWorkDays(rs, "sunday"),
                        getWorkDays(rs, "monday"),
                        getWorkDays(rs, "tuesday"),
                        getWorkDays(rs, "wednesday"),
                        getWorkDays(rs, "thursday"),
                        getWorkDays(rs, "friday")
                );
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving doctors: " + e.getMessage());
        }
        return doctors;
    }

    private static List<Integer> getWorkDays(ResultSet rs, String day) throws SQLException {
        // Implement logic to retrieve work days from the database
        return null; // Placeholder
    }

    public static void addDoctor(ArrayList<Doctor> doctors) {
        addDoctorC(doctors);
        Doctor doctor=doctors.get(0);
        String query = "INSERT INTO doctors (name, id, email, address, appointment_admin, inventory_admin, sector, Doctors_admin, Patients_admin, staff_admin, speciality, userName, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getId());
            pstmt.setString(3, doctor.getEmail());
            pstmt.setString(4, doctor.getAddress());
            pstmt.setBoolean(5, doctor.isAppointment_admin());
            pstmt.setBoolean(6, doctor.isInventory_admin());
            pstmt.setString(7, doctor.getSector());
            pstmt.setBoolean(8, doctor.isDoctors_admin());
            pstmt.setBoolean(9, doctor.isPatients_admin());
            pstmt.setBoolean(10, doctor.isStaff_admin());
            pstmt.setString(11, doctor.getSpeciality());
            pstmt.setString(12, doctor.getUserName());
            pstmt.setString(13, doctor.getPassword());
            pstmt.setString(14, doctor.getRole());

            pstmt.executeUpdate();
            System.out.println("Doctor added successfully.");

        } catch (SQLException e) {
            System.err.println("Error while adding doctor: " + e.getMessage());
        }
    }


    public static void addDoctorC(ArrayList<Doctor> doctors) {
        Doctor doctor=doctors.get(0);
        String query = "INSERT INTO doctors (name, id, email, address, appointment_admin, inventory_admin, sector, Doctors_admin, Patients_admin, staff_admin, speciality, userName, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = cloud();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getId());
            pstmt.setString(3, doctor.getEmail());
            pstmt.setString(4, doctor.getAddress());
            pstmt.setBoolean(5, doctor.isAppointment_admin());
            pstmt.setBoolean(6, doctor.isInventory_admin());
            pstmt.setString(7, doctor.getSector());
            pstmt.setBoolean(8, doctor.isDoctors_admin());
            pstmt.setBoolean(9, doctor.isPatients_admin());
            pstmt.setBoolean(10, doctor.isStaff_admin());
            pstmt.setString(11, doctor.getSpeciality());
            pstmt.setString(12, doctor.getUserName());
            pstmt.setString(13, doctor.getPassword());
            pstmt.setString(14, doctor.getRole());

            pstmt.executeUpdate();
            System.out.println("Doctor added successfully.");

        } catch (SQLException e) {
            System.err.println("Error while adding doctor: " + e.getMessage());
        }
    }

    public static void updateDoctor(Doctor doctor) {
        updateDoctorC(doctor);
        String query = "UPDATE doctors SET name=?, email=?, address=?, appointment_admin=?, inventory_admin=?, sector=?, Doctors_admin=?, Patients_admin=?, staff_admin=?, speciality=?, userName=?, password=?, role=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getEmail());
            pstmt.setString(3, doctor.getAddress());
            pstmt.setBoolean(4, doctor.isAppointment_admin());
            pstmt.setBoolean(5, doctor.isInventory_admin());
            pstmt.setString(6, doctor.getSector());
            pstmt.setBoolean(7, doctor.isDoctors_admin());
            pstmt.setBoolean(8, doctor.isPatients_admin());
            pstmt.setBoolean(9, doctor.isStaff_admin());
            pstmt.setString(10, doctor.getSpeciality());
            pstmt.setString(11, doctor.getUserName());
            pstmt.setString(12, doctor.getPassword());
            pstmt.setString(13, doctor.getRole());
            pstmt.setString(14, doctor.getId());

            pstmt.executeUpdate();
            System.out.println("Doctor updated successfully.");

        } catch (SQLException e) {
            System.err.println("Error while updating doctor: " + e.getMessage());
        }
    }
    public static void updateDoctorC(Doctor doctor) {
        String query = "UPDATE doctors SET name=?, email=?, address=?, appointment_admin=?, inventory_admin=?, sector=?, Doctors_admin=?, Patients_admin=?, staff_admin=?, speciality=?, userName=?, password=?, role=? WHERE id=?";

        try (Connection conn = cloud();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getEmail());
            pstmt.setString(3, doctor.getAddress());
            pstmt.setBoolean(4, doctor.isAppointment_admin());
            pstmt.setBoolean(5, doctor.isInventory_admin());
            pstmt.setString(6, doctor.getSector());
            pstmt.setBoolean(7, doctor.isDoctors_admin());
            pstmt.setBoolean(8, doctor.isPatients_admin());
            pstmt.setBoolean(9, doctor.isStaff_admin());
            pstmt.setString(10, doctor.getSpeciality());
            pstmt.setString(11, doctor.getUserName());
            pstmt.setString(12, doctor.getPassword());
            pstmt.setString(13, doctor.getRole());
            pstmt.setString(14, doctor.getId());

            pstmt.executeUpdate();
            System.out.println("Doctor updated successfully.");

        } catch (SQLException e) {
            System.err.println("Error while updating doctor: " + e.getMessage());
        }
    }


    public static Doctor getDoctor(String username) {
        String query = "SELECT * FROM doctors WHERE userName=?";
        Doctor doctor = null;

        try (Connection conn = getRemoteConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor(
                            rs.getString("name"),
                            rs.getString("id"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getBoolean("appointment_admin"),
                            rs.getBoolean("inventory_admin"),
                            rs.getString("sector"),
                            rs.getBoolean("Doctors_admin"),
                            rs.getBoolean("Patients_admin"),
                            rs.getBoolean("staff_admin"),
                            rs.getString("speciality"),
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("role"),
                            getWorkDays(rs, "saturday"),
                            getWorkDays(rs, "sunday"),
                            getWorkDays(rs, "monday"),
                            getWorkDays(rs, "tuesday"),
                            getWorkDays(rs, "wednesday"),
                            getWorkDays(rs, "thursday"),
                            getWorkDays(rs, "friday")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving doctor: " + e.getMessage());
        }
        return doctor;
    }

    public static boolean doesUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM doctors WHERE userName=?";
        try (Connection conn = getRemoteConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking username: " + e.getMessage());
        }
        return false;
    }

    public static boolean doesIdExists(String id) {
        String query = "SELECT COUNT(*) FROM doctors WHERE id=?";
        try (Connection conn = getRemoteConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking doctor ID: " + e.getMessage());
        }
        return false;
    }

    public static Doctor getDoctorById(String id) {
        String query = "SELECT * FROM doctors WHERE id=?";
        Doctor doctor = null;

        try (Connection conn = getLocalConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor(
                            rs.getString("name"),
                            rs.getString("id"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getBoolean("appointment_admin"),
                            rs.getBoolean("inventory_admin"),
                            rs.getString("sector"),
                            rs.getBoolean("Doctors_admin"),
                            rs.getBoolean("Patients_admin"),
                            rs.getBoolean("staff_admin"),
                            rs.getString("speciality"),
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("role"),
                            getWorkDays(rs, "saturday"),
                            getWorkDays(rs, "sunday"),
                            getWorkDays(rs, "monday"),
                            getWorkDays(rs, "tuesday"),
                            getWorkDays(rs, "wednesday"),
                            getWorkDays(rs, "thursday"),
                            getWorkDays(rs, "friday")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving doctor: " + e.getMessage());
        }
        return doctor;
    }

    public static void deleteDoctor(String id) {
        deleteDoctorC(id);
        String query = "DELETE FROM doctors WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Doctor deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error while deleting doctor: " + e.getMessage());
        }
    }

    public static void deleteDoctorC(String id) {
        String query = "DELETE FROM doctors WHERE id=?";

        try (Connection conn = cloud();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Doctor deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error while deleting doctor: " + e.getMessage());
        }
    }

    public static void UpdateDoctorInfo(ArrayList<Doctor> doctors) {
        Doctor doctor=doctors.get(0);

        updateDoctor(doctor);
    }


}
