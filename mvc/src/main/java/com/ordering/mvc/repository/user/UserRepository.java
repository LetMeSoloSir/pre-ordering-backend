package com.ordering.mvc.repository.user;

import com.ordering.mvc.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

    @Query("SELECT COUNT(u) FROM UserInfo u WHERE u.role = 'EMPLOYEE'")
    long countEmployee();
}
