package com.example.twillioautentication.service.impl;

import com.example.twillioautentication.entity.OTP;
import com.example.twillioautentication.repository.OTPRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OTPServiceImpl {

    private final OTPRepository otpRepository;

    @Autowired
    public OTPServiceImpl(OTPRepository otpRepository) {
        this.otpRepository = otpRepository;
    }
    private static final int OTP_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public void save(OTP otp) {
        otpRepository.save(otp);
    }

    public OTP getOtp(String otp) {
        return otpRepository.getByOTP(otp);
    }
}
