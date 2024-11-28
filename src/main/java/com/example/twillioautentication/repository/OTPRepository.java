package com.example.twillioautentication.repository;


import com.example.twillioautentication.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    OTP getByOTP(String otp);
}
