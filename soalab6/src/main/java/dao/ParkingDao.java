package dao;

import parking.Parking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ParkingDao extends AbstractDao<Parking> {
    public ParkingDao(){
        super();
    }
}
