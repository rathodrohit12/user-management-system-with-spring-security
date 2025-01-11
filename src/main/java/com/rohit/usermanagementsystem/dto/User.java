package com.rohit.usermanagementsystem.dto;

import com.rohit.usermanagementsystem.entity.RoleEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@ToString(exclude = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;

	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Email is required")
	@Email(message = "Please enter a valid email address")
	private String email;

	@NotEmpty(message = "Mobile number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be numerical and exactly 10 digits")
	private String mobile;

	@NotEmpty(message = "Password is required")
	@Size(min = 3, message = "Password must be at least 3 characters long")
	private String pass;

	private List<Role> roles = new ArrayList<>();
	private String otp;
	private Date otpRequestedTime;
	private String status;




}
