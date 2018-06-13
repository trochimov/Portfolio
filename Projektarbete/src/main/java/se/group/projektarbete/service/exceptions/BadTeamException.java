package se.group.projektarbete.service.exceptions;

import javax.ws.rs.ext.Provider;

@Provider
public class BadTeamException extends RuntimeException{
    public BadTeamException(String message){
        super(message);
    }
}
