package app.controllers;

import app.dto.ConverterEntityToDTO;
import app.dto.UserDTO;
import app.exceptions.AppDatabaseException;
import app.managers.UserManager;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/deleteUser")
@SessionAttributes(names = "deleteUser")
public class DeleteUserController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    @GetMapping
    public String getDeleteUserView(Principal principal, Model model){
        try {
            User user = userManager.findUserByEmail(principal.getName());
            model.addAttribute("deleteUser", user);
            UserDTO userDTO = converterEntityToDTO.convertUserToUserDTO(user);
            model.addAttribute("userDTO", userDTO);
        } catch (AppDatabaseException e) {
            model.addAttribute("error",true);
        }finally {
            return "users/deleteUser";
        }
        //TODO zrobić widok
    }
    @PostMapping
    public String deleteUserAccountAndLogout(@ModelAttribute("deleteUser") User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        userManager.deleteUser(user);
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication!=null) new SecurityContextLogoutHandler().logout(request,response,authentication);
        context.setAuthentication(null);
        redirectAttributes.addFlashAttribute("alert", "deleteUser");
        return "redirect:/home";
    }
}
