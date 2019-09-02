package webservice.parking;

import dao.ParkingSpaceDao;
import events.EventHandler;
import events.NotificationHandler;
import parking.ParkingSpace;

import javax.jws.WebService;

@WebService(endpointInterface = "webservice.parking.ParkingSpaceService")
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private boolean thread_log = false;
//    ParkingSpaceDao parkingSpaceDao = new ParkingSpaceDao();

    @Override
    public String changeParkingSpaceState(int id/*,boolean state*/) {
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

        EventHandler.changeParkingSpaceStatus(id);
//        if(state)
//            EventHandler.handleCarArriveEvent(id);
//        else
//            EventHandler.handleCarLeavingEvent(id);
        return "DONE!";

    }
}
