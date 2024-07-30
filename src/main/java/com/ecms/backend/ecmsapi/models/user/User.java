package com.ecms.backend.ecmsapi.models.user;
import com.ecms.backend.ecmsapi.models.role.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "user_mst",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;


  private Integer roleId;

  private String name;

  private  String nic;

  private String roleName; // this will contain the role name for the user

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String email, String password, String nic, String name) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.nic = nic;
    this.name = name;
  }
}
