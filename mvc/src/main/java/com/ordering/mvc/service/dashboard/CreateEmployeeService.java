package com.ordering.mvc.service.dashboard;

import com.ordering.mvc.model.user.UserInfo;
import com.ordering.mvc.repository.user.UserRepository;
import com.ordering.mvc.request.user.CreateEmployeeRequest;
import com.ordering.mvc.response.user.UserResponse;
import com.ordering.mvc.service.common.BaseService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateEmployeeService implements BaseService<CreateEmployeeRequest, UserResponse> {

    private static final String REALM = "pre-ordering";
    private final Keycloak keycloak;
    private final UserRepository userRepo;

    @Override
    public UserResponse doProcess(CreateEmployeeRequest request) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmailVerified(true);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFullname());
        user.setEnabled(true);

        Response response = keycloak.realm(REALM)
                .users()
                .create(user);

        int status = response.getStatus();
        String error = response.readEntity(String.class);

        if (status != 201) {
            throw new RuntimeException("Create user failed: " + status + " - " + error);
        }

        String userId = response.getLocation().getPath()
                .replaceAll(".*/([^/]+)$", "$1");

        CredentialRepresentation password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(request.getPassword());
        password.setTemporary(false);

        keycloak.realm(REALM)
                .users()
                .get(userId)
                .resetPassword(password);

        RoleRepresentation role = keycloak.realm(REALM)
                .roles()
                .get("EMPLOYEE")
                .toRepresentation();

        keycloak.realm(REALM)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUsername(request.getUsername());
        userInfo.setFullname(request.getFullname());
        userInfo.setEmail(request.getEmail());
        userInfo.setPhone(request.getPhone());
        userInfo.setRole("EMPLOYEE");
        userInfo.setIsActive(true);

        userRepo.save(userInfo);

        UserResponse res = new UserResponse();
        res.setUserId(userId);
        res.setUsername(request.getUsername());
        res.setRole("EMPLOYEE");

        return res;
    }
}
