package UranusBlog.Controller.Account;

import UranusBlog.DAO.AccountDAO;
import UranusBlog.DB.MySQLDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountDeleteController extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        Integer currentUserID = (Integer) session.getAttribute("userID");
        if( currentUserID == null || currentUserID == 0){
            out.print("\"result\":\"fail\",\"reason\":\"Please login before operating\"");
            return;
        }

        String userIDStr = req.getParameter("uid");
        if(userIDStr == null || userIDStr.isEmpty() || !userIDStr.matches("^[+-]?\\d+$")){
            out.print("{\"result\":\"fail\", \"reason\":\"No valid user ID provided\"}");
            return;
        }
        int targetUserID = Integer.parseInt(userIDStr);

        String roleDetail = (String) session.getAttribute("roleDetail");
        if(targetUserID != currentUserID && (roleDetail == null || !roleDetail.equals("admin"))){
            out.print("{\"result\":\"fail\", \"reason\":\"No authorization to operate\"}");

        } else {
            try (AccountDAO dao = new AccountDAO(new MySQLDatabase(getServletContext()))) {
                dao.deleteUser(targetUserID);
                session.invalidate();
                out.print("{\"result\":\"success\"}");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
