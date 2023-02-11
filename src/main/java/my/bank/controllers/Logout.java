package my.bank.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class Logout {

    @GetMapping("/logout")
    @ResponseBody
    public void logout(HttpSession session, HttpServletResponse response){
        try {
            if (session.getAttribute("user") != null) {
                session.removeAttribute("user");
            }
            response.sendRedirect("/login");
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
