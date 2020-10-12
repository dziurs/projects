package app.interceptor;

import app.exception.BuildingSalesAppException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@DAOTransaction
public class DAOInterceptor {

    public DAOInterceptor() {
    }
    @AroundInvoke
    public Object dataBaseAction(InvocationContext context) throws BuildingSalesAppException {
        try{
           return context.proceed();
        }catch(Exception e){
            throw new BuildingSalesAppException("You are working with outdated data");
        }
    }

}
