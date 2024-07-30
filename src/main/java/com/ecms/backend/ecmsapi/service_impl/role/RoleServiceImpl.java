package com.ecms.backend.ecmsapi.service_impl.role;
import com.ecms.backend.ecmsapi.models.dropdown.DropDownDto;
import com.ecms.backend.ecmsapi.repository.role.RoleRepository;
import com.ecms.backend.ecmsapi.service.role.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<DropDownDto> getAllRoles() {
        return this.roleRepository.findAll().stream()
                .map(role -> new DropDownDto(role.getId(), role.getName().name()))
                .collect(Collectors.toList());
    }
}
