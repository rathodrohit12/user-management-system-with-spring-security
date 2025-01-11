package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.Role;
import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;
import com.rohit.usermanagementsystem.entity.RoleEntity;
import com.rohit.usermanagementsystem.entity.UserEntity;
import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.exception.DuplicateEmailException;
import com.rohit.usermanagementsystem.exception.DuplicateMobileException;
import com.rohit.usermanagementsystem.exception.InvalidPasswordException;
import com.rohit.usermanagementsystem.exception.UserNotFoundException;
import com.rohit.usermanagementsystem.repository.RoleRepository;
import com.rohit.usermanagementsystem.repository.UserRepository;
import com.rohit.usermanagementsystem.utils.OTPGenerator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(OtpService otpService, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.otpService = otpService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists");
        }
        if (userRepository.findByMobile(user.getMobile()) != null) {
            throw new DuplicateMobileException("Mobile number already exists");
        }
        user.setPass(passwordEncoder.encode(user.getPass()));
        user.setStatus("INACTIVE");

        Role role = assignRolesBasedOnEmail(user.getEmail());
        user.setRoles(Collections.singletonList(role));

        String otp = OTPGenerator.generateOTP();
        user.setOtp(otp);
        user.setOtpRequestedTime(new Date());

        UserEntity entity = userRepository.save(modelMapper.map(user, UserEntity.class));

        otpService.sendOTPViaEmail(user, user.getOtp());

        return modelMapper.map(entity, User.class);
    }




    @Override
    public Role assignRolesBasedOnEmail(String email) {
        String roleName = email.equals("rohitrathoddev@gmail.com") ? "ROLE_ADMIN" : "ROLE_USER";
        RoleEntity roleEntity = roleRepository.findByName(roleName);
        if (roleEntity == null) {
            throw new IllegalArgumentException("Role not found for user: " + roleName);
        }
        return modelMapper.map(roleEntity, Role.class);
    }


    @Override
    public User loginUser(String email, String pass) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pass));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity.getStatus().equalsIgnoreCase("ACTIVE")) {
                return modelMapper.map(userEntity, User.class);
            } else
                return null;
        }
        return null;
    }





    @Override
    public void updateUser(Long id, UserNoPasswordDTO user) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setMobile(user.getMobile());

        userRepository.save(entity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return Arrays.asList(modelMapper.map(userRepository.findAll(), User[].class));
    }

    @Override
    public User getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")), User.class);
    }



    @Override
    public User getUserByMobile(String mobile) {
        UserEntity entity = userRepository.findByMobile(mobile);
        if (entity != null) {
            return modelMapper.map(entity, User.class);
        }else{
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email);
        if(entity != null) {
            return modelMapper.map(entity, User.class);
        }else{
            return null;
        }

    }

    @Transactional
    @Override
    public User verifyOtp(String email, String otp) {
        UserEntity entity = userRepository.findByEmailAndOtp(email, otp);
        if(entity != null) {
            entity.setStatus("ACTIVE");
            return modelMapper.map(userRepository.save(entity), User.class);
        }
        return null;
    }

    @Override
    public UserNoPasswordDTO convertToUpdateDTO(User user) {
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }



}
