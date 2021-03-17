package app.loggers;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ApplicationLogger {


    private Logger logger = LoggerFactory.getLogger(ApplicationLogger.class.getName());

    @Around("within(app.repositories..*) || @annotation(LogApp)")
    public Object loggingMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder builder = new StringBuilder("Registration of the use of the business method ");
        builder.append(joinPoint.getSignature().getName());
        builder.append(" from class : ");
        builder.append(joinPoint.getTarget().getClass().getName());
        builder.append(" invoked by ");
        if(null!=authentication)
            builder.append(authentication.getName());
        else builder.append("GUEST");
        builder.append(" at time ");
        builder.append(new Date().toString());
        builder.append(" with parameters : ");
        Object[] args = joinPoint.getArgs();
        if(null!= args){
            for(Object o : args){
                builder.append(o.getClass().getName());
                builder.append(" = ");
                builder.append(o.toString());
            }
        }else builder.append(" no parameters");
        try{
            Object proceed = joinPoint.proceed();
            if (null != proceed){
                builder.append(" return : ");
                builder.append(proceed.toString());
            }else builder.append(" return : null ");
            return proceed;
        }catch (Exception ex){
            logger.error("Can't invoke bussines method class name " + joinPoint.getTarget().getClass().getName() + " method name " + joinPoint.getSignature().getName()
                    +" invoked by " + authentication.getName()+ " at time " + new Date().toString() + " "+ ex.getMessage());
            throw ex;
        }
        finally {
            logger.info(builder.toString());
        }
    }
}
