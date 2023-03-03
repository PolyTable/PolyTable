package kr.ac.kopo.polytable.reservationtime.presentation;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import kr.ac.kopo.polytable.reservationtime.application.ReservationTimeService;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeResponse;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeSaveRequest;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeUpdateRequest;
import kr.ac.kopo.polytable.reservationtime.dto.SimpleReservationTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservationTime")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;
    private final CustomModelMapper customModelMapper;

    
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public SimpleReservationTimeResponse createReservationTime(@Validated @RequestBody ReservationTimeSaveRequest request,
                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long reservationTimeId = reservationTimeService.create(request.toEntity(), customUserDetails.getId());
        return new SimpleReservationTimeResponse(reservationTimeId, true);
    }

    @GetMapping
    public List<ReservationTimeResponse> findAll(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return reservationTimeService.findAll(customUserDetails.getId());
    }

    @GetMapping("/{reservationTimeId}")
    public ReservationTimeResponse findById(@PathVariable Long reservationTimeId,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return reservationTimeService.findById(reservationTimeId, customUserDetails.getId());
    }

    @PutMapping("/{reservationTimeId}")
    public void update(@Validated @RequestBody ReservationTimeUpdateRequest updateRequest,
                                          @PathVariable Long reservationTimeId,
                                          @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        reservationTimeService.update(updateRequest.toEntity(), reservationTimeId, customUserDetails.getId());
    }

    @DeleteMapping("/{reservationTimeId}")
    public void delete(@PathVariable Long reservationTimeId,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        reservationTimeService.delete(reservationTimeId, customUserDetails.getId());
    }


}
