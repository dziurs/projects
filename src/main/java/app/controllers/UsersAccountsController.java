package app.controllers;

import app.dto.AccountDTO;
import app.dto.ConverterEntityToDTO;
import app.exceptions.AppDatabaseException;
import app.managers.UserManager;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/allUsers")
public class UsersAccountsController {

    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    @Autowired
    private UserManager userManager;

    @GetMapping
    public String getAllUsers(Model model){
        AccountDTO formAccountDTO = new AccountDTO();
        List<User> allUsers = userManager.getAllUsers();
        List<AccountDTO> accountList = new ArrayList<>();
        Iterator<User> iterator = allUsers.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            AccountDTO accountDTO = converterEntityToDTO.convertUserToAccountDTO(user);
            accountList.add(accountDTO);
        }
        model.addAttribute("accountDTOList",accountList);
        model.addAttribute("accountDTO",formAccountDTO);
        return "users/usersList";
    }
    @PostMapping
    public String changeUserAccountStatus(@ModelAttribute("accountDTO") AccountDTO accountDTO, RedirectAttributes redirectAttributes){
        try {
            userManager.changeAccountStatus(accountDTO);
        } catch (AppDatabaseException e) {
            redirectAttributes.addFlashAttribute("error", true);
        } finally {
            return "redirect:/allUsers";
        }

    }

}
