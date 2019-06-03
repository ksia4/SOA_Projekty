package webservice.parking;

import dao.ParkingSpaceDao;
import enums.ParkingSpaceState;
import parking.ParkingSpace;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    ParkingSpaceDao parkingSpace;

    public ParkingSpaceServiceImpl(){
        this.parkingSpace = new ParkingSpaceDao();
    }

    @Override
    public String changeParkingSpaceState(int id,boolean state) {

        if(state)
            parkingSpace.changeState(id,ParkingSpaceState.WAITING_FOR_PAYMENT);
        else
            parkingSpace.changeState(id,ParkingSpaceState.FREE);
        return "DONE!";

    }
}
