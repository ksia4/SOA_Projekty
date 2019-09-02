package webservice.parking;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ParkingSpaceService {

    @WebMethod
    String changeParkingSpaceState(int id/*, boolean state*/);
}
