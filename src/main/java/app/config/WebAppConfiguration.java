package app.config;

import app.dto.ConverterEntityToDTO;
import app.dto.ConverterDTOToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.Locale;

@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {

    @Autowired
    private MessageSource messageSource;


    @Bean
    @Scope("prototype")
    public ConverterDTOToEntity loadConverterDTOToEntity(){
        return new ConverterDTOToEntity();
    }
    @Bean
    @Scope("prototype")
    public ConverterEntityToDTO loadConverterEntityToDTO(){
        return new ConverterEntityToDTO();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver  = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale("pl","PL"));
        return acceptHeaderLocaleResolver;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(){
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
        return  messageSourceAccessor;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/auth").setViewName("login/loginPage");
    }
}
