package kz.edu.astanait;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ServletLogout")
public class ServletLogout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessions = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (int a = 0; a < cookies.length; a++) {
            cookies[a].setValue("");
            cookies[a].setMaxAge(0);
            response.addCookie(cookies[a]);
        }
        sessions.removeAttribute("lib");
        //remove the sessions
//        sessions.removeAttribute("user");
//        sessions.removeAttribute("message");
        response.sendRedirect(getServletContext().getContextPath());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
