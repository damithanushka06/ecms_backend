package com.ecms.backend.ecmsapi.controller.auth;
import com.ecms.backend.ecmsapi.controller.payload.request.LoginRequest;
import com.ecms.backend.ecmsapi.controller.payload.request.SignupRequest;
import com.ecms.backend.ecmsapi.controller.payload.response.MessageResponse;
import com.ecms.backend.ecmsapi.controller.payload.response.UserInfoResponse;
import com.ecms.backend.ecmsapi.security.jwt.JwtUtils;
import com.ecms.backend.ecmsapi.models.role.ERole;
import com.ecms.backend.ecmsapi.models.role.Role;
import com.ecms.backend.ecmsapi.models.user.User;
import com.ecms.backend.ecmsapi.repository.role.RoleRepository;
import com.ecms.backend.ecmsapi.repository.user.UserRepository;
import com.ecms.backend.ecmsapi.service_impl.user.UserDetailsImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok()
            .body(new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles, jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()), signUpRequest.getNic(), signUpRequest.getName());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = (Role) roleRepository.findByName(ERole.ROLE_PUBLIC_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    }
    Date date = new Date(); // This is your existing java.util.Date object
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    user.setCreatedDate(localDate);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!Objects.equals(principle.toString(), "anonymousUser")) {
      Long userId = ((UserDetailsImpl) principle).getId();
//      refreshTokenService.deleteByUserId(userId);
    }
    return ResponseEntity.ok()
            .body(new MessageResponse("You've been signed out!"));
  }
}


