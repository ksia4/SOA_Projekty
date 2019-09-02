package dao;

import parking.ParkingSpace;
import javax.persistence.Query;
import java.util.List;

public class ParkingSpaceDao extends AbstractDao<ParkingSpace> {

    public ParkingSpaceDao(){
        super();
    }

    @Override
    public ParkingSpace get(int id) {
        return em.find(ParkingSpace.class,id);
    }

    @Override
    public List<ParkingSpace> getAll() {
        Query query = em.createQuery("SELECT s FROM ParkingSpace s");
        return query.getResultList();
    }

    public List<ParkingSpace> getAllSpacesToPaid(int parkingId){
        String jpql = "select ps from ParkingSpace ps, Parking p where p.parkingId = :pid " +
                "and (ps.parkingSpaceState = 1 or ps.parkingSpaceState = 3)";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class)
                .setParameter("pid",parkingId)
                .getResultList();
        return result;
    }

    public List<ParkingSpace> getSpacesByUser(int employeeId){
        String jpql = "select DISTINCT ps from ParkingSpace ps, Parking p where " +
                "ps.parking.employee.employeeId = :eid";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class)
                .setParameter("eid",employeeId)
                .getResultList();
        return result;
    }

    public List<ParkingSpace> getSpacesToCorrection(int employeeId){
        String jpql = "select DISTINCT ps from ParkingSpace ps, Parking p where " +
                "ps.parking.employee.employeeId = :eid and ps.parkingSpaceState != 0";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class)
                .setParameter("eid",employeeId)
                .getResultList();
        return result;
    }

    public List<ParkingSpace> getAllNonEmptySpaces(){
        String jpql = "select DISTINCT ps from ParkingSpace ps where " +
                "ps.parkingSpaceState != 0";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class).getResultList();
        return result;
    }
}
