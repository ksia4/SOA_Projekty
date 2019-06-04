package parking;


import java.time.LocalDateTime;


public class PaymentRegistration {
    private int parkingId;
    private String plate;
    private int timeInHours;

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getTimeInHours() {
        return timeInHours;
    }

    public void setTimeInHours(int timeInHours) {
        this.timeInHours = timeInHours;
    }

}
