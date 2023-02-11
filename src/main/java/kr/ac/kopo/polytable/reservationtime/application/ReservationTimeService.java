package kr.ac.kopo.polytable.reservationtime.application;

import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;



}
