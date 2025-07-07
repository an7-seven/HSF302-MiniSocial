package com.hsf302.social.minisocial.service;

import com.hsf302.social.minisocial.entity.User;
import com.hsf302.social.minisocial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user = (User) userRepository.findByEmail(input).or(()->userRepository.findByUserName(input))
                .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(), // dùng đúng với entity bạn
                List.of(new SimpleGrantedAuthority("ROLE_USER")) // bạn có thể mở rộng
        );
    }
}
