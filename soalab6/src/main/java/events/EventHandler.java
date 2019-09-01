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
//        Parking parking = parkingDao.get(p.getParkingId()); //usunac
        ParkingSpace parkingSpace = matchParkingSpace(p);
        RegisteredPayment payment = parkingSpace.getPayment();
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

//    private static ParkingSpace matchParkingSpace(Parking parking){
//        List<ParkingSpace> allSpaces = new ArrayList<>(parkingSpaceDao.getAllSpacesToPaid(parking.getParkingId()));
//        return allSpaces.get(0);
//    }

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
        //wizecie miejsca parkingowego po rejestracji
        //jezeli zwroci null to zbieramy najstarsze nieoplacone miejsce i jeszcze nie ukarane
        //jak dokupil pod rejestracje to stary bilecik mozna albo zaktualizowac albo wywalic z bazy
        //ale raczej wywalic bo bilet bedzie mial nowe id
    }
}
