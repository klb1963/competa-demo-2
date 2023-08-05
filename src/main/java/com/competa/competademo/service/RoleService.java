package com.competa.competademo.service;

import com.competa.competademo.entity.Role;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author Andrej Reutow
 * created on 02.08.2023
 */
public interface RoleService {

    Role checkRoleExist();

    Role findRoleByName(@NonNull String roleName);

    Optional<Role> findRoleByNameAsOptional(@NonNull String roleName);
}
