package server.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.AuthDataStorage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/*
 curl -i -X GET -H "Host: localhost:8080" -i localhost:8080/data/users
*/

@Path("/data")
public class GetUsers {
    private static final Logger log = LogManager.getLogger(GetUsers.class);
    @GET
    @Path("users")
    @Produces("application/json")
    public Response getUsers(){
        try {
            return Response.ok("{users : "+ AuthDataStorage.writeUsersJson() +"}").build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
