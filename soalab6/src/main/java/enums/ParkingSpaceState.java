package enums;

public enum ParkingSpaceState {
    FREE,                       //wolne
    WAITING_FOR_PAYMENT,        //w okienku czasowym na zapłacenie, po zaparkowaniu, jak i po wygaśnieciu
    PAID,                       //opłacone
    UNPAID,                     //nieopłacone i mineło 10 minut na zapłacenie
    PUNISHED                    //gość dostał mandat
}
