package dev.cbos.o11yc.challenge01.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Path("/challenge")
public class ChallengeResource {

    record Challenge(int id, String name) {
    }

    private final List<Challenge> challenges = List.of(new Challenge(1, "Challenge 1"), new Challenge(2, "Challenge 2"));

    Logger logger = LoggerFactory.getLogger(ChallengeResource.class);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ManagedAsync
    public Response getAllChallenges() {
        logger.info("getAllChallenges");
        return Response.ok(challenges).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public Response getChallengeById(@PathParam("id") Integer id) {
        logger.info("getChallengeById: {}", id);
        Optional<Challenge> first = challenges.stream().filter(c -> c.id() == id).findFirst();
        if (first.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(first.get()).build();
        }
    }
}