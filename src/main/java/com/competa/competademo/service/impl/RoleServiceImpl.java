package com.competa.competademo.service.impl;

import com.competa.competademo.entity.Role;
import com.competa.competademo.repository.RoleRepository;
import com.competa.competademo.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Andrej Reutow
 * created on 02.08.2023
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    private static final String ROLE_NOT_EXISTS_MSG = "Role with name '%s' not exists";
    static final String INIT_USER_ROLE = "ROLE_USER";
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Role checkRoleExist() {
        Role role = new Role();
        role.setName(INIT_USER_ROLE);
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(@NonNull final String roleName) {
        return findRoleByNameAsOptional(roleName)
                .orElseThrow(() -> new IllegalArgumentException(String.format(ROLE_NOT_EXISTS_MSG, roleName)));
    }

    @Override
    public Optional<Role> findRoleByNameAsOptional(@NonNull final String roleName) {
        return roleRepository.findByName(roleName);
    }
}
