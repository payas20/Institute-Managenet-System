package com.lucentlearn.payas.app.StudentCRUD.service.impl;

import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import com.lucentlearn.payas.app.StudentCRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user==null) throw new UsernameNotFoundException("No user found");
        return user;
    }
}
