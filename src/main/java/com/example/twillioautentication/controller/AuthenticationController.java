package com.example.twillioautentication.controller;

import com.example.twillioautentication.entity.User;
import com.example.twillioautentication.model.UserDTO;
import com.example.twillioautentication.service.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ap1/v1/authentication")
public class AuthenticationController {

    private final UserAuthentication userAuthentication;

    @Autowired
    public AuthenticationController(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    @PostMapping("/user")
    public ResponseEntity<String> signUpUser(@RequestBody UserDTO userDTO){
            String response = userAuthentication.authenticate(userDTO);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/user/validate")
    public ResponseEntity<String> validateUser(@RequestParam Long IdNewUser, @RequestParam String otp){
        String validateUser = userAuthentication.validateUser(IdNewUser, otp);
        return ResponseEntity.ok(validateUser);
    }
}
