package parking;

//na razie tutaj wrzucam wszystkie metody, ktore nie wiem jak ulokowac
//pewnie bedziemy to rozbijac na mniejsze klasy

import dao.ParkingDao;
import dao.ParkingSpaceDao;
import dao.RegisteredPaymentDao;
import enums.ParkingSpaceState;

import java.time.LocalDateTime;
import java.util.List;

public class Logic {
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

        RegisteredPayment registeredPayment = new RegisteredPayment();
        registeredPayment.setPlate(p.getPlate());
        registeredPayment.setParking(parking);
        registeredPayment.setStartTime(LocalDateTime.now());
        registeredPayment.setEndTime(LocalDateTime.now().plusHours(p.getTimeInHours()));
        if(parkingSpace != null){
            registeredPayment.setParkingSpace(parkingSpace);
            changeParkingSpaceState(parkingSpace.getParkingSpaceId(),ParkingSpaceState.PAID);
        }
        registeredPaymentDao.save(registeredPayment);
    }

    private static ParkingSpace matchParkingSpace(Parking parking){
        List<ParkingSpace> allSpaces = parkingSpaceDao.getAllSpacesToPaid(parking.getParkingId());
        if(allSpaces.size() == 1)
            return allSpaces.get(0);
        return null;

        //jasli wincyj to niech wybierze randomowe na razie


    }
}
