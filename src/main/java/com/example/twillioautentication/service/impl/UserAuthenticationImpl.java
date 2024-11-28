package com.example.twillioautentication.service.impl;

import com.example.twillioautentication.config.TwilioConfig;
import com.example.twillioautentication.entity.OTP;
import com.example.twillioautentication.entity.User;
import com.example.twillioautentication.model.UserDTO;
import com.example.twillioautentication.repository.UserRepository;
import com.example.twillioautentication.service.UserAuthentication;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserAuthenticationImpl implements UserAuthentication {

    private final UserRepository userRepository;
    private final TwilioConfig twilioConfig;
    private final OTPServiceImpl otpService;

    @Autowired
    public UserAuthenticationImpl(UserRepository userRepository, TwilioConfig twilioConfig, OTPServiceImpl otpService) {
        this.userRepository = userRepository;
        this.twilioConfig = twilioConfig;
        this.otpService = otpService;
    }

    @Override
    public String authenticate(UserDTO userDTO) {

        if(userDTO.getUsername() == null || userDTO.getUsername().isBlank()){
            throw new IllegalArgumentException("Username cannot be blank");
        }

        User foundUser = userRepository.findByEmail(userDTO.getUsername());
        if(foundUser != null) throw new EntityExistsException("User already exist");

        if(userDTO.getPassword() == null || userDTO.getPassword().isBlank() || userDTO.getPassword().length() < 8 ){
            throw new IllegalArgumentException("Password must have at least 8 characters");
        }

        String otpForUser = otpService.generateOtp();

        User newUser = User.builder()
                .email(userDTO.getUsername())
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();

        userRepository.save(newUser);

        OTP otpUser =  OTP.builder()
                        .OTP(otpForUser)
                        .optCreatedDate(LocalDateTime.now())
                        .optExpiredData(LocalDateTime.now().plusMinutes(10))
                        .user(newUser).build();

        otpService.save(otpUser);

        Message.creator(
                new PhoneNumber(userDTO.getPhoneNumber()),
                new PhoneNumber(twilioConfig.getTwilioClientPhone()), "Verify your account using this code -  " + otpForUser + " .The code is available 10 minutes.").create();

        return "A verification code was sent to your personal phone number";
    }

    @Override
    public String validateUser(Long idNewUser, String otp) {

        Optional<User> user = userRepository.findById(idNewUser);
        if(!user.isPresent()) throw new IllegalArgumentException("User not found");

        if(user.get().isVerified()) throw new IllegalArgumentException("User is already verified");

        OTP foundOTP = otpService.getOtp(otp);
        if(foundOTP == null) throw new IllegalArgumentException("OTP not found");

        Long IdUserRelatedWithOTP = foundOTP.getUser().getId();

        if(!Objects.equals(IdUserRelatedWithOTP, idNewUser)) throw new IllegalArgumentException("Code is not valid for this user");

        if(foundOTP.getOptExpiredData().isBefore(LocalDateTime.now())) throw new IllegalArgumentException("OTP is expired");

        user.get().setVerified(true);
        userRepository.save(user.get());

        return "The account has been validated";
    }
}
