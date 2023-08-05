package com.competa.competademo.dto;

import com.competa.competademo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;

import static com.competa.competademo.dto.UserDtoHelper.parseUserName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto extends UserDto {

    @NotEmpty(message = "Password should not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Override
    public User toEntity() {
        return User.builder()
                .name(parseUserName(this))
                .password(this.password)
                .email(this.email)
                .id(this.getId())
                .roles(new HashSet<>())
                .competas(new ArrayList<>())
                .build();
    }
}
