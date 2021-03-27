package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginErrorController {

    @RequestMapping(value = "/loginError")
    public String loginError(Model model){
        model.addAttribute("errorLogin", true);
        return "login/loginPage";
    }

}
