package dev.cbos.o11yc.challenge01.config;


import dev.cbos.o11yc.challenge01.resource.ChallengeResource;
import jakarta.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/spring-app")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ChallengeResource.class);
        register(ManagedAsyncExecutorProvider.class);
    }
}
