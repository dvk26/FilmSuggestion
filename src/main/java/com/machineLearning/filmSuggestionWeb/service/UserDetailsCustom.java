package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import com.machineLearning.filmSuggestionWeb.model.RoleEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity= userRepository.findByUserName(username);
        RoleEntity role= userEntity.getRole();
        System.out.println("Role Code: " + "ROLE_"+role.getCode());
        return new User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role.getCode()))
        );



    }
}
