package com.phoenix.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AppUserResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
