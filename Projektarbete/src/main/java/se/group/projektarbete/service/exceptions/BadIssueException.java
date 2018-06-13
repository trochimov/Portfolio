package se.group.projektarbete.service.exceptions;

import javax.ws.rs.ext.Provider;

@Provider
public class BadIssueException extends RuntimeException {
    public BadIssueException(String message) {
        super(message);
    }
}