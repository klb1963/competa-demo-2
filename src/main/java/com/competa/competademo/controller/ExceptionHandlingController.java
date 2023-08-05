package com.competa.competademo.controller;

import com.competa.competademo.exceptions.CompetaNotFoundException;
import com.competa.competademo.exceptions.UserAlreadyExistsException;
import com.competa.competademo.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;

/**
 * @author Andrej Reutow
 * created on 02.08.2023
 */
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleError(final UserNotFoundException ex) {
        return fillModelAndView(ex.getMessage());
    }

    @ExceptionHandler(CompetaNotFoundException.class)
    public ModelAndView handleError(final CompetaNotFoundException ex) {
        return fillModelAndView(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView handleError(final UserAlreadyExistsException ex) {
        return fillModelAndView(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleError(final AuthenticationException ex) {
        return fillModelAndView(ex.getMessage());
    }

    private ModelAndView fillModelAndView(String message) {
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }
}
