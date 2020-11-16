package app.interceptor;

import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
@Log
public class LogInterceptor {

    @Inject
    SecurityContext securityContext;

    public LogInterceptor() {
    }

    @AroundInvoke
    public Object loggerAction(InvocationContext context) throws Exception {
        String userLogin = securityContext.getCallerPrincipal().getName();
        String className = context.getMethod().getClass().getName();
        String methodName = context.getMethod().getName();
        Logger logger = Logger.getLogger(className + " " + methodName);
        logger.log(Level.INFO, "registration of the use of the business method " + methodName +
                " from the class " + className +" invoked by " + userLogin + " at time " + new Date().toString());
        try {
            return context.proceed();
        }catch (Exception e){
            logger.log(Level.SEVERE, "can't invoke bussines method class name " + className + " method name " + methodName
            +" invoked by " +userLogin + " at time " + new Date().toString());
            throw e;
        }
    }
}
