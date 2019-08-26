package dao;

import parking.Employee;
import parking.ParkingSpace;

import javax.persistence.Query;
import javax.xml.registry.infomodel.User;
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

//    public void update(ParkingSpace s){
//        try{
//            em.getTransaction().begin();
//            em.merge(s);
//            em.getTransaction().commit();
//        }
//        catch (Exception e){
//            System.err.println("Error in ParkingSpaceDao->changeState()" + e);
//        }
//    }

    public List<ParkingSpace> getAllSpacesToPaid(int parkingId){
        String jpql = "select ps from ParkingSpace ps, Parking p where p.parkingId = :pid " +
                "and (ps.parkingSpaceState = 1 or ps.parkingSpaceState = 3)";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class)
                .setParameter("pid",parkingId)
                .getResultList();
        return result;
    }

    public List<ParkingSpace> getSpacesByUser(int employeeId){
        String jpql = "select ps from ParkingSpace ps, Parking p where " +
                "ps.parking.employee.employeeId = :eid";
        List<ParkingSpace> result = em.createQuery(jpql,ParkingSpace.class)
                .setParameter("eid",employeeId)
                .getResultList();
        return result;
    }
}
