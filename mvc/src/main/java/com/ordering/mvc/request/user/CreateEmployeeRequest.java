package com.ordering.mvc.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
}
