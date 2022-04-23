package com.navpahul.KTems.services;

import com.navpahul.KTems.data.CustomUserDetails;
import com.navpahul.KTems.entities.User;
import com.navpahul.KTems.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                            () -> new UsernameNotFoundException("User not found!!!")); 
                            
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), 
                                        user.getEmail(), user.getRoles());
    }
}