package com.rohit.usermanagementsystem.controller;


import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;
import com.rohit.usermanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin-all-users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
//        userService.convertToUpdateDTO(user);
        model.addAttribute("user", user);
        return "admin-update-page";
    }


    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id , @Valid UserNoPasswordDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "admin-update-page";
        }
        try {
            userService.updateUser(id, user);
            model.addAttribute("msg", "Update successful!");
            model.addAttribute("msgType", "alert-success");
            return "redirect:/admin/all";

        } catch (Exception e) {
            model.addAttribute("msg", "Email already exists. Update failed. Please try again.");
            model.addAttribute("msgType", "alert-danger");
            return "admin-update-page";
        }
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userName", user.getName());
        model.addAttribute("userMobile", user.getMobile());
        model.addAttribute("userEmail", user.getEmail());
        return "admin-profile-page";
    }




}
