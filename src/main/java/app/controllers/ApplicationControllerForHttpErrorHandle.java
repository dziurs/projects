package app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ApplicationControllerForHttpErrorHandle implements ErrorController {

    @RequestMapping("/error")
    public String customErrorPages(HttpServletResponse response){
        if(response.getStatus()== HttpStatus.NOT_FOUND.value()) return "/errors/error404";
        else if(response.getStatus()== HttpStatus.FORBIDDEN.value()) return "/errors/error403";
        else if(response.getStatus()== HttpStatus.INTERNAL_SERVER_ERROR.value()) return "/errors/error500";
        else if(response.getStatus()== HttpStatus.SERVICE_UNAVAILABLE.value()) return "/errors/error409";
        else return "/errors/error";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
