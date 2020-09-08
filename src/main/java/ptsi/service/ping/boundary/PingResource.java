package ptsi.service.ping.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Test API Endpoint using Microprofile Injection of Properties
 * @author alliancecoder <sboyd@electricaltrainingalliance.org>
 */
@Path("ping")
public class PingResource {

    @SuppressWarnings("cdi-ambiguous-dependency")
    @Inject
    @ConfigProperty(name = "message")
    String message;    

    @GET
    public String ping() {
        return this.message + " - Jakarta EE 8 with MicroProfile 3+!";
    }

}