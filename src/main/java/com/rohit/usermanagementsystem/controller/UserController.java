package com.rohit.usermanagementsystem.controller;

import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;
import com.rohit.usermanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userName", user.getName());
        model.addAttribute("userMobile", user.getMobile());
        model.addAttribute("userEmail", user.getEmail());
        return "user-profile-page";
    }



    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-update-page";
    }


    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id , @Valid UserNoPasswordDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user-update-page";
        }
        try {
            userService.updateUser(id, user);
            model.addAttribute("msg", "Update successful!");
            model.addAttribute("msgType", "alert-success");
            return "redirect:/user/profile";

        } catch (Exception e) {
            model.addAttribute("msg", "Email already exists. Update failed. Please try again.");
            model.addAttribute("msgType", "alert-danger");
            return "user-update-page";
        }

    }











}
