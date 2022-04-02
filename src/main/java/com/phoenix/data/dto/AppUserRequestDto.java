package com.phoenix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;

}
