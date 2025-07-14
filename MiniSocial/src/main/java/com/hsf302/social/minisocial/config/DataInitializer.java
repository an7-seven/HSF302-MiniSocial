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
        String[] usernames = {"johnny", "alice_dev", "mikejava", "sandy.tech", "tonyreact", "linhcute", "ducnode", "hana_hihi", "boss_bao", "testguy999"};
        String[] emails = {"johnny@demo.com", "alice@demo.com", "mike@demo.com", "sandy@demo.com", "tony@demo.com", "linh@demo.com", "duc@demo.com", "hana@demo.com", "bao@demo.com", "testguy@demo.com"};
        String[] fullNames = {"Johnny Walker", "Alice Nguyen", "Mike Le", "Sandy Tran", "Tony Nguyen", "Linh Vu", "Duc Le", "Hana Pham", "Bao Ho", "Tester Anonymous"};
        String[] bios = {
                "Loves traveling and photography.",
                "Frontend dev & coffee addict.",
                "Java enthusiast.",
                "I test stuff for a living.",
                "React fanboy & anime watcher.",
                "Just here for the memes.",
                "Node.js + late-night coding.",
                "Lofi + study = mood.",
                "Backend is life.",
                "Don’t mind me. I break things."
        };

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserName(usernames[i]);
            user.setPassword("pass123");
            user.setEmail(emails[i]);
            user.setFullName(fullNames[i]);
            user.setAvatarUrl("https://i.pravatar.cc/150?img=" + (i + 1));
            user.setBio(bios[i]);
            userService.save(user);
        }


    }
}
