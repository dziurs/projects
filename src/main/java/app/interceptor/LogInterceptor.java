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
    public Object loggerAction(InvocationContext context) throws BuildingSalesAppException {
        String className = context.getMethod().getClass().getName();
        String methodName = context.getMethod().getName();
        Logger logger = Logger.getLogger(className + " " + methodName);
        logger.log(Level.INFO, "registration of the use of the business method " + methodName + " from the class " + className);
        try {
            return context.proceed();
        }catch (Exception e){
            logger.log(Level.SEVERE, "can't invoke bussines method");
            throw new BuildingSalesAppException("Error during invoke business method by interceptor");
        }
    }
}
