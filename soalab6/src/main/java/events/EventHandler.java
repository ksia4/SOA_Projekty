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

        RegisteredPayment payment = new RegisteredPayment();
        payment.setParking(space.getParking());
        payment.setParkingSpace(space);
        payment.setStartTime(LocalDateTime.now());
        payment.setEndTime(LocalDateTime.now().plusSeconds(30));
        payment.setAlert(false);
        registeredPaymentDao.save(payment);

        space.setParkingSpaceState(state);
        space.setPayment(payment);
        parkingSpaceDao.update(space);
        //zaktualizowac parking pod wzgledem wolnych miejsc, jesli trzeba
    }

    public static void registerPayment(PaymentRegistration p){
        Parking parking = parkingDao.get(p.getParkingId());
        ParkingSpace parkingSpace = matchParkingSpace(parking);
        RegisteredPayment payment = registeredPaymentDao.get(parkingSpace.getPayment().getPaymentId());
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(p.getTimeInHours());

        payment.setPlate(p.getPlate());
        payment.setStartTime(startTime);
        payment.setEndTime(endTime);
        parkingSpace.setParkingSpaceState(ParkingSpaceState.PAID);
        //changeParkingSpaceState(parkingSpace.getParkingSpaceId(),ParkingSpaceState.PAID);
        parkingSpaceDao.update(parkingSpace);
        registeredPaymentDao.update(payment);
    }

    private static ParkingSpace matchParkingSpace(Parking parking){
        List<ParkingSpace> allSpaces = new ArrayList<>(parkingSpaceDao.getAllSpacesToPaid(parking.getParkingId()));
        return allSpaces.get(0);
    }
}
