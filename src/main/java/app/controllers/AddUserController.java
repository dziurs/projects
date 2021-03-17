package app.controllers;

import app.dto.ConverterDTOToEntity;
import app.dto.UserDTO;
import app.managers.UserManager;
import app.model.ApplicationRole;
import app.model.User;
import app.validators.UserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registerUser")
public class AddUserController {

    @Autowired
    private ConverterDTOToEntity converter;
    @Autowired
    private UserManager userManager;

    @ModelAttribute(name = "userDTO")
    public UserDTO userDTO(){
        return new UserDTO();
    }

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(new UserDTOValidator());
    }

    @GetMapping
    public String registerForm(){
        return "users/addUser";
    }

    @PostMapping
    public String addUser(@Valid UserDTO userDTO, Errors errors, RedirectAttributes redirectAttributes){
        if(errors.hasErrors()) return "users/addUser";
        User user = converter.simplyConvertUserDTOToUser(userDTO);
        user.getRoles().add(ApplicationRole.ROLE_USER);
        user.setStatus(true);
        userManager.addUser(user);
        redirectAttributes.addFlashAttribute("alert", "successUser");
        return "redirect:/home";
    }

}
