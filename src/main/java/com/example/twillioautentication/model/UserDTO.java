package com.example.twillioautentication.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class UserDTO  {
    String username;
    String phoneNumber;
    String password;
}
