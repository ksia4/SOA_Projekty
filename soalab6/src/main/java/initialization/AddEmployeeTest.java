package initialization;

import dao.EmployeeDao;
import dao.ParkingDao;
import dao.ParkingSpaceDao;
import enums.EmployeeRole;
import parking.Employee;
import parking.Parking;
import parking.ParkingSpace;

public class AddEmployeeTest {
    public static void main(String[] args){
        EmployeeDao em = new EmployeeDao();
//        ParkingDao pa = new ParkingDao();
//        ParkingSpaceDao ps = new ParkingSpaceDao();
//
//        Parking parking = new Parking(10);
//        pa.save(parking);

//        em.save(new Employee("Krzysztof","Jarzyna",EmployeeRole.ADMIN));
//        em.save(new Employee("Jacek","Malina",EmployeeRole.PARKING_CONTROLLER));
//        em.save(new Employee("Stanisław","Pierzyna",EmployeeRole.PARKING_CONTROLLER));
        em.save(new Employee("Zbigniew","Jerzyna",EmployeeRole.PARKING_CONTROLLER));
//        for (int i=0 ; i<10 ; ++i)
//            ps.save(new ParkingSpace(parking));

    }
}
