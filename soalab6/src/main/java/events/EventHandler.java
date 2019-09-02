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

    public static void handleCarArriveEvent(int id){
        ParkingSpace space = parkingSpaceDao.get(id);

        RegisteredPayment payment = new RegisteredPayment();
        payment.setParking(space.getParking());
        payment.setParkingSpace(space);
        payment.setStartTime(LocalDateTime.now());
        payment.setEndTime(LocalDateTime.now().plusSeconds(30));//sparametryzowac
        payment.setAlert(false);
        registeredPaymentDao.save(payment);

        space.setParkingSpaceState(ParkingSpaceState.WAITING_FOR_PAYMENT);
        space.setPayment(payment);
        parkingSpaceDao.update(space);
        //zaktualizowac parking pod wzgledem wolnych miejsc, jesli trzeba
    }

    public static void handleCarLeavingEvent(int id){
        ParkingSpace space = parkingSpaceDao.get(id);
        RegisteredPayment payment = space.getPayment();

        space.setParkingSpaceState(ParkingSpaceState.FREE);
        space.setPayment(null);

        payment.setAlert(false);
        payment.setParkingSpace(null);

        parkingSpaceDao.save(space);
        registeredPaymentDao.save(payment);
    }

    public static void registerPayment(PaymentRegistration p){
        ParkingSpace parkingSpace = matchParkingSpace(p);
        RegisteredPayment payment = parkingSpace.getPayment();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(p.getTimeInHours());
        payment.setPlate(p.getPlate());
        payment.setStartTime(startTime);
        payment.setEndTime(endTime);
        payment.setAlert(false);
        parkingSpace.setParkingSpaceState(ParkingSpaceState.PAID);
        parkingSpaceDao.update(parkingSpace);
        registeredPaymentDao.update(payment);
    }

    public static ParkingSpace matchParkingSpace(PaymentRegistration p){
        ParkingSpace resultSpace = parkingSpaceDao.getSpaceByPlate(p.getPlate());
        if(resultSpace == null){
            Parking parking = parkingDao.get(p.getParkingId());
            List<ParkingSpace> unpaidSpaces = new ArrayList<ParkingSpace>(parkingSpaceDao.getAllSpacesToPaid(parking.getParkingId()));
            resultSpace = unpaidSpaces.get(0);
            for(ParkingSpace ps : unpaidSpaces){
                if(ps.getPayment().getStartTime().isBefore(resultSpace.getPayment().getStartTime())){
                    resultSpace = ps;
                }
            }
        }
        return resultSpace;
    }

    public static void handlePaymentTimeOut(RegisteredPayment p, ParkingSpaceState state){
        ParkingSpace space = p.getParkingSpace();
        space.setParkingSpaceState(state);
        parkingSpaceDao.update(space);
    }

    public static void changeParkingSpaceStatus(int id){
        ParkingSpace space = parkingSpaceDao.get(id);
        boolean state = space.getParkingSpaceState() == ParkingSpaceState.FREE;
        if(state)
            handleCarArriveEvent(id);
        else
            handleCarLeavingEvent(id);
    }
}
