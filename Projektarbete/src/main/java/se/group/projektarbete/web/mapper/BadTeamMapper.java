package se.group.projektarbete.web.mapper;

import se.group.projektarbete.service.exceptions.BadTeamException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class BadTeamMapper implements ExceptionMapper<BadTeamException> {

    @Override
    public Response toResponse(BadTeamException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Collections.singletonMap("error", e.getMessage()))
                .build();
    }
}
