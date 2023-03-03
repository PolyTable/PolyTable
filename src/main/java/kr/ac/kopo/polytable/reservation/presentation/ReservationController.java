package kr.ac.kopo.polytable.reservation.presentation;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.reservation.application.ReservationService;
import kr.ac.kopo.polytable.reservation.dto.ReservationResponse;
import kr.ac.kopo.polytable.reservation.dto.ReservationSaveRequest;
import kr.ac.kopo.polytable.reservation.dto.ReservationUpdateRequest;
import kr.ac.kopo.polytable.reservation.dto.SimpleReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationResponse> findAll(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return reservationService.findAllByReservationResponse(customUserDetails.getId());
    }

    @PostMapping("{reservationTimeId}")
    public SimpleReservationResponse create(@Validated @RequestBody ReservationSaveRequest request,
                                            @PathVariable Long reservationTimeId,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long reservationId = reservationService
                        .create(request.toEntity(), reservationTimeId, request.getPhone(), customUserDetails.getId());
        return new SimpleReservationResponse(reservationId, true);
    }

    @PutMapping("{reservationId}")
    public void update(@Validated @RequestBody ReservationUpdateRequest request,
                                      @PathVariable Long reservationId,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        reservationService.update(request.toEntity(), reservationId, customUserDetails.getId());
    }

    @DeleteMapping("{reservationId}")
    public void delete(@PathVariable Long reservationId,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        reservationService.delete(reservationId, customUserDetails.getId());
    }

}
