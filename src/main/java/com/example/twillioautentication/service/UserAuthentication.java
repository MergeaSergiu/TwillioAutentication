package com.example.twillioautentication.service;

import com.example.twillioautentication.model.UserDTO;

public interface UserAuthentication {

     String authenticate(UserDTO userDTO);

     String validateUser(Long idNewUser, String otp);
}
