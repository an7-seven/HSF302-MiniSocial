package com.hsf302.social.minisocial.service;

import com.hsf302.social.minisocial.entity.User;
import com.hsf302.social.minisocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }
}
