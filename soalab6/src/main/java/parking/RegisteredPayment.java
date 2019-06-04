package parking;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class RegisteredPayment {

    private int paymentId;
    private Parking parking;
    private ParkingSpace parkingSpace;
    private String plate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //    private PaymentStatus status; to bedzie kolejny enum

    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_ID", unique = true, nullable = false)
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_ID", nullable = false)
    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @OneToOne
    @JoinColumn(name = "PARKING_ID", nullable = false)
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @JoinColumn(name = "PLATE", nullable = false)
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @JoinColumn(name = "START_TIME", nullable = false)
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @JoinColumn(name = "END_TIME", nullable = false)
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



}
