package com.ordering.mvc.controller.dashboard;

import com.ordering.mvc.request.dashboard.DashboardRequest;
import com.ordering.mvc.request.user.CreateEmployeeRequest;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.response.dashboard.AdminDashboardResponse;
import com.ordering.mvc.response.user.UserResponse;
import com.ordering.mvc.service.dashboard.AdminDashboardService;
import com.ordering.mvc.service.dashboard.CreateEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    private final CreateEmployeeService createEmployeeService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> dashboard(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate
    ) {

        DashboardRequest request = new DashboardRequest();
        request.setFromDate(fromDate);
        request.setToDate(toDate);

        AdminDashboardResponse data = dashboardService.doProcess(request);

        return ResponseEntity.ok(
                ApiResponse.<AdminDashboardResponse>builder()
                        .status("SUCCESS")
                        .code(200)
                        .message("Fetched dashboard")
                        .data(data)
                        .build()
        );
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<UserResponse>> createEmployee(
            @RequestBody CreateEmployeeRequest request
    ) {

        log.info("Admin creating employee: {}", request.getUsername());

        UserResponse data = createEmployeeService.doProcess(request);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .status("SUCCESS")
                        .code(200)
                        .message("Employee created successfully")
                        .data(data)
                        .build()
        );
    }
}
