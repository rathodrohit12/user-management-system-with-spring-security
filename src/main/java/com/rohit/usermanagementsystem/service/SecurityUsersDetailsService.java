package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.entity.UserEntity;
import com.rohit.usermanagementsystem.exception.UserNotFoundException;
import com.rohit.usermanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class SecurityUsersDetailsService implements UserDetailsService {


    public UserRepository userRepository;

    public SecurityUsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserEntity entity = userRepository.findByEmail(username);
        if(entity==null){
            throw new UsernameNotFoundException("Could not found User !!!!");
        }
        return new SecurityUsersDetails(entity);

    }









}
