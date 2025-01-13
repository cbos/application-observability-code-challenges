package dev.cbos.o11yc.challenge01.resource;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Path("/challenge")
@Component
public class ChallengeResource {

    public static final long FIVE_MINUTES = Duration.of(5, ChronoUnit.MINUTES).toMillis();

    record Challenge(int id, String name) {
    }

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private List<Challenge> challenges = Collections.emptyList();

    Logger logger = LoggerFactory.getLogger(ChallengeResource.class);

    @PostConstruct
    public void init() {
        challenges = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            challenges.add(new Challenge(i, "Challenge " + i));
        }
        logger.info("ChallengeResource initialized");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ManagedAsync
    public Response getAllChallenges() throws InterruptedException {
        logger.info("getAllChallenges");

        List<Challenge> allChallenges = handleRequest(-1);
        return Response.ok(allChallenges).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public Response getChallengeById(@PathParam("id") Integer id) throws InterruptedException {
        logger.info("getChallengeById");

        List<Challenge> challenges = handleRequest(id);
        if (challenges.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(challenges).build();
        }
    }

    private List<Challenge> handleRequest(int id) throws InterruptedException {
        Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 300.0))); // Simulate some heavy work

        if (id < 0) {
            return challenges;
        } else {

            if (id > 20) {
                // Simulate a long running request, e.g. a hanging downstream dependency or a slow database query
                Thread.sleep(FIVE_MINUTES);
                logger.error("Slow and invalid id: {}", id);
                return Collections.emptyList();
            }

            return challenges.stream().filter(c -> c.id() == id).toList();
        }
    }
}