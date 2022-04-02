package com.phoenix.service.user;

import com.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.repository.AppRepository;
import com.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AppRepository appRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @Override
    public AppUserResponseDto createUser(AppUserRequestDto userRequestDto)  throws BusinessLogicException{


        Optional<AppUser> savedUser = appRepository.findByEmail(userRequestDto.getEmail());
        if(savedUser.isPresent()){
            throw new BusinessLogicException("user with this email already exist");
        }

        AppUser newUser = new AppUser();
        newUser.setEmail(userRequestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        newUser.setFirstName(userRequestDto.getFirstName());
        newUser.setAddress(userRequestDto.getAddress());

        //save object
        appRepository.save(newUser);

        //return response
        return buildResponse(newUser);
    }


    private AppUserResponseDto buildResponse(AppUser appUser){
        return AppUserResponseDto.builder()
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .address(appUser.getAddress())
                .build();
    }
}
