package app.interceptor;

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

    private Logger logger;

    private StringBuilder builder;

    public LogInterceptor() {
    }

    @AroundInvoke
    public Object loggerAction(InvocationContext context) throws Exception {
        String space = " ";
        builder = new StringBuilder("Registration of the use of the business method ");
        builder.append(context.getMethod().getName());
        builder.append(" from the class ");
        builder.append(context.getMethod().getClass().getName());
        builder.append(" invoked by ");
        builder.append(securityContext.getCallerPrincipal().getName());
        builder.append(" at time " + new Date().toString());
        Object[] parameters = context.getParameters();
        if (null != parameters) {
            for (Object o : parameters) {
                if (o != null) {
                    builder.append(" with parameter ").append(o.getClass().getName()).append('=').append(o);
                } else {
                    builder.append(" without value - (null)");
                }
            }
        }
        logger = Logger.getLogger(getClass().getName());

        try {
            Object proceed = context.proceed();
            if (proceed != null) {
                builder.append(" return ").append(proceed.getClass().getName()).append('=').append(proceed);
            } else {
                builder.append(" return null");
            }
            return proceed;
        }catch (Exception e){
            logger.log(Level.SEVERE, "can't invoke bussines method class name " + context.getMethod().getClass().getName() + " method name " + context.getMethod().getName()
            +" invoked by " +securityContext.getCallerPrincipal().getName()+ " at time " + new Date().toString());
            throw e;
        }
        finally {
            logger.log(Level.SEVERE, builder.toString());
        }
    }
}
