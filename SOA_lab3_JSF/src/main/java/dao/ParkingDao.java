package dao;

import parking.Parking;

import javax.persistence.Query;
import java.util.List;

public class ParkingDao extends AbstractDao<Parking> {
    public ParkingDao(){
        super();
    }

    @Override
    public Parking get(int id) {
        return em.find(Parking.class,id);
    }

    @Override
    public List<Parking> getAll() {
        Query query = em.createQuery("SELECT p FROM Parking p");
        return query.getResultList();
    }
}
