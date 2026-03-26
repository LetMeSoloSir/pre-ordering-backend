package com.ordering.mvc.service.user;

import com.ordering.mvc.model.user.UserInfo;
import com.ordering.mvc.repository.user.UserRepository;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMeService implements BaseService<Void, UserInfo> {

    private final UserRepository userRepository;

    @Override
    public UserInfo doProcess(Void unused) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findById(userId)
                .orElseThrow();
    }
}

