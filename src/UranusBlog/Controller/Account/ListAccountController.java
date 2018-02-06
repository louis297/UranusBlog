package UranusBlog.Controller.Account;

import UranusBlog.DAO.AccountDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Account;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ListAccountController extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doPost(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("ListAccountControllerActivated");
            HttpSession session = req.getSession(true);
            //int roleID = Integer.parseInt(session.getAttribute("roleID").toString());
            PrintWriter out = resp.getWriter();
            //if (roleID == 0) {
                try (AccountDAO dao = new AccountDAO(new MySQLDatabase(getServletContext()))) {
                    List<Account> accounts;
                    accounts = dao.getUserList();

                    JSONArray jsonArray = constructJSON(accounts);
                    if (jsonArray.isEmpty()) {
                        out.print("{\"result\":\"fail\", \"reason\":\"No article found\"}");
                        System.out.println("json not returned");
                    } else {
                        out.print(jsonArray);
                        System.out.println("json returned");
                    }
                } catch (Exception e) {
                    out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
                    e.printStackTrace();
                }
            //}
        }


        private JSONArray constructJSON(List<Account> accounts) throws SQLException{
            JSONArray jsonArray = new JSONArray();
            for (Account account : accounts) {

                JSONObject jsonSingle = new JSONObject();

                jsonSingle.put("uid", account.getUserId());
                jsonSingle.put("uname", account.getUsername());
                jsonSingle.put("fname", account.getFirstname());
                jsonSingle.put("lname", account.getLastname());
                jsonSingle.put("email", account.getEmail());
                jsonSingle.put("avatar_thumbnailpath", account.getAvatarThumbnailPath());

                jsonArray.add(jsonSingle);
            }
            return jsonArray;
        }
    }

