package net.java.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.java.dao.CarDAO;
import net.java.model.Car;
import net.java.model.User;

public class CarMiddleware {


	public void listCars(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    CarDAO carDAO = new CarDAO();
	    
	   
	    String keyword = request.getParameter("keyword");
	    
	    List<Car> cars;
	    
	    if (keyword != null && !keyword.isEmpty()) {
	        
	        cars = carDAO.searchCars(keyword);
	    } else {
	        
	        cars = carDAO.selectAllCars();
	    }
	    
	    request.setAttribute("cars", cars);
	  
	    RequestDispatcher dispatcher = request.getRequestDispatcher("cars-list.jsp");
	    dispatcher.forward(request, response);
	}


    public void showNewCarForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("car-form.jsp");
        dispatcher.forward(request, response);
    }

    public void showEditCarForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        CarDAO carDAO = new CarDAO();
        String registrationNumber = request.getParameter("registrationNumber");
        Car existingCar = carDAO.selectCar(registrationNumber);
        RequestDispatcher dispatcher = request.getRequestDispatcher("car-update.jsp");
        request.setAttribute("car", existingCar);
        dispatcher.forward(request, response);
    }

    public void insertCar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException ,ServletException{
        String registrationNumber = request.getParameter("registrationNumber");
        String model = request.getParameter("model");
        String manufacturer = request.getParameter("manufacturer");
        String year = request.getParameter("year");
        String color = request.getParameter("color");

        Car newCar = new Car(registrationNumber, model, manufacturer, year, color);
        CarDAO carDAO = new CarDAO();
        try {
        	carDAO.insertCar(newCar);
        	
        } catch(SQLException e) {
        	  if (e.getErrorCode() == 1062) {
        	        request.setAttribute("errorMessage", "A car with this registration number already exists.");
        	    } else {
        	        request.setAttribute("errorMessage", e.getMessage());
        	    }
        	    RequestDispatcher dispatcher = request.getRequestDispatcher("car-form.jsp");
        	    dispatcher.forward(request, response);
        	    return;
        
        	
        }
        
        response.sendRedirect("cars-list");
    }

    public void updateCar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String registrationNumber = request.getParameter("registrationNumber");
        String model = request.getParameter("model");
        String manufacturer = request.getParameter("manufacturer");
        String year = request.getParameter("year");
        String color = request.getParameter("color");

        Car updatedCar = new Car(registrationNumber, model, manufacturer, year, color);
        CarDAO carDAO = new CarDAO();
        try {
            carDAO.updateCar(updatedCar);
            response.sendRedirect("cars-list");
        } catch(SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            
            Car existingCar = carDAO.selectCar(registrationNumber);
            request.setAttribute("car", existingCar);
            
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("car-update.jsp");
            dispatcher.forward(request, response);
        }
    }


    public void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String registrationNumber = request.getParameter("registrationNumber");
        CarDAO carDAO = new CarDAO();
        carDAO.deleteCar(registrationNumber);
        response.sendRedirect("cars-list");
    }
}
