package server.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.AuthDataStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/*
 curl -i -X GET -H "Host: localhost:8080" localhost:8080/data/leaderboard
*/

@Path("/data")
public class GetLeaders {
    private static final Logger log = LogManager.getLogger(GetUsers.class);
    private static final int N = 3;

    @GET
    @Path("leaderboard")
    @Produces("application/json")
    public Response getLeaders(){
        try {
            return Response.ok("{leaders : "+ AuthDataStorage.writeLeadersJson(N) +"}").build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
