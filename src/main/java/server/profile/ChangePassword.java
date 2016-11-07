package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authorized;
import server.info.AuthDataStorage;
import server.info.Token;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/profile")
public class ChangePassword {
    private static final Logger log = LogManager.getLogger(ChangeName.class);

    @POST
    @Path("password")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Authorized
    public Response changePassword(@FormParam("password") String password,
                                @HeaderParam("Authorization") String authHeader) {
        Token token = new Token(Long.parseLong(authHeader.substring("Bearer".length()).trim()));
        if (password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            String user = AuthDataStorage.changePassword(token, password);
            log.info("Password of user '{}' changed to '{}'", user, password);
            return Response.ok("User '" + user + "' changed password to " + password + ".").build();
        } catch (Exception e){
            log.info("Can't change password");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
