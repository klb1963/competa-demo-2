package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class IndustryNotFoundException extends RuntimeException{

    public IndustryNotFoundException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public IndustryNotFoundException(final String message) {
        super(message);
    }
}
