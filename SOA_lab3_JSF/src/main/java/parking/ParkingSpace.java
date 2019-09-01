package parking;

import enums.ParkingSpaceState;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace implements Serializable {
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

//    @Column(name = "PARKING_START_TIME")
//    public Date getParkingStartTime() {
//        return parkingStartTime;
//    }
//
//    public void setParkingStartTime(Date parkingStartTime) {
//        this.parkingStartTime = parkingStartTime;
//    }
//
//    @Column(name = "PAYMENT_EXPIRY")
//    public Date getPaymentExpiry() {
//        return paymentExpiry;
//    }
//
//    public void setPaymentExpiry(Date paymentExpiry) {
//        this.paymentExpiry = paymentExpiry;
//    }

    @OneToOne(mappedBy = "parkingSpace")
    public RegisteredPayment getPayment() {
        return payment;
    }

    public void setPayment(RegisteredPayment payment) {
        this.payment = payment;
    }
}
