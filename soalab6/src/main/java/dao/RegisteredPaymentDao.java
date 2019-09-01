package dao;

import parking.ParkingSpace;
import parking.RegisteredPayment;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class RegisteredPaymentDao extends AbstractDao<RegisteredPayment>{
    @Override
    public RegisteredPayment get(int id) {
        return em.find(RegisteredPayment.class,id);
    }

    @Override
    List<RegisteredPayment> getAll() {
        Query query = em.createQuery("SELECT s FROM RegisteredPayment s");
        return query.getResultList();
    }

    public List<RegisteredPayment> getExpiredPayments(){
        LocalDateTime now = LocalDateTime.now();
        String jpql = "select p from RegisteredPayment p where p.alert = false " +
                "and p.endTime < :time and p.parkingSpace is not null " +
                "and p.parkingSpace.parkingSpaceState != 4";
        List<RegisteredPayment> result = em.createQuery(jpql,RegisteredPayment.class)
                .setParameter("time",now)
                .getResultList();
        return result;
    }

//    public void update(RegisteredPayment p){
//        try{
//            em.getTransaction().begin();
//            em.merge(p);
//            em.getTransaction().commit();
//        }
//        catch (Exception e){
//            System.err.println("Error in ParkingSpaceDao->changeState()" + e);
//        }
//    }
}
