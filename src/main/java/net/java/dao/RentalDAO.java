package net.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.java.model.Rental;
import config.ConnexionDb;

public class RentalDAO {
    private static final String INSERT_RENTAL_SQL = "INSERT INTO rental (user_id, registrationNumber, startDate, endDate, note) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_RENTAL_BY_ID = "SELECT user_id, registrationNumber, startDate, endDate, note FROM rental WHERE id=?";
    private static final String SELECT_ALL_RENTALS = "SELECT id,user_id, registrationNumber, startDate, endDate, note FROM rental";
    private static final String DELETE_RENTAL_SQL = "DELETE FROM rental WHERE id=?";
    private static final String UPDATE_RENTAL_SQL = "UPDATE rental SET user_id=? ,registrationNumber=?, startDate=?, endDate=?, note=? WHERE id=?";

    public RentalDAO() {
    }
    
    
    public List<Rental> searchRentalsForClient(String user_id, String keyword) {
        List<Rental> rentals = new ArrayList<>();
        String SEARCH_RENTALS_SQL = "SELECT id, registrationNumber, startDate, endDate, note FROM rental WHERE user_id=? AND (registrationNumber LIKE ? OR startDate LIKE ? OR endDate LIKE ? OR note LIKE ?)";
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_RENTALS_SQL)) {
            preparedStatement.setString(1, user_id);
            String searchKeyword = "%" + keyword + "%";
            for (int i = 2; i <= 5; i++) {
                preparedStatement.setString(i, searchKeyword);
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String registrationNumber = rs.getString("registrationNumber");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String note = rs.getString("note");
                rentals.add(new Rental(id, user_id, registrationNumber, startDate, endDate, note));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rentals;
    }
    
    
    
    

    public void insertRental(Rental rental) throws SQLException {
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RENTAL_SQL)) {
            preparedStatement.setString(1, rental.getUser_id());
            preparedStatement.setString(2, rental.getRegistrationNumber());
            preparedStatement.setString(3, rental.getStartDate());
            preparedStatement.setString(4, rental.getEndDate());
            preparedStatement.setString(5, rental.getNote());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Rental selectRental(String rental_id) {
        Rental rental = null;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RENTAL_BY_ID)) {
            preparedStatement.setString(1, rental_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String user_id = rs.getString("user_id");
                String registrationNumber = rs.getString("registrationNumber");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String note = rs.getString("note");
                
                rental = new Rental(rental_id, user_id, registrationNumber, startDate, endDate, note);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return rental;
    }
    
    public List<Rental> searchRentals(String keyword) {
        List<Rental> rentals = new ArrayList<>();
        
        String SEARCH_RENTALS_SQL = "SELECT id, user_id, registrationNumber, startDate, endDate, note FROM rental WHERE user_id LIKE ? OR registrationNumber LIKE ? OR startDate LIKE ? OR endDate LIKE ? OR note LIKE ?";
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_RENTALS_SQL)) {
            
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, searchKeyword);
            }
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String user_id = rs.getString("user_id");
                String registrationNumber = rs.getString("registrationNumber");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String note = rs.getString("note");
                rentals.add(new Rental(id, user_id, registrationNumber, startDate, endDate, note));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rentals;
    }



    public List<Rental> selectAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        try (Connection connection = ConnexionDb.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RENTALS)) {
               ResultSet rs = preparedStatement.executeQuery();
               while (rs.next()) {
            	   String id = rs.getString("id");
                   String user_id = rs.getString("user_id");
                   String registrationNumber = rs.getString("registrationNumber");
                   String startDate = rs.getString("startDate");
                   String endDate = rs.getString("endDate");
                   String note = rs.getString("note");
                   // Assuming your Rental class has a constructor with id as well
                   Rental rental = new Rental(id,user_id, registrationNumber, startDate, endDate, note);
            
                   rentals.add(rental);
               }
           } catch (SQLException e) {
               printSQLException(e);
               
           }
           return rentals;
    }


       public boolean deleteRental(String rental_id) throws SQLException {
           boolean rowDeleted;
           try (Connection connection = ConnexionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_RENTAL_SQL)) {
               statement.setString(1, rental_id);
               rowDeleted = statement.executeUpdate() > 0;
           }
           System.out.println("OK+");
           return rowDeleted;
       }

       public boolean updateRental(Rental rental) throws SQLException {
    	    boolean rowUpdated;
    	    
    	    try (Connection connection = ConnexionDb.getConnection();
    	         PreparedStatement statement = connection.prepareStatement(UPDATE_RENTAL_SQL)) {
    	        statement.setString(1, rental.getUser_id());
    	        statement.setString(2, rental.getRegistrationNumber());
    	        statement.setString(3, rental.getStartDate());
    	        statement.setString(4, rental.getEndDate());
    	        statement.setString(5, rental.getNote());
    	        statement.setString(6, rental.getId()); 
    	        
    	        // Print the rental object
    	        System.out.println("Updated Rental: " + rental.getId());
    	        
    	        rowUpdated = statement.executeUpdate() > 0;
    	    }
    	    return rowUpdated;
    	}
       public boolean isAvailable(String registrationNumber, String startDate, String endDate) {
    	    boolean isAvailable = true;
    	    try (Connection connection = ConnexionDb.getConnection();
    	         PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM rental WHERE registrationNumber = ? AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?))")) {
    	        preparedStatement.setString(1, registrationNumber);
    	        preparedStatement.setString(2, startDate);
    	        preparedStatement.setString(3, startDate);
    	        preparedStatement.setString(4, endDate);
    	        preparedStatement.setString(5, endDate);
    	        preparedStatement.setString(6, startDate);
    	        preparedStatement.setString(7, endDate);
    	        ResultSet rs = preparedStatement.executeQuery();
    	        if (rs.next()) {
    	            int count = rs.getInt("count");
    	            if (count > 0) {
    	                isAvailable = false; 
    	            }
    	        }
    	    } catch (SQLException e) {
    	        printSQLException(e);
    	    }
    	    return isAvailable;
    	}
       public List<Rental> selectAllRentalsForClient(String user_id) {
    	    List<Rental> rentals = new ArrayList<>();
    	    try (Connection connection = ConnexionDb.getConnection();
    	         PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, registrationNumber, startDate, endDate, note FROM rental WHERE user_id=?")) {
    	        preparedStatement.setString(1, user_id);
    	        ResultSet rs = preparedStatement.executeQuery();
    	        while (rs.next()) {
    	            String id = rs.getString("id");
    	            String registrationNumber = rs.getString("registrationNumber");
    	            String startDate = rs.getString("startDate");
    	            String endDate = rs.getString("endDate");
    	            String note = rs.getString("note");
    	            rentals.add(new Rental(id, user_id, registrationNumber, startDate, endDate, note));
    	        }
    	    } catch (SQLException e) {
    	        printSQLException(e);
    	    }
    	    return rentals;
    	}

       
       
       
       
       public boolean isAvailableForUpdate(String registrationNumber, String startDate, String endDate, String rentalId) {
    	    boolean isAvailable = true;
    	    try (Connection connection = ConnexionDb.getConnection();
    	         PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM rental WHERE registrationNumber = ? AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?)) AND id != ?")) {
    	        preparedStatement.setString(1, registrationNumber);
    	        preparedStatement.setString(2, startDate);
    	        preparedStatement.setString(3, startDate);
    	        preparedStatement.setString(4, endDate);
    	        preparedStatement.setString(5, endDate);
    	        preparedStatement.setString(6, startDate);
    	        preparedStatement.setString(7, endDate);
    	        preparedStatement.setString(8, rentalId);
    	        ResultSet rs = preparedStatement.executeQuery();
    	        if (rs.next()) {
    	            int count = rs.getInt("count");
    	            if (count > 0) {
    	                isAvailable = false; 
    	            }
    	        }
    	    } catch (SQLException e) {
    	        printSQLException(e);
    	    }
    	    return isAvailable;
    	}



       private void printSQLException(SQLException ex) {
           ex.printStackTrace();
       }
   }
