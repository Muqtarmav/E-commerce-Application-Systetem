package com.phoenix.web.controllers;

import com.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.service.user.AppUserService;
import com.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    AppUserService appUserService;


    @PostMapping()
    public ResponseEntity<?>
    createUser(@RequestBody AppUserRequestDto appUserRequestDto){

        try {
            AppUserResponseDto responseDto = appUserService.createUser(appUserRequestDto);
            return ResponseEntity.ok().body(responseDto);
        }
        catch(BusinessLogicException e){
            return ResponseEntity.badRequest().body(e);
        }

        }
    }

