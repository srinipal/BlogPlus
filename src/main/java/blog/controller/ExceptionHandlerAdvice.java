package blog.controller;

import blog.common.exceptions.BadRequestException;
import blog.common.exceptions.RestrictedAccessException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by srinivas.g on 22/11/16.
 */

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RestrictedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleError(RestrictedAccessException ex, Model model){
        model.addAttribute("error", ex.getErrorMessage());
        return "appl_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest req, Exception ex, Model model){
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());
        return "gen_error";
    }

}
