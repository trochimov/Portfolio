package se.group.projektarbete.web;


import org.springframework.stereotype.Component;
import se.group.projektarbete.data.Issue;
import se.group.projektarbete.data.WorkItem;
import se.group.projektarbete.data.workitemenum.Status;
import se.group.projektarbete.service.IssueService;
import se.group.projektarbete.service.WorkItemService;
import se.group.projektarbete.web.filter.ApiKey;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("workitems")
public class WorkItemResource {

    private final WorkItemService workItemService;
    private final IssueService issueService;

    @Context
    private UriInfo uriInfo;

    public WorkItemResource(WorkItemService workItemService, IssueService issueService) {
        this.workItemService = workItemService;
        this.issueService = issueService;
    }

    @POST
    @ApiKey
    public Response createWorkItem(WorkItem workItem) {
        WorkItem createdWorkItem = workItemService.createWorkItem(workItem);

        return Response.status(CREATED).header("Location",
                uriInfo.getAbsolutePathBuilder().path(createdWorkItem.getId().toString())).build();
    }

    @POST
    @ApiKey
    @Path("{id}/issues")
    public Response addIssueToWorkItem(@PathParam("id") Long id, Issue issue) {
        workItemService.addIssueToWorkItem(id, issue);
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{id}/users/{userId}")
    public Response addWorkItemByUserId(@PathParam("id") Long workItemId,
                                        @PathParam("userId") Long userId) {
        workItemService.addWorkItemByUserId(workItemId, userId);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}/status")
    public Response changeStatus(@PathParam("id") Long workItemId,
                                 @QueryParam("set") String status) {
        if (workItemService.changeStatus(workItemId, status)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public List<WorkItem> getAllWorkItem() {
        return workItemService.getAllItems();
    }

    @GET
    @Path("{id}")
    public Response getWorkItem(@PathParam("id") Long id) {
        return  workItemService.getItem(id)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @GET
    @Path("team/{id}")
    public List<WorkItem> getAllWorkItemsForTeam(@PathParam("id") Long teamId) {
        return workItemService.findAllWorkItemsByTeamId(teamId);
    }

    @GET
    @Path("user/{id}")
    public List<WorkItem> getAllWorkItemsForUser(@PathParam("id") Long userId) {
        return workItemService.findAllWorkItemsByUserId(userId);
    }

    @GET
    @Path("desc/{description}")
    public List<WorkItem> getWorkItemsByDescription(@PathParam("description") String value) {
        return workItemService.findAllWorkItemsByDescription(value);
    }

    @GET
    @Path("issues")
    public List<WorkItem> getAllWorkItemsWithIssues() {
        return workItemService.getAllWorkItemsWithIssues();
    }

    @GET
    @Path("stat/{statusValue}")
    public List<WorkItem> getAllWorkItemsByStatus(@PathParam("statusValue") Status statValue) {
        return workItemService.findAllWorkItemsByStatus(statValue);
    }

    @GET
    @Path("date")
    public List<WorkItem> getAllWorkItemsByDatesAndStatus(@QueryParam("start") String startDate,
                                                          @QueryParam("end") String endDate) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return workItemService.findDoneWorkItemsByDates(LocalDate.parse(startDate,dateformatter),
                                                                LocalDate.parse(endDate, dateformatter));
    }

    @DELETE
    @Path("{id}")
    public Response deleteWorkItem(@PathParam("id") Long id) {
        workItemService.deleteWorkItem(id);
        return Response.status(NO_CONTENT).build();
    }
}