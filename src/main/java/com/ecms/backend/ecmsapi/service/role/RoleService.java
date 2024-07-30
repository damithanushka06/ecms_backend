package com.ecms.backend.ecmsapi.service.role;
import com.ecms.backend.ecmsapi.models.dropdown.DropDownDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<DropDownDto> getAllRoles();
}
