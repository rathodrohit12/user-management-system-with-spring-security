package com.rohit.usermanagementsystem.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles DuplicateKeyException and sets error attributes in the model.
     *
     * @param ex    The DuplicateKeyException.
     * @param model The Model to pass attributes to the view.
     * @return The error page or redirect path.
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateKeyException(DuplicateKeyException ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        model.addAttribute("msgType", "alert-danger");
        return "register-page"; // Replace with your error view or redirect page
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsernameNotFound(UsernameNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("error", "Resource Not Found");
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "404";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("error", "Bad Request");
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        return "error";
    }
//    @ExceptionHandler({InvalidPasswordException.class, UserNotFoundException.class})
//    public String handleLoginExceptions(RuntimeException ex, Model model) {
//        model.addAttribute("msg", ex.getMessage());
//        model.addAttribute("msgType", "alert-danger");
//        return "login-page";
//    }

//    @ExceptionHandler({DuplicateEmailException.class, DuplicateMobileException.class})
//    public String handleRegisterExceptions(RuntimeException ex, Model model) {
//        model.addAttribute("msg", ex.getMessage());
//        model.addAttribute("msgType", "alert-danger");
//        model.addAttribute("showOtpModal", false);  // Ensure OTP modal is not triggered
//        return "register-page";
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("error", "Resource Not Found");
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "404";
    }


    /**
     * Handles all other exceptions and sets a generic error message in the model.
     *
     * @param ex    The Exception.
     * @param model The Model to pass attributes to the view.
     * @return The error page or redirect path.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }









}
