package webservice.parking;

import events.EventHandler;
import events.NotificationHandler;

import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private boolean thread_log = false;

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
            EventHandler.handleCarArriveEvent(id);
        else
            EventHandler.handleCarLeavingEvent(id);
        return "DONE!";

    }
}
