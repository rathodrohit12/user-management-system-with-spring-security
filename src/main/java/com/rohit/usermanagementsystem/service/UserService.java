package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.Role;
import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
	
	public User registerUser(User user) throws MessagingException, UnsupportedEncodingException;
	public User loginUser(String email, String pass);
 	public void updateUser(Long id, UserNoPasswordDTO user);
	public void deleteUser(Long id);
	public List<User> getAllUsers();
	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public User getUserByMobile(String mobile);
	public UserNoPasswordDTO convertToUpdateDTO(User user);
	public User verifyOtp(String email, String otp);
	public Role assignRolesBasedOnEmail(String email);
}