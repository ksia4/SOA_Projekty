package webservice.parking;

import dao.ParkingSpaceDao;
import enums.ParkingSpaceState;
import parking.Logic;
import parking.ParkingSpace;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

//    public ParkingSpaceServiceImpl(){
//
//    }

    @Override
    public String changeParkingSpaceState(int id,boolean state) {

        if(state)
            Logic.changeParkingSpaceState(id,ParkingSpaceState.WAITING_FOR_PAYMENT);
        else
            Logic.changeParkingSpaceState(id,ParkingSpaceState.FREE);
        return "DONE!";

    }
}
