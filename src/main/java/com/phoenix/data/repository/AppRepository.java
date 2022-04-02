package com.phoenix.data.repository;

import com.phoenix.data.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository <AppUser, Long>{

    Optional<AppUser> findByEmail(String email);


}
