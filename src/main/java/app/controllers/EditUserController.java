package app.controllers;
import app.dto.ConverterEntityToDTO;
import app.dto.UserDTO;
import app.dto.UserDTOForEdit;
import app.exceptions.AppDatabaseException;
import app.managers.UserManager;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/editUser")
@SessionAttributes(names = "sessionUser")
public class EditUserController {

    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    @Autowired
    private UserManager userManager;

    @ModelAttribute(name = "userDTOForEdit")
    public UserDTOForEdit userDTO(){
        return new UserDTOForEdit();
    }

    @ModelAttribute(name = "error")
    public boolean error(){
        return false;
    }

    @GetMapping
    public String getEditView(Principal principal, @ModelAttribute UserDTOForEdit userDTOForEdit, Model model){
        try {
            User user = userManager.findUserByEmail(principal.getName());
            if(null!=user){
                model.addAttribute("sessionUser", user);
                model.addAttribute("error",false);
                UserDTO userDTO = converterEntityToDTO.convertUserToUserDTO(user);
                userDTOForEdit.setName(userDTO.getName());
                userDTOForEdit.setSurname(userDTO.getSurname());
            }
        } catch (AppDatabaseException e) {
            model.addAttribute("error", true);
        }finally {
            return "users/editUser";
        }
    }

    @PostMapping
    public String setEditedUserData(@Valid UserDTOForEdit userDTOForEdit, Errors errors, @ModelAttribute("sessionUser") User user,  RedirectAttributes redirectAttributes){
        if(errors.hasErrors()) return "users/editUser";
        user.setName(userDTOForEdit.getName());
        user.setSurname(userDTOForEdit.getSurname());
        userManager.editUser(user);
        redirectAttributes.addFlashAttribute("alert", "editUser");
        return "redirect:/home";
    }
}
