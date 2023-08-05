package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.Role;
import com.competa.competademo.entity.User;
import com.competa.competademo.exceptions.UserAlreadyExistsException;
import com.competa.competademo.exceptions.UserNotFoundException;
import com.competa.competademo.repository.UserRepository;
import com.competa.competademo.service.RoleService;
import com.competa.competademo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.competa.competademo.service.impl.RoleServiceImpl.INIT_USER_ROLE;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    /**
     *
     */
    @Transactional
    @Override
    public User saveUser(@NonNull CreateUserDto userDto) {

        if (isUserByEmailExist(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this email '{}' already exists", userDto.getEmail());
        }

        final User user = userDto.toEntity();
        //encrypt the password once we integrate spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        final Role role = roleService.findRoleByNameAsOptional(INIT_USER_ROLE)
                .orElseGet(roleService::checkRoleExist);
        user.getRoles().add(role);
        return saveUser(user);
    }

    @Transactional
    @Override
    public User addUserRole(final long userId, final String roleName) {
        final User user = findById(userId);
        final Role roleToAdd = roleService.findRoleByName(roleName);

        final Set<Role> userRoles = user.getRoles();
        userRoles.add(roleToAdd);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User removeUserRole(final long userId, final String roleName) {
        final User user = findById(userId);
        final Role roleToRemove = roleService.findRoleByName(roleName);

        user.getRoles().remove(roleToRemove);
        return userRepository.save(user);
    }

    @Override
    public boolean isUserByEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id '{}' not found", userId));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email '%s' not found", email)));
    }

    @Override
    public User getAuthUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // вызов контекста
        final String authUserEmail = authentication.getName(); // получение имени текущего пользователя
        return findByEmail(authUserEmail);
    }
}
