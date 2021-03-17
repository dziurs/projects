package app.managers;

import app.exceptions.AppDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceManager implements UserDetailsService {

    @Autowired
    private UserManager manager;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails;
        try {
            userDetails = manager.findUserByEmail(userName);
            return userDetails;
        } catch (AppDatabaseException e) {
            throw new UsernameNotFoundException(messageSourceAccessor.getMessage("spring.security.message.login.notfound"));
        }
    }
}
