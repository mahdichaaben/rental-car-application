package net.java.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import net.java.dao.CarDAO;
import net.java.dao.RentalDAO;
import net.java.model.Car;
import net.java.model.Rental;
import net.java.model.User;




@MultipartConfig(    fileSizeThreshold = 1024 * 1024 * 2,  // 2 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 50   )  // 50 MB)
@WebServlet(urlPatterns = {"/"})
public class GlobalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthMiddleware authMiddleware;
    private CarMiddleware carMiddleware;
    private RentalMiddleware rentalMiddleware;

    public void init() {
        authMiddleware = new AuthMiddleware();
        carMiddleware = new CarMiddleware();
        rentalMiddleware = new RentalMiddleware();
       
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.print("hello");

        try {
            switch (action) {
            case "/signup":
                authMiddleware.showRegisterForm(request, response);
                break;
            case "/registerUser":
                authMiddleware.registerUser(request, response);
                break;
            case "/checkuser":
                authMiddleware.checkuser(request, response);
                break;
            case "/signin":
            	 if (authMiddleware.isAuthenticated(request)) {
            	        response.sendRedirect("home"); // Redirect to home page if already authenticated
            	    } else {
            	        authMiddleware.showLoginForm(request, response);
            	    }
            	 break;
               
            case "/car-form":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.showNewCarForm(request, response);
                } else {
                    response.sendRedirect("login");
                }
                break;
            case "/insertcar":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.insertCar(request, response);
                } else {
                    response.sendRedirect("login");
                }
                break;
            case "/deletecar":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.deleteCar(request, response);
                } else {
                    response.sendRedirect("login");
                }
                break;
            case "/editcar":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.showEditCarForm(request, response);
                } else {
                    response.sendRedirect("home");
                }
                break;
            case "/updatecar":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.updateCar(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/cars-list":
                if (authMiddleware.isAuthenticated(request)) {
                    carMiddleware.listCars(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/newrental":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.showNewRentalForm(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/insertrental":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.insertRental(request, response);
                } else {
                    response.sendRedirect("login");
                }
                break;
            case "/deleterental":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.deleteRental(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/editrental":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.showEditRentalForm(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/rental-update":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.updateRental(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/rentals-list":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.listRentals(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/home":
                showHome(request, response);
                break;
            case "/logout":
                authMiddleware.logout(request, response);
                break;
            case "/account-settings":
                if (authMiddleware.isAuthenticated(request)) {
                	authMiddleware.accountsetting(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
                
                
            case "/rental-form":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.showNewRentalForm(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/insertRental":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.insertRental(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/myrentals":
                if (authMiddleware.isAuthenticated(request)) {
                    rentalMiddleware.listRentalsForClient(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            case "/updateUserInfo":
                if (authMiddleware.isAuthenticated(request)) {
                	authMiddleware.updateUserInfo(request, response);
                } else {
                    response.sendRedirect("signin");
                }
                break;
            default :
                showHome(request, response);
                break;
            	


                	
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        
        
        
        
        

        
        
        
        
  
        
        

        
        
    }
    
    public boolean uploadFile(InputStream is, String path){
        boolean test = false;
        try{
            byte[] byt = new byte[is.available()];
            is.read();
            
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            
            test = true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return test;
    }
    
    
    
    
    
    public void showHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,IOException, SQLException {
       
    	    CarDAO carDAO = new CarDAO();
    	    
    	   
    	    String keyword = request.getParameter("keyword");
    	    
    	    List<Car> cars;
    	    
    	    if (keyword != null && !keyword.isEmpty()) {
    	        
    	        cars = carDAO.searchCars(keyword);
    	    } else {
    	        
    	        cars = carDAO.selectAllCars();
    	    }
    	    
    	    request.setAttribute("cars", cars);
    	    RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
    	    dispatcher.forward(request, response);
    }
    
    
    
    
    
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Fetch form data
            Part part = request.getPart("file");
            
            // Generate a unique file name to avoid overwriting existing files
            String fileName = UUID.randomUUID().toString() + "_" + part.getSubmittedFileName();
            
            // Specify the directory where you want to save the uploaded file (relative path)
            String uploadDirectory = "C:\\isims\\d-piim\\sem2\\architecture logiciel\\JEE\\mvc_tp4\\files";
            // Create the full path to the file
            String fullPath = uploadDirectory +'\\' + fileName;
            response.getWriter().print(fullPath);
            // Write the uploaded file to the specified directory
            part.write(fullPath);
      
        } catch (IOException | ServletException e) {
        	
            // Handle exceptions
        	
            e.printStackTrace();
            response.getWriter().print("Error occurred while uploading the file.");
        }
    }
    
    
    
    
    
    
}
