package dao;

import parking.Parking;
import parking.ParkingSpace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ParkingDao extends AbstractDao<Parking> {
    public ParkingDao(){
        super();
    }

    @Override
    public Parking get(int id) {
        return em.find(Parking.class,id);
    }
}
