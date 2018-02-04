package UranusBlog.Controller.Account;

import UranusBlog.DAO.AccountDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Account;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class AccountInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        String targetUserIDRaw = req.getParameter("uid");
        Integer currentUserID = (Integer) session.getAttribute("userID");
        if( currentUserID == null || currentUserID == 0){
            out.print("\"result\":\"fail\",\"reason\":\"Please login before trying to get user info\"");
            return;
        }
        Integer targetUserID;
        if (targetUserIDRaw == null || targetUserIDRaw.isEmpty()){
            targetUserID = currentUserID;
        } else {
            targetUserID = Integer.parseInt(targetUserIDRaw);
        }
        if(session.getAttribute("roleDetail").equals("admin") || targetUserID.equals(currentUserID)){
            try (AccountDAO dao = new AccountDAO(new MySQLDatabase(getServletContext()))) {
                Account account = dao.getUserById(targetUserID);
                if(account != null)
                    out.print(constructReturnData(account));
                else
                    out.print("\"result\":\"fail\",\"reason\":\"No user found\"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject constructReturnData(Account account){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userID", account.getUserId());
        jsonObject.put("username", account.getUsername());
        jsonObject.put("firstname", account.getFirstname());
        jsonObject.put("lastname", account.getLastname());
        jsonObject.put("middlename", account.getMiddlename());
        jsonObject.put("email", account.getEmail());
        jsonObject.put("nation", account.getNation());
        jsonObject.put("birthday", account.getBirthday().toLocalDate().format(DateTimeFormatter.ISO_DATE));
        return jsonObject;
    }
}
