package parking;

import enums.ParkingSpaceState;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    private int parkingSpaceId;
    private ParkingSpaceState parkingSpaceState;
    private Parking parking;
    private RegisteredPayment payment;

    public ParkingSpace(){super();}

    public ParkingSpace(Parking parking){
        this.parking = parking;
        this.parkingSpaceState = ParkingSpaceState.FREE;
    }

    @Id
    @GeneratedValue
    @Column(name = "PARKING_SPACE_ID", unique = true, nullable = false)
    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_ID", nullable = false)
    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @Column(name = "PARKING_SPACE_STATE",nullable = false)
    public ParkingSpaceState getParkingSpaceState() {
        return parkingSpaceState;
    }

    public void setParkingSpaceState(ParkingSpaceState parkingSpaceState) {
        this.parkingSpaceState = parkingSpaceState;
    }

    @OneToOne(mappedBy = "parkingSpace")
    public RegisteredPayment getPayment() {
        return payment;
    }

    public void setPayment(RegisteredPayment payment) {
        this.payment = payment;
    }
}
