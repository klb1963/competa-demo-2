package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class CtypeNotFoundException extends RuntimeException{

    public CtypeNotFoundException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public CtypeNotFoundException(final String message) {
        super(message);
    }

}
