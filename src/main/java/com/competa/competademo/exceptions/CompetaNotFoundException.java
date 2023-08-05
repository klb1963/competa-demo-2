package com.competa.competademo.exceptions;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
public class CompetaNotFoundException extends RuntimeException {

    public CompetaNotFoundException(final String template, Object... replacements) {
        super(MessageFormatter.arrayFormat(template, replacements).getMessage());
    }

    public CompetaNotFoundException(final String message) {
        super(message);
    }
}
