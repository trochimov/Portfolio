package se.group.projektarbete.web.filter;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.Priorities.AUTHENTICATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;

@ApiKey
@Provider
@Priority(AUTHENTICATION)
public final class ApiKeyFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String apikey = containerRequestContext.getHeaderString("api-key");
        if(!"password".equals(apikey)) {
            containerRequestContext.abortWith(status(UNAUTHORIZED)
                    .entity(singletonMap("error","missing/invalid api-key"))
                    .build());
        }
    }
}
