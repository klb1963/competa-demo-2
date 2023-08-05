package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
