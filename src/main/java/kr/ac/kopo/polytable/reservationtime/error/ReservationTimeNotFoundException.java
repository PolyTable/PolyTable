package kr.ac.kopo.polytable.reservationtime.error;

public class ReservationTimeNotFoundException extends RuntimeException{
    public ReservationTimeNotFoundException(String message) {
        super(message);
    }
}
