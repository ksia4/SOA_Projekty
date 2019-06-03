package dao;

import enums.ParkingSpaceState;
import parking.ParkingSpace;

public class ParkingSpaceDao extends AbstractDao<ParkingSpace> {

    public ParkingSpaceDao(){
        super();
    }

    @Override
    public ParkingSpace get(int id) {
        return em.find(ParkingSpace.class,id);
    }

    public void changeState(int id,ParkingSpaceState state){
        ParkingSpace space = get(id);
        if(space == null)
            System.out.println("Null kurwa");
        System.out.println("Jebane ID to " + space.getParking_space_id());
        System.out.println(space.getParkingSpaceState());
        space.setParkingSpaceState(state);
        System.out.println(space.getParkingSpaceState());
        em.getTransaction().begin();
        em.merge(space);
        em.getTransaction().commit();


    }
}
