package UranusBlog.Controller.Account;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();


    }

    //    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //super.doPost(req, resp);
//
//        //test case delete after testing
//        //PrintWriter out = resp.getWriter();
//        //String filePath;
//        int maxFileSize = 5 * 1024 * 1024;
//
//
//        ServletContext servletContext = getServletContext();
//        String fullPhotoPath = servletContext.getRealPath("/Photos");
//        String avitar_path = "";
//
////        boolean isMultipart;
////        isMultipart = ServletFileUpload.isMultipartContent(request);
//        DiskFileItemFactory factory = new DiskFileItemFactory();
////        // maximum size that will be stored in memory
////        factory.setSizeThreshold(maxFileSize);
////
////        // Location to save data that is larger than maxMemSize.
////        factory.setRepository(new File("c:\\temp"));
//
//        // Create a new file upload handler
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // maximum file size to be uploaded.
//        upload.setSizeMax(maxFileSize);
//
//        try {
//            List fileItems = upload.parseRequest(req);
//            Iterator i = fileItems.iterator();
//            while (i.hasNext()) {
//                FileItem fi = (FileItem) i.next();
//                if (!fi.isFormField()) {
//                    // Get the uploaded file parameters
//                    String fieldName = fi.getFieldName();
//                    String fileName = fi.getName();
//                    String contentType = fi.getContentType();
//                    boolean isInMemory = fi.isInMemory();
//                    long sizeInBytes = fi.getSize();
//
//                    // Write the file
//                    File file;
//                    if (fileName.lastIndexOf("\\") >= 0) {
//                        file = new File(fullPhotoPath + "\\" + fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf('.')) + "fullsize" + fileName.substring(fileName.lastIndexOf(".")));
//                        avitar_path = fullPhotoPath + "\\" + fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf('.')) + "fullsize" + fileName.substring(fileName.lastIndexOf("."));
//                    } else {
//                        file = new File(fullPhotoPath + "\\" + fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.lastIndexOf('.')) + "fullsize" + fileName.substring(fileName.lastIndexOf(".")));
//                        avitar_path = fullPhotoPath + "\\" + fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf('.')) + "fullsize" + fileName.substring(fileName.lastIndexOf("."));
//                    }
//                    fi.write(file);
//                }
//            }
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        String username = req.getParameter("userName");
//        String emailaddress = req.getParameter("email");
//        String password = req.getParameter("password");
//        String firstName = req.getParameter("fname");
//        String lastName = req.getParameter("lname");
//        String middleName = req.getParameter("mname");
//        String birthday = req.getParameter("bday");
//        String nation = req.getParameter("nation");
//
//
//        boolean authorized = false;
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        String preparedStatment = "INSERT INTO account (username, password, firstname, lastname, middlename,email, birthday, nation, avatar_path, role) VALUES (?,?,?,?,?,?,?,?,?,?)";
//        Properties dbProps = new Properties();
//        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
//        dbProps.setProperty("user", "xliu617");
//        dbProps.setProperty("password", "123");
//        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
//            try (PreparedStatement stmt = conn.prepareStatement("SELECT username FROM account WHERE username = ?")) {
//                stmt.setString(1, username);
//                try (ResultSet r = stmt.executeQuery()) {
//                    if (r == null) {
//                        try (PreparedStatement stmt1 = conn.prepareStatement(preparedStatment)) {
//                            stmt1.setString(1, username);
//                            stmt1.setString(2, password);
//                            stmt1.setString(3, firstName);
////                            "Hi James"
//                            stmt1.setString(4, lastName);
//                            stmt1.setString(5, middleName);
//                            stmt1.setString(6, emailaddress);
//                            stmt1.setString(7, birthday);
//                            stmt1.setString(8, nation);
//                            stmt1.setString(9, avitar_path);
//                            stmt1.setInt(10, 2);
//
//                        }
//                    }
//
//
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}