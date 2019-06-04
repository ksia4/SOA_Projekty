package parking;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parkings")
public class Parking {

    private int parkingId;
    private int parkingSpacesNumber;        //rozwazyc czy to potrzebne
    private int freeParkingSpacesNumber;    //rozwazyc czy to potrzebne
    private Employee employee;
    @JsonIgnore
    private List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>(0);

    public Parking(){super();}

    public Parking(int parkingSpacesNumber){
        this.parkingSpacesNumber = parkingSpacesNumber;
        this.freeParkingSpacesNumber = parkingSpacesNumber;
    }

    @Id
    @GeneratedValue
    @Column(name = "PARKING_ID", unique = true, nullable = false)
    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    @Column(name = "PARKING_SPACES_NUMBER", nullable = false)
    public int getParkingSpacesNumber() {
        return parkingSpacesNumber;
    }

    public void setParkingSpacesNumber(int parkingSpacesNumber) {
        this.parkingSpacesNumber = parkingSpacesNumber;
    }

    @Column(name = "FREE_PARKING_SPACES_NUMBER", nullable = false)
    public int getFreeParkingSpacesNumber() {
        return freeParkingSpacesNumber;
    }

    public void setFreeParkingSpacesNumber(int freeParkingSpacesNumber) {
        this.freeParkingSpacesNumber = freeParkingSpacesNumber;
    }

    @OneToOne(mappedBy = "parking")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    @OneToMany(mappedBy = "parking")
    @JsonIgnore
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}
