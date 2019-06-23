package dashboard;

import dao.ParkingSpaceDao;
import parking.ParkingSpace;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name="parkingSpaces")
public class ParkingSpacesBean {

    private ParkingSpaceDao parkingSpaceDao;

    public ParkingSpacesBean(){
        this.parkingSpaceDao = new ParkingSpaceDao();
    }

    public List<ParkingSpace> getAll(){
        return parkingSpaceDao.getAll();
    }
}
