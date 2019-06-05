package webservice.parkomat;

import dao.ParkingSpaceDao;
import parking.ParkingSpace;
import parking.PaymentRegistration;

import javax.ws.rs.*;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;


@Path("/parkomat")
public class ParkomatService {
    private ParkingSpaceDao dao;

    public ParkomatService() {
        this.dao = new ParkingSpaceDao();
    }

    //wywalic
    @GET
    @Path("getSpace/{id}")
    @Produces("application/json")
    public ParkingSpace getParkingSpace(@PathParam("id") String id){
        ParkingSpace result = dao.get(Integer.parseInt(id));
        return result;
    }

    @POST
    @Path("registerPayment")
    @Produces("application/json")
    public Response registerPayment(PaymentRegistration p){
        System.out.println("Dostalem auto: "+p.getPlate() +
                " parking " + p.getParkingId());
        return Response.status(201).entity(p.getPlate()).build();
    }

}
