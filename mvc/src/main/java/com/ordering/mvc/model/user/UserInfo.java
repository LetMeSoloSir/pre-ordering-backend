package com.ordering.mvc.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    @Id
    @Column(name = "user_id", length = 100)
    private String  userId;
    @Column(name = "username")
    private String username;
    @Column(name = "full_name")
    @JsonProperty("fullname")
    private String fullname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private String role;
    @Column(name = "is_active")
    @JsonProperty("isActive")
    private Boolean isActive;

}
