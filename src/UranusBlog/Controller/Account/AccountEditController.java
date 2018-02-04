package UranusBlog.Controller.Account;

import UranusBlog.DAO.AccountDAO;
import UranusBlog.DB.MySQLDatabase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class AccountEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int maxFileSize = 5 * 1024 * 1024;
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        Integer currentUserID = (Integer) session.getAttribute("userID");
        if( currentUserID == null || currentUserID == 0){
            out.print("\"result\":\"fail\",\"reason\":\"Please login before trying to get user info\"");
            return;
        }
        String targetUserIDRaw = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String middlename = "";
        String email = "";
        String nation = "";
        String description = "";
        String birthdayStr = "";
        String sysAvatarPath = null;
        String avatarPath = null;
        Boolean hasUploadedAvatar = false;
        Integer role = 2; // 2 for normal user

        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletContext context = req.getServletContext();
        String path = context.getRealPath("/static/image/Avatar/");
        String filePath = path;
        System.out.println(filePath);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(path));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(req);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            File file;
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    if(sizeInBytes == 0){
                        continue;
                    }
                    // Write the file
                    String finalName;
                    finalName = filePath + session.getId() + fileName.substring(fileName.lastIndexOf("."));
                    file = new File(finalName);
                    avatarPath = finalName;
                    fi.write(file);

                    // thumbnails
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File(finalName));
                        String thumbnailFile = finalName.replace(finalName.substring(finalName.lastIndexOf(".")), "_thumbnail.jpg");
                        if (img.getHeight() < 400 && img.getWidth() < 400) {

                            ImageIO.write(img, "jpg", new File(thumbnailFile));
                        } else {
                            double zoom = Math.max(1.0 * img.getHeight() / 400, 1.0 * img.getWidth() / 400);
                            int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
                            BufferedImage resizedImage = new BufferedImage((int) (img.getWidth() / zoom), (int) (img.getHeight() / zoom), type);
                            Graphics2D g = resizedImage.createGraphics();
                            g.drawImage(img, 0, 0, (int) (img.getWidth() / zoom), (int) (img.getHeight() / zoom), null);
                            g.dispose();
                            g.setComposite(AlphaComposite.Src);
                            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            ImageIO.write(resizedImage, "jpg", new File(thumbnailFile));
                        }
                        hasUploadedAvatar = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // get all data from form
                    String fieldName = fi.getFieldName();
                    if (fieldName.equals("password")) {
                        password = fi.getString();
                    } else if (fieldName.equals("firstname")) {
                        firstname = fi.getString();
                    } else if (fieldName.equals("lastname")) {
                        lastname = fi.getString();
                    } else if (fieldName.equals("middlename")) {
                        middlename = fi.getString();
                    } else if (fieldName.equals("email")) {
                        email = fi.getString();
                    } else if (fieldName.equals("nation")) {
                        nation = fi.getString();
                    } else if (fieldName.equals("birthday")) {
                        birthdayStr = fi.getString();
                    } else if (fieldName.equals("system_avatars")){
                        sysAvatarPath = "static/image/Avatar/" + fi.getString()+".png";
                    } else if (fieldName.equals("uid")){
                        targetUserIDRaw = fi.getString();
                    } else if (fieldName.equals("description")){
                        description = fi.getString();
                    }


                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (
                password == null || password.isEmpty() ||
                firstname == null || firstname.isEmpty() ||
                lastname == null || lastname.isEmpty() ||
                email == null || email.isEmpty() ||
                nation == null || nation.isEmpty() ||
                birthdayStr == null || birthdayStr.isEmpty()) {
            // missing area and send error message back
            out.print("\"result\":\"fail\",\"reason\":\"Some required fields are missing.\"");
            return;
        }

        if(hasUploadedAvatar) {
            if (avatarPath == null || avatarPath.isEmpty()) {
                // failed to save avatar file
                out.print("\"result\":\"fail\",\"reason\":\"Failed to save avatar file.\"");
                return;
            } else {
                avatarPath = avatarPath.substring(avatarPath.indexOf("static/image/Avatar/"));
            }
        } else {
            avatarPath = sysAvatarPath;
        }
        // validation data (e.g. length of username, etc.)

        // try some format
        LocalDate birthday = LocalDate.parse(birthdayStr);
        Integer targetUserID;
        if (targetUserIDRaw == null || targetUserIDRaw.isEmpty()){
            out.print("\"result\":\"fail\",\"reason\":\"Which account would you like to modify?\"");
            return;
        } else {
            targetUserID = Integer.parseInt(targetUserIDRaw);
        }

        if( !session.getAttribute("roleDetail").equals("admin") && !targetUserID.equals(currentUserID)){
            out.print("\"result\":\"fail\",\"reason\":\"No authorization to modify user info\"");
return;
        }

        // modify user info
        try (AccountDAO dao = new AccountDAO(new MySQLDatabase(getServletContext()))) {
            dao.modifyUser(targetUserID, password, firstname, lastname, middlename, email, birthday, nation, avatarPath,description);
            resp.sendRedirect("../main.html");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
