package net.java.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.java.dao.CarDAO;
import net.java.dao.RentalDAO;
import net.java.model.Car;
import net.java.model.Rental;
import net.java.model.User;

public class RentalMiddleware {
	
	  public void listRentalsForClient(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        RentalDAO rentalDAO = new RentalDAO();
	       
	        HttpSession session = request.getSession();
	        User authenticatedUser = (User) session.getAttribute("user");
	        String user_id=authenticatedUser.getId();
	        String keyword = request.getParameter("keyword");

	        List<Rental> rentals;

	        if (keyword != null && !keyword.isEmpty()) {
	            rentals = rentalDAO.searchRentalsForClient(user_id, keyword);
	        } else {
	            rentals = rentalDAO.selectAllRentalsForClient(user_id);
	        }

	        request.setAttribute("rentals", rentals);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("myrentals.jsp");
	        dispatcher.forward(request, response);
	    }


	public void listRentals(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    RentalDAO rentalDAO = new RentalDAO();
	   

	    String keyword = request.getParameter("keyword");

	    List<Rental> rentals;

	    if (keyword != null && !keyword.isEmpty()) {
	        rentals = rentalDAO.searchRentals(keyword);
	    } else {
	        rentals = rentalDAO.selectAllRentals();
	    }

	    request.setAttribute("rentals", rentals);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("rentals-list.jsp");
	    dispatcher.forward(request, response);
	}

    public void showNewRentalForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String registrationNumber = request.getParameter("registrationNumber");
        CarDAO carDAO = new CarDAO();
        Car car = carDAO.selectCar(registrationNumber);
  
        if (car != null) {
            request.setAttribute("car", car);
            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-form.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle the case when the car is not found
            // For example, redirect to an error page or show an error message
            response.sendRedirect("error.jsp");
        }
    }

    public void showEditRentalForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	
        RentalDAO rentalDAO = new RentalDAO();
        String rental_id = request.getParameter("rentalid");
        Rental existingRental = rentalDAO.selectRental(rental_id);
        request.setAttribute("rental", existingRental);
        RequestDispatcher dispatcher = request.getRequestDispatcher("rental-update.jsp");
        dispatcher.forward(request, response);
    }

    public void insertRental(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	 
        RentalDAO rentalDAO = new RentalDAO();
        String id="test";
        String user_id = request.getParameter("user_id");
        String registrationNumber = request.getParameter("registrationNumber");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String note = request.getParameter("note");
        
        System.out.println("New rental details:");
        System.out.println("User ID: " + user_id);
        System.out.println("Registration Number: " + registrationNumber);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Note: " + note);
        
        
     // Check if start date is after end date
        if (startDate.compareTo(endDate) > 0) {
            String errorMessage = "Error occurred while updating rental: Start date must be before or equal to end date.";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        
        if (!rentalDAO.isAvailable(registrationNumber, startDate, endDate)) {
            String errorMessage = "Error occurred while inserting rental: The car is not available during the specified period.";
            
            
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        Rental newRental = new Rental(id,user_id, registrationNumber, startDate, endDate, note);
        try {
            rentalDAO.insertRental(newRental);
            response.sendRedirect("listrentals");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-form.jsp");
            dispatcher.forward(request, response);
        }
    }


    public void updateRental(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String rental_id = request.getParameter("rentalid");
        System.out.println(rental_id);
        RentalDAO rentalDAO = new RentalDAO();
        Rental existingRental = rentalDAO.selectRental(rental_id);
        String user_id = request.getParameter("user_id");
        String registrationNumber = request.getParameter("registrationNumber");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String note = request.getParameter("note");

        // Check if start date is after end date
        if (startDate.compareTo(endDate) > 0) {
            String errorMessage = "Error occurred while updating rental: Start date must be before or equal to end date.";
            request.setAttribute("errorMessage", errorMessage);
            
            // Retrieve the existing Rental for the given rental_id
            
        
            request.setAttribute("rental", existingRental);

            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-update.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        
        if (!rentalDAO.isAvailableForUpdate( rental_id,registrationNumber, startDate, endDate)) {
            String errorMessage = "Error occurred while inserting rental: The car is not available during the specified period.";
            
            request.setAttribute("rental", existingRental);
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Create the updated Rental object
        Rental updatedRental = new Rental(rental_id, user_id, registrationNumber, startDate, endDate, note);
        
        System.out.println("Updated Rental ID: " + updatedRental.getId());
        System.out.println("User ID: " + updatedRental.getUser_id());
        System.out.println("Registration Number: " + updatedRental.getRegistrationNumber());
        System.out.println("Start Date: " + updatedRental.getStartDate());
        System.out.println("End Date: " + updatedRental.getEndDate());
        System.out.println("Note: " + updatedRental.getNote());
        
      
        try {
            rentalDAO.updateRental(updatedRental);
            response.sendRedirect("rentals-list");
        } catch (SQLException e) {
            String errorMessage = "Error occurred while updating rental: ";

            // Check specific SQL error codes or message patterns to provide more informative error messages
            if (e.getSQLState().equals("23000")) { // Constraint violation (e.g., unique key violation)
                errorMessage += "Duplicate registration number or invalid user ID.";
            } else if (e.getSQLState().equals("22001")) { // Data too long for column
                errorMessage += "Data too long for one of the fields.";
            } else { // Default error message for other SQL exceptions
                errorMessage += "Database error: " + e.getMessage();
            }

            request.setAttribute("errorMessage", errorMessage);

          
            request.setAttribute("rental", existingRental);

            RequestDispatcher dispatcher = request.getRequestDispatcher("rental-update.jsp");
            dispatcher.forward(request, response);
        }
    }


    public void deleteRental(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        RentalDAO rentalDAO = new RentalDAO();
        String rental_id = request.getParameter("rentalid");
        System.out.println(rental_id);
        try {
           boolean t= rentalDAO.deleteRental(rental_id);
           if(t) {
        	   response.sendRedirect("rentals-list");
        	   
           }else {
        	   request.setAttribute("errorMessage", "Error deleting rental");
        	   
        	   
           }
            
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error deleting rental: " + e.getMessage());
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("rentals-list.jsp");
            dispatcher.forward(request, response);
        }
}
}
