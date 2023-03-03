package kr.ac.kopo.polytable.reservation.model.repository;

import kr.ac.kopo.polytable.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, CustomReservationRepository {


}
