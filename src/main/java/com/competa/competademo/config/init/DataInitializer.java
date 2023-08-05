package com.competa.competademo.config.init;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.entity.Role;
import com.competa.competademo.exceptions.UserAlreadyExistsException;
import com.competa.competademo.repository.RoleRepository;
import com.competa.competademo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс DataInitializer предназначен для инициализации данных при запуске приложения.
 * Этот компонент выполняет роль ApplicationRunner и предоставляет метод run(),
 * который будет вызван после запуска Spring Boot приложения.
 * Он загружает предопределенные роли и пользователей в базу данных.
 * Используется только в профиле "dev".
 *
 * @author Andrej Reutow
 * created on 02.08.2023
 */
@Component
@Profile("dev")
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final UserService userService;

    private final RoleRepository roleRepository;
    private final UserProperties userProperties;

    /**
     * Конструктор класса DataInitializer.
     *
     * @param userService    Сервис для работы с пользователями.
     * @param roleRepository Репозиторий для работы с ролями.
     * @param userProperties Параметры пользователей, определенные в файле настроек.
     */
    public DataInitializer(UserService userService,
                           RoleRepository roleRepository,
                           UserProperties userProperties) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userProperties = userProperties;
    }

    /**
     * Метод run() вызывается после запуска приложения.
     * Создает предопределенные роли, проверяет наличие пользователей в базе данных,
     * и добавляет предопределенного администратора, если он отсутствует.
     *
     * @param args Аргументы командной строки, переданные приложению.
     */
    @Override
    public void run(ApplicationArguments args) {
        final CreateUserDto userDto = userProperties.getUser();
        final CreateUserDto adminUserDto = userProperties.getAdmin();

        createRoles();

        saveUser(userDto);
        saveUser(adminUserDto);

        if (adminUserDto.getId() != null) {
            userService.addUserRole(adminUserDto.getId(), ROLE_ADMIN);
        }
    }

    private void saveUser(final CreateUserDto userDto) {
        try {
            final long savedUserID = userService.saveUser(userDto).getId();
            userDto.setId(savedUserID);
            log.debug("Saved init user with email {} and id {}", userDto.getEmail(), userDto.getId());
        } catch (UserAlreadyExistsException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Создает предопределенные роли, если они еще не существуют в базе данных.
     */
    private void createRoles() {
        final List<String> initRoles = List.of(ROLE_USER, ROLE_ADMIN);
        for (String roleName : initRoles) {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(Role.builder().
                        name(roleName)
                        .build());
            }
        }
    }
}
