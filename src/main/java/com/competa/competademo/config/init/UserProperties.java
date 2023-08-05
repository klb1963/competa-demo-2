package com.competa.competademo.config.init;

import com.competa.competademo.dto.CreateUserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Andrej Reutow
 * created on 02.08.2023
 */
@Component
@Profile("dev")
@ConfigurationProperties(prefix = "competa.user")
@Getter
@Setter
public class UserProperties {
    private CreateUserDto user;
    private CreateUserDto admin;
}
