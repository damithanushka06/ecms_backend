package com.ecms.backend.ecmsapi.repository.role;
import com.ecms.backend.ecmsapi.models.role.ERole;
import com.ecms.backend.ecmsapi.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
  Optional<Role> findById(int id);
}
