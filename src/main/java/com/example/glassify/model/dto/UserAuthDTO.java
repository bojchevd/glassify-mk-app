package com.example.glassify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private String city;
    private String phoneNumber;

}
