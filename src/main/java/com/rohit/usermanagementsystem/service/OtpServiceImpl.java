package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.entity.UserEntity;
import com.rohit.usermanagementsystem.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender mailSender;
    OtpServiceImpl(UserRepository userRepository, ModelMapper modelMapper, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mailSender = mailSender;
    }

    @Override
    public User generateOTPAndSave(User user) throws UnsupportedEncodingException, MessagingException {
        int otp = (int) (Math.random() * 900000) + 100000; // Generates a number between 100000 and 999999
        String OTP = String.valueOf(otp);

        //String encodedOTP = passwordEncoder.encode(OTP);
        user.setOtp(OTP);
        user.setOtpRequestedTime(new Date());

        UserEntity entity = modelMapper.map(user, UserEntity.class);
        UserEntity userEntity = userRepository.save(entity);
        return modelMapper.map(userEntity, User.class);

//        sendOTPEmail(user, OTP);
    }

    @Override
    public void sendOTPViaEmail(User user, String OTP) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("@gmail.com", "My Website");
        helper.setTo(user.getEmail());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p><h3>Hello " + user.getName() + "</h3></p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to register:</p>"
                + "<p><b><h1>" + OTP + "<h1></b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void disposeOTP(User user) {
        UserEntity entity = modelMapper.map(user, UserEntity.class);
        entity.setOtp(null);
        entity.setOtpRequestedTime(null);
        userRepository.save(entity);
    }

    @Override
    public boolean isOTPValid(User user) {
        if (user.getOtp() == null || user.getOtpRequestedTime() == null) {
            return false;
        }
        long otpRequestedTimeInMillis = user.getOtpRequestedTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        long OTP_VALID_DURATION = 60 * 1000;  // 1 minutes

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expired
            this.disposeOTP(user);  // Dispose OTP if expired
            return false;
        }

        return true;
    }




}
