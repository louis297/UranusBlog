package UranusBlog.Controller.Account;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IsLoggedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Boolean isLogged = (Boolean) req.getSession().getAttribute("is_logged");
        JSONObject jsonObject = new JSONObject();
        if(isLogged != null && isLogged){
            jsonObject.put("is_logged", true);
            jsonObject.put("username", req.getSession().getAttribute("username"));
            jsonObject.put("uid", req.getSession().getAttribute("userID"));
        } else {
            jsonObject.put("is_logged", false);
        }
        resp.getWriter().print(jsonObject);
    }
}
