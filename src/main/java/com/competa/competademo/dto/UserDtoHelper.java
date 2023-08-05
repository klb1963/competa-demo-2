package com.competa.competademo.dto;

import com.competa.competademo.entity.User;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
public class UserDtoHelper {

    private UserDtoHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserNameModel parseUserName(final User user) {
        final String[] name = user.getName().split(" ");
        switch (name.length) {
            case 2 -> {
                return new UserNameModel(name[0], name[1]);
            }
            case 1 -> {
                return new UserNameModel(name[0], "");
            }
            default -> {
                return new UserNameModel("", "");
            }
        }
    }

    public static String parseUserName(final UserDto userDto) {
        return String.format("%s %s",
                replaceNullWithEmpty(userDto.getFirstName()),
                replaceNullWithEmpty(userDto.getLastName()));
    }

    private static String replaceNullWithEmpty(final String value) {
        return value == null ? "" : value;
    }
}
