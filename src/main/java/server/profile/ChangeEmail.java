package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authorized;
import server.info.AuthDataStorage;
import server.info.Token;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/profile")
public class ChangeEmail {
    private static final Logger log = LogManager.getLogger(ChangeName.class);

    @POST
    @Path("email")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Authorized
    public Response changeEmail(@FormParam("email") String email,
                               @HeaderParam("Authorization") String authHeader) {
        Token token = new Token(Long.parseLong(authHeader.substring("Bearer".length()).trim()));
        if (email == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            String user = AuthDataStorage.changeEmail(token, email);
            log.info("Email of user '{}' changed to '{}'", user, email);
            return Response.ok("User '" + user + "' changed email to " + email + ".").build();
        } catch (Exception e){
            log.info("Can't change email");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
