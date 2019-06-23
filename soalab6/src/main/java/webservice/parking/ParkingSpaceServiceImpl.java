package webservice.parking;

import enums.ParkingSpaceState;
import events.EventHandler;
import events.NotificationHandler;

import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private boolean thread_log = false;

//    public ParkingSpaceServiceImpl(){
//
//    }

    @Override
    public String changeParkingSpaceState(int id,boolean state) {
        //Achtung - przemyslec czy to tutaj
        if (!thread_log){
            Thread thread = new Thread() {
                @Override
                public void run() {
                    NotificationHandler watchdog = new NotificationHandler();
                    watchdog.start();
                }
            };
            thread.start();
            thread_log = true;
        }

        if(state)
            EventHandler.changeParkingSpaceState(id,ParkingSpaceState.WAITING_FOR_PAYMENT);
        else
            EventHandler.changeParkingSpaceState(id,ParkingSpaceState.FREE);
        return "DONE!";

    }
}
