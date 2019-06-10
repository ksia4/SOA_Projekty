package dao;

import parking.RegisteredPayment;

import javax.persistence.Query;
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
}
