package se.group.projektarbete.web;

import org.springframework.stereotype.Component;
import se.group.projektarbete.data.User;
import se.group.projektarbete.service.UserService;
import se.group.projektarbete.web.filter.ApiKey;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("users")
public final class UserResource {

    private final UserService userService;

    @Context
    private UriInfo uriInfo;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @ApiKey
    public Response createUser(User user) {
        User createdUser = userService.createUser(user);
        return Response.status(CREATED).header("Location",
                uriInfo.getAbsolutePathBuilder().path(createdUser.getUserNumber().toString())).build();
    }

    @PUT
    @Path("{userNumber}")
    public Response updateUserByUserNumber(@PathParam("userNumber") Long userNumber, User user) {
        if (userService.updateUser(userNumber, user)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @PUT
    @Path("{userNumber}/inactivate")
    public Response inactivateUser(@PathParam("userNumber") Long userNumber) {
        if (userService.inactivateUser(userNumber)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @GET
    @Path("{userNumber}")
    public Response getUserByUserNumber(@PathParam("userNumber") Long userNumber) {
        return userService.getUserByUsernumber(userNumber)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @GET
    @Path("team/{teamName}")
    public List<User> getAllUsersFromSpecificTeam(@PathParam("teamName") String teamName) {
        return userService.findAllUsersAtTeamByTeamName(teamName);
    }

    @GET
    public List<User> getUsersByFirstNameAndLastNameAndUserName
            (@QueryParam("firstName") String firstName,
             @QueryParam("lastName") String lastName,
             @QueryParam("userName") String userName) {
        return userService.findUsersByFirstNameAndLastNameAndUserName(firstName, lastName, userName);
    }
}