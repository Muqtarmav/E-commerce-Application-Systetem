package com.phoenix.security;

import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Authority;
import com.phoenix.data.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RDBUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AppRepository appRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //query for user details from db
        AppUser user = appRepository.findByEmail(username).orElse(null);
        //check that user exist

        if (user == null){
            throw new UsernameNotFoundException("user with email does not exist");
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        //getAuthority(user.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Authority> authorities){
        return authorities.stream().map(authority -> {return new SimpleGrantedAuthority(authority.name());
        }).collect(Collectors.toList());


    }
}
