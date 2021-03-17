package app.accounts;

import app.managers.UserManager;
import app.model.ApplicationRole;
import app.model.Email;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("singleton")
public class AdministratorAccount {

    @Autowired
    private UserManager userManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Email email = new Email();
        email.setEmail("admin@spring.com");
        User user = new User();
        user.getRoles().add(ApplicationRole.ROLE_ADMIN);
        user.setName("admin");
        user.setSurname("admin");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setStatus(true);
        userManager.addUser(user);
    }
}
