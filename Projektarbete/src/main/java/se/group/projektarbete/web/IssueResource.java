package se.group.projektarbete.web;

import org.springframework.stereotype.Component;
import se.group.projektarbete.data.Issue;
import se.group.projektarbete.service.IssueService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@Component
@Path("issues")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)

public final class IssueResource {

    private final IssueService issueService;

    public IssueResource(IssueService issueService) {
        this.issueService = issueService;
    }

    @PUT
    @Path("{id}")
    public Response updateIssue(@PathParam("id") Long id, Issue issue) {
        if (issueService.updateIssue(id, issue)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }
}