package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Andrej Reutow
 * created on 02.08.2023
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public UserNotFoundException(final String message) {
        super(message);
    }
}
