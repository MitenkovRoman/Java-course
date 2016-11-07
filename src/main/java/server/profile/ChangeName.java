package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authorized;
import server.info.AuthDataStorage;
import server.info.Token;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/profile")
public class ChangeName {
    private static final Logger log = LogManager.getLogger(ChangeName.class);

    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Authorized
    public Response changeName(@FormParam("name") String name,
                               @HeaderParam("Authorization") String authHeader) {
        Token token = new Token(Long.parseLong(authHeader.substring("Bearer".length()).trim()));
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            String oldName = AuthDataStorage.changeName(token, name);
            log.info("name Changed from '{}' to '{}'", oldName, name);
            return Response.ok("User " + oldName + " changed name to " + name + ".").build();
        } catch (Exception e){
            log.info("Can't change name");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
