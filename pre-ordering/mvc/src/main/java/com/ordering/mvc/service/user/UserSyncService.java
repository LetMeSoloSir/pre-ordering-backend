package com.ordering.mvc.service.user;

import com.ordering.mvc.model.user.UserInfo;
import com.ordering.mvc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSyncService {

    private final UserRepository userRepository;

    public UserInfo syncFromJwt(Jwt jwt) {
        String userId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");

        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(username);
                    user.setEmail(email);
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    UserInfo user = new UserInfo();
                    user.setUserId(userId);
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setRole("USER");
                    user.setIsActive(true);
                    return userRepository.save(user);
                });
    }
}

