package parking;

import enums.ParkingSpaceState;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    private int parking_space_id;
    private ParkingSpaceState parkingSpaceState;
    private Date parkingStartTime;
    private Date paymentExpiry;
    @JsonIgnore
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
    public int getParking_space_id() {
        return parking_space_id;
    }

    public void setParking_space_id(int parking_space_id) {
        this.parking_space_id = parking_space_id;
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

    @Column(name = "PARKING_START_TIME")
    public Date getParkingStartTime() {
        return parkingStartTime;
    }

    public void setParkingStartTime(Date parkingStartTime) {
        this.parkingStartTime = parkingStartTime;
    }

    @Column(name = "PAYMENT_EXPIRY")
    public Date getPaymentExpiry() {
        return paymentExpiry;
    }

    public void setPaymentExpiry(Date paymentExpiry) {
        this.paymentExpiry = paymentExpiry;
    }

    @OneToOne(mappedBy = "parkingSpace")
    public RegisteredPayment getPayment() {
        return payment;
    }

    public void setPayment(RegisteredPayment payment) {
        this.payment = payment;
    }
}
