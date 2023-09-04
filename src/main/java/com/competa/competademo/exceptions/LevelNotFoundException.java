package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class LevelNotFoundException extends RuntimeException {
    public LevelNotFoundException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public LevelNotFoundException(final String message) {
        super(message);
    }

}
