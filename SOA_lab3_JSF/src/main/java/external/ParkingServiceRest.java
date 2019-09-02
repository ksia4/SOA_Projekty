//package external;
//
//import jdk.nashorn.internal.objects.annotations.Getter;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.awt.*;
//
//@Path("/area")
//public class ParkingServiceRest {
//    ParkingService service = new ParkingService();
//
//    @GET
//    @Path("/parkings")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getParkings(){
//        return Response.ok().entity(service.getParkingList()).build();
//    }
//
//    @GET
//    @Path("/{parking_id}/spaces")
//    public Response getNumberOfSpacesInArea(@PathParam("parking_id")Long parkingId){
//
//    }
//}
