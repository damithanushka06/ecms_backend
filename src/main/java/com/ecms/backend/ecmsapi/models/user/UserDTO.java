package com.ecms.backend.ecmsapi.models.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;

    private String email;
    private String employeeNo;
    private Integer roleId;

    private String roleName;

    private String createdBy;

    private LocalDate createdDate;
}
