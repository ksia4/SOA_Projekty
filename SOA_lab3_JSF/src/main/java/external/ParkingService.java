package external;

import dao.ParkingDao;
import parking.Parking;

import java.util.List;

public class ParkingService {
    ParkingDao parkingDao = new ParkingDao();

    public List<Parking> getParkingList(){
        return parkingDao.getAll();
    }
}
