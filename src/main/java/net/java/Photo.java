package net.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.OutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/image")
public class Photo extends HttpServlet {    
private static final long serialVersionUID = 1L;

private static final String UPLOAD_DIR = "uploads";

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the image path parameter
        String imagePath = request.getParameter("imagePath");

        // Validate the path (optional)
        // You can add checks to ensure the path doesn't access unauthorized locations

        // Get a handle to the image file (replace with your logic)
        String appPath = "C:\\isims\\d-piim\\sem2\\architecture logiciel\\JEE\\mvc_tp4";
       
        // Define the directory to save the uploaded file
        String uploadDirPath = appPath + File.separator + UPLOAD_DIR;

        // Construct the full path based on the parameter and base path
        File imageFile = new File(uploadDirPath + imagePath);

        // Check if the file exists
        if (!imageFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Image not found!");
            return;
        }

        // Set the content type based on the image format (optional)
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType != null) {
            response.setContentType(mimeType);
        } else {
            response.setContentType("image/jpeg");  // Default to JPEG
        }

        // Write the image data to the response
        try (InputStream inputStream = new FileInputStream(imageFile);
             OutputStream outputStream = response.getOutputStream()) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error serving image!");
            return;
        }
    }
}