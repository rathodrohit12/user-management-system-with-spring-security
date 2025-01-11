package com.rohit.usermanagementsystem.controller;

import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.exception.DuplicateEmailException;
import com.rohit.usermanagementsystem.exception.DuplicateMobileException;
import com.rohit.usermanagementsystem.exception.InvalidPasswordException;
import com.rohit.usermanagementsystem.exception.UserNotFoundException;
import com.rohit.usermanagementsystem.service.OtpService;
import com.rohit.usermanagementsystem.service.UserService;
import com.rohit.usermanagementsystem.utils.SessionHandler;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@Controller
public class AuthController {
    private SessionHandler sessionHandler;
    private final AuthenticationManager authenticationManager;

    private final OtpService otpService;

    private final UserService userService;

    public AuthController(OtpService otpService, UserService userService, AuthenticationManager authenticationManager, SessionHandler sessionHandler) {
        this.otpService = otpService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.sessionHandler = sessionHandler;

    }


    @GetMapping("/login-page")
    public String showLoginForm() {
        return "login-page";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String pass, Model model, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pass));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            System.out.println("Authentication Success");

            return "redirect:/";
        }else {
            model.addAttribute("msg", "Invalid username or password");
            model.addAttribute("msgType", "alert-danger");
            return "login-page";
        }








//        System.out.println("from login controller: " + user);
//        if (user == null) {
//            model.addAttribute("msg", "Invalid username or password");
//            model.addAttribute("msgType", "alert-danger");
//            return "login-page";
//        }
//        model.addAttribute("userStatus", user.getStatus());
//
//        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
//            model.addAttribute("user", user);
//            sessionHandler.setSessionAttributes(session, user);
//            System.out.println("user role "+user.getRoles());
//            boolean isAdmin = user.getRoles().stream()
//                    .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
//            session.setAttribute("isAdmin", isAdmin);
//            return isAdmin ? "admin-profile-page" : "user-profile-page";
//        } else {
//            return "login-page";
//        }
    }




    @GetMapping("/register-page")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("showOtpModal", false);
        return "register-page";
    }

//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register-page";
//        }
//        if (userService.getUserByEmail(user.getEmail()) != null) {
//            model.addAttribute("msg", "Email already exists");
//            model.addAttribute("msgType", "alert-danger");
//            return "register-page";
//        }
//        if (userService.getUserByMobile(user.getMobile()) != null) {
//            model.addAttribute("msg", "Mobile number already exists");
//            model.addAttribute("msgType", "alert-danger");
//            return "register-page";
//        }
//        try {
//            User user1 = userService.registerUser(user);
//
//            if(user1 != null) {
//                model.addAttribute("msg", "Registration successful!");
//                model.addAttribute("msgType", "alert-success");
//                model.addAttribute("user", user1);
//                model.addAttribute("showOtpModal", true);  // This will trigger the OTP modal
//
//            }
//            return "register-page";
//
//        } catch (Exception e) {
//            model.addAttribute("msg", "Registration failed. Please try again.");
//            model.addAttribute("msgType", "alert-danger");
//            model.addAttribute("showOtpModal", false);  // This will trigger the OTP modal
//
//            return "register-page";
//        }
//
//
//
//    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register-page";
        }
        try {
            userService.registerUser(user);
            model.addAttribute("msg", "Registration successful!");
            model.addAttribute("msgType", "alert-success");
            model.addAttribute("user", user);
            model.addAttribute("showOtpModal", true); // Show OTP modal
        } catch (DuplicateEmailException | DuplicateMobileException ex) {
            model.addAttribute("msg", ex.getMessage());
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        } catch (Exception ex) {
            model.addAttribute("msg", "Registration failed. Please try again.");
            model.addAttribute("msgType", "alert-danger");
        }
        return "register-page";

    }




    @PostMapping("/verifyOtp")
    public String verifyOtp(@ModelAttribute User user, Model model) {
        User user1 = userService.getUserByEmail(user.getEmail());
        if (user1 != null && user1.getOtp() != null) {
            if (!otpService.isOTPValid(user1)) {
                model.addAttribute("msg", "OTP Expired");
                model.addAttribute("msgType", "alert-danger");
                model.addAttribute("user", user1);
                model.addAttribute("otpValid", false);
                model.addAttribute("showOtpModal", true); // Ensure the modal is shown
                return "register-page";
            }
            if (user.getOtp().equals(user1.getOtp())) {
                model.addAttribute("msg", "OTP Verified");
                model.addAttribute("msgType", "alert-success");
                model.addAttribute("user", user1);
                model.addAttribute("otpValid", true);
                User user2 = userService.verifyOtp(user1.getEmail(), user1.getOtp());
                otpService.disposeOTP(user2);
                return "register-page";
            } else {
                model.addAttribute("msg", "Invalid OTP");
                model.addAttribute("msgType", "alert-danger");
                model.addAttribute("user", user1);
                model.addAttribute("otpValid", false);
            }
        } else {
            model.addAttribute("msg", "OTP Expired");
            model.addAttribute("msgType", "alert-danger");
            model.addAttribute("user", user1);
            model.addAttribute("otpValid", false);
        }
        model.addAttribute("showOtpModal", true);
        return "register-page";

    }



    @PostMapping("/resendOtp")
    public String resendOtp(@ModelAttribute User user, Model model) {
        try {
            User user1 = userService.getUserByEmail(user.getEmail());
            if (user1 != null) {
                User user2 = otpService.generateOTPAndSave(user1);
                otpService.sendOTPViaEmail(user2, user2.getOtp());  // Send new OTP to user's email
                model.addAttribute("msg", "OTP resent successfully!");
                model.addAttribute("msgType", "alert-success");
                model.addAttribute("user", user2);
                model.addAttribute("otpValid", true);
                return "register-page";
            } else {
                model.addAttribute("msg", "User not found!");
                model.addAttribute("msgType", "alert-danger");
                model.addAttribute("otpValid", false);
                return "register-page";
            }
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("msg", "Error resending OTP");
            model.addAttribute("msgType", "alert-danger");
            model.addAttribute("otpValid", false);
            return "register-page";
        }
    }



    @GetMapping ("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("msg", "You have been logged out.");
        model.addAttribute("msgType", "alert-success");
        return "login-page";
    }





}
