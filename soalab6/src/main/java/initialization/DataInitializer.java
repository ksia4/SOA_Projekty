package initialization;

import dao.EmployeeDao;
import dao.ParkingDao;
import dao.ParkingSpaceDao;
import enums.EmployeeRole;
import parking.Employee;
import parking.Parking;
import parking.ParkingSpace;

public class DataInitializer {
    public static void main(String[] args){
        EmployeeDao em = new EmployeeDao();
        ParkingDao pa = new ParkingDao();
        ParkingSpaceDao ps = new ParkingSpaceDao();

        Parking parking1 = new Parking("Urzednicza",10);
        Parking parking2 = new Parking("Krolewska",15);
        pa.save(parking1);
        pa.save(parking2);

        em.save(new Employee("Krzysztof","Jarzyna",EmployeeRole.ADMIN));
        em.save(new Employee("Jacek","Malina",EmployeeRole.PARKING_CONTROLLER));
        em.save(new Employee("Stanisław","Pierzyna",EmployeeRole.PARKING_CONTROLLER));
        em.save(new Employee("Zbigniew","Zwierzyna",EmployeeRole.PARKING_CONTROLLER));
        for (int i=0 ; i<10 ; ++i)
            ps.save(new ParkingSpace(parking1));
        for (int i=0 ; i<15 ; ++i)
            ps.save(new ParkingSpace(parking2));

    }
}
