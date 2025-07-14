package com.hsf302.social.minisocial.config;

import com.hsf302.social.minisocial.entity.User;
import com.hsf302.social.minisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;


    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword("password" + i);
            user.setEmail("user" + i + "@example.com");
            user.setFullName("User Number " + i);
            user.setAvatarUrl("https://example.com/avatar" + i + ".png");
            user.setBio("This is bio of user " + i);
            userService.save(user);
        }

    }
}
