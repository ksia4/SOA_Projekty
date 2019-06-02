package webservice.parking;

import dao.ParkingSpaceDao;
import enums.ParkingSpaceState;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ParkingSpaceService {

    private ParkingSpaceDao parkingSpace;

    public ParkingSpaceService(){
        this.parkingSpace = new ParkingSpaceDao();
    }

    @WebMethod
    public void setStateOfParkingPlace(ParkingSpaceState state){
        System.out.println("Zmienilem stan miejsca");
    }
}
