package events;

import dao.ParkingDao;
import dao.ParkingSpaceDao;
import dao.RegisteredPaymentDao;
import enums.ParkingSpaceState;
import parking.Parking;
import parking.ParkingSpace;
import parking.PaymentRegistration;
import parking.RegisteredPayment;
import javax.ejb.Singleton;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Singleton
public class EventHandler {
    private static ParkingSpaceDao parkingSpaceDao = new ParkingSpaceDao();
    private static ParkingDao parkingDao = new ParkingDao();
    private static RegisteredPaymentDao registeredPaymentDao = new RegisteredPaymentDao();

    public static void changeParkingSpaceState(int id, ParkingSpaceState state){
        ParkingSpace space = parkingSpaceDao.get(id);
        space.setParkingSpaceState(state);
        parkingSpaceDao.update(space);
        //zaktualizowac parking pod wzgledem wolnych miejsc, jesli trzeba
    }

    public static void registerPayment(PaymentRegistration p){
        Parking parking = parkingDao.get(p.getParkingId());
        ParkingSpace parkingSpace = matchParkingSpace(parking);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(p.getTimeInHours());

        RegisteredPayment registeredPayment = new RegisteredPayment();
        registeredPayment.setPlate(p.getPlate());
        registeredPayment.setParking(parking);
        registeredPayment.setStartTime(startTime);
        registeredPayment.setEndTime(endTime);
        registeredPayment.setParkingSpace(parkingSpace);
        changeParkingSpaceState(parkingSpace.getParkingSpaceId(),ParkingSpaceState.PAID);

        registeredPaymentDao.save(registeredPayment);
    }

    private static ParkingSpace matchParkingSpace(Parking parking){
        List<ParkingSpace> allSpaces = new ArrayList<>(parkingSpaceDao.getAllSpacesToPaid(parking.getParkingId()));
        return allSpaces.get(0);
    }
}
