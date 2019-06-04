package dao;

import enums.ParkingSpaceState;
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

    public void changeState(int id,ParkingSpaceState state){
        ParkingSpace space = get(id);
        space.setParkingSpaceState(state);//ACHTUNG, obsluzyc null, chociaz teoretycznie nie jest potrzebny bo to czujnik
        try{
            em.getTransaction().begin();
            em.merge(space);
            em.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("Error in ParkingSpaceDao->changeState()" + e);
        }



    }
}
