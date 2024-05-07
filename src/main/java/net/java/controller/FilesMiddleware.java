package net.java.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.java.dao.CarDAO;
import net.java.model.Car;

public class FilesMiddleware {
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //fetch form data
            
            Part part = request.getPart("file");
            String fileName = part.getSubmittedFileName();
            
            String path = request.getServletContext().getRealPath("/"+"files"+File.separator+fileName);
            
            InputStream is = part.getInputStream();
            boolean test = uploadFile(is,path);
            if(test){
                out.println("uploaded "+path);
            }else{
            	System.out.println("something wrong");
            }
            
           
        }
    }

}
