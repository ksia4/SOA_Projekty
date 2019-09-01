package enums;

public enum ParkingSpaceState {
    FREE,                       // 0 - wolne
    WAITING_FOR_PAYMENT,        // 1 - w okienku czasowym na zapłacenie, po zaparkowaniu, jak i po wygaśnieciu
    PAID,                       // 2 - opłacone
    UNPAID,                     // 3 - nieopłacone i mineło 10 minut na zapłacenie
    PUNISHED                    // 4 - gość dostał mandat
}
