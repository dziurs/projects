package app.interceptor;

import app.exception.BuildingSalesAppException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
@Log
public class LogInterceptor {
    public LogInterceptor() {
    }
    @AroundInvoke
    public void loggerAction(InvocationContext context) throws BuildingSalesAppException {
        String className = context.getMethod().getClass().getName();
        String methodName = context.getMethod().getName();
        Logger logger = Logger.getLogger(className + " " + methodName);
        try{
            logger.log(Level.INFO, "registration of the use of the business method "+methodName+
                    " from the class "+className);
            context.proceed();
        }catch(Exception e){
            throw new BuildingSalesAppException("Can't invoke business method");
        }
    }
}
