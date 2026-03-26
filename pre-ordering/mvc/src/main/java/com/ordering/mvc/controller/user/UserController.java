package com.ordering.mvc.controller.user;

import com.ordering.mvc.model.user.UserInfo;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.service.user.UserMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserMeService userMeService;
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfo>> me() {

        return ResponseEntity.ok(ApiResponse.<UserInfo>builder()
                .status("SUCCESS")
                .code(200)
                .message("Fetched user info")
                .data(userMeService.doProcess(null))
                .build());
    }
}
