package com.hsf302.social.minisocial.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.hsf302.social.minisocial.config.JwtTokenProvider;
import com.hsf302.social.minisocial.dto.request.LoginRequest;
import com.hsf302.social.minisocial.dto.response.ApiResponse;
import com.hsf302.social.minisocial.dto.response.LoginResponse;
import com.hsf302.social.minisocial.entity.User;
import com.hsf302.social.minisocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Value("${google.client.id}")
    private String googleClientId;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());

        LoginResponse loginResponse = new LoginResponse(token, request.getEmail());

        return ResponseEntity.ok(new ApiResponse<>(true,
                "Đăng nhập thành công", loginResponse));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String idTokenString){
        try{

//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFacoty())
//                    .setAudience(Collections.singletonList(googleClientId))
//                    .build();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).setAudience(Collections.singletonList(googleClientId)).build();

            GoogleIdToken idToken = verifier.verify(idTokenString.replace("\"", ""));
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Token Google không hợp lệ", null));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            // Tìm user trong database, nếu chưa có thì tạo mới
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElseGet(() -> {
                User newUser = new User();
                newUser.setId(UUID.randomUUID());
                newUser.setEmail(email);
                newUser.setUserName(email.split("@")[0]);
                newUser.setFullName(name);
                newUser.setAvatarUrl(pictureUrl);
                return userRepository.save(newUser);
            });

            // Tạo UserDetails & JWT
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(), "", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );

            String jwtToken = jwtTokenProvider.generateToken(userDetails);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đăng nhập bằng Google thành công", new LoginResponse(jwtToken, email)));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi đăng nhập bằng Google", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Đăng xuất thành công", null));
    }

}
