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
    public void dataBaseAction(InvocationContext context) throws BuildingSalesAppException {
        try{
            context.proceed();
        }catch(Exception e){
            throw new BuildingSalesAppException("You are working with outdated data");
        }
    }

}
