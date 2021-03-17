package app.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.OptimisticLockException;
import java.sql.SQLException;

@ControllerAdvice
public class ApplicationControllerForExceptionHandle {

    private final static String OPTIMISTIC_ERROR = "errors/optimistic";
    private final static String RUNTIME_ERROR = "errors/runtime";
    private final static String DATABASE_CONSTRAINT = "errors/constraint";

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public void handleConflict() {
    }

    @ExceptionHandler({OptimisticLockException.class, JpaOptimisticLockingFailureException.class})
    public ModelAndView OptimisticLockErrorHandler(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName(OPTIMISTIC_ERROR);
        return mav;
    }
    @ExceptionHandler(JpaSystemException.class)
    public ModelAndView databaseConstraintErrorHandler(Exception ex){
        ModelAndView mav = new ModelAndView();
        if(ex.getCause().getCause()  instanceof ConstraintViolationException){
            mav.setViewName(DATABASE_CONSTRAINT);
            return mav;
        }
        else {
            mav.setViewName(RUNTIME_ERROR);
            return mav;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public String defaultRuntimeErrorHandler(){
        return RUNTIME_ERROR;
    }
}
