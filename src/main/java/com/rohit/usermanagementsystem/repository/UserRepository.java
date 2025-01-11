package com.rohit.usermanagementsystem.repository;

import com.rohit.usermanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByMobile(String mobile);
	UserEntity findByEmailAndOtp( String email, String otp);
	UserEntity findByEmailAndPass( String email, String pass);
}
