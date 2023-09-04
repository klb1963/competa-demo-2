package com.competa.competademo.dto;

import com.competa.competademo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static com.competa.competademo.dto.UserDtoHelper.parseUserName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    protected Long id;
    @NotEmpty
    protected String firstName;
    @NotEmpty
    protected String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    protected String email;

    protected String avatar; // ссылка на аватар

    public UserDto(final User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        final UserNameModel userNameModel = parseUserName(user);
        this.firstName = userNameModel.firstName();
        this.lastName = userNameModel.lastName();
    }

    // конструктор
    public UserDto(User authUser, String avatarData) {
        this(authUser);
        this.avatar = avatarData;
    }

    public User toEntity() {
        return User.builder()
                .name(parseUserName(this))
                .email(this.email)
                .id(this.getId())
                .build();
    }


}
