package dao;

import parking.RegisteredPayment;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class RegisteredPaymentDao extends AbstractDao<RegisteredPayment> {
    @Override
    public RegisteredPayment get(int id) {
        return em.find(RegisteredPayment.class,id);
    }

    @Override
    public List<RegisteredPayment> getAll() {
        Query query = em.createQuery("SELECT s FROM RegisteredPayment s");
        return query.getResultList();
    }

    public List<RegisteredPayment> getExpiredPayments(){
        LocalDateTime now = LocalDateTime.now();
        String jpql = "select p from RegisteredPayment p where p.alert = false " +
                "and p.endTime < :time";
        List<RegisteredPayment> result = em.createQuery(jpql,RegisteredPayment.class)
                .setParameter("time",now)
                .getResultList();
        return result;
    }

    public List<RegisteredPayment> getTicketsToCorrection(int parkingId){
        String jpql = "select p from RegisteredPayment p where p.parking.parkingId = :pid";
        List<RegisteredPayment> result = em.createQuery(jpql,RegisteredPayment.class)
                .setParameter("pid", parkingId)
                .getResultList();
        return result;
    }
}
