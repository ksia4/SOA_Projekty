package webservice.parking;

import enums.ParkingSpaceState;
import events.EventHandler;

import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

//    public ParkingSpaceServiceImpl(){
//
//    }

    @Override
    public String changeParkingSpaceState(int id,boolean state) {

        if(state)
            EventHandler.changeParkingSpaceState(id,ParkingSpaceState.WAITING_FOR_PAYMENT);
        else
            EventHandler.changeParkingSpaceState(id,ParkingSpaceState.FREE);
        return "DONE!";

    }
}
