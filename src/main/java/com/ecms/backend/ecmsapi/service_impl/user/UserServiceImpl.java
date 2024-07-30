package com.ecms.backend.ecmsapi.service_impl.user;
import com.ecms.backend.ecmsapi.models.dropdown.DropDownDto;
import com.ecms.backend.ecmsapi.models.user.User;
import com.ecms.backend.ecmsapi.models.user.UserDTO;
import com.ecms.backend.ecmsapi.repository.user.UserRepository;
import com.ecms.backend.ecmsapi.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
