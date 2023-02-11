//package kr.ac.kopo.polytable.reservationtime.presentation;
//
//import kr.ac.kopo.polytable.member.model.Member;
//import kr.ac.kopo.polytable.reservationtime.application.ReservationTimeService;
//import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeSaveRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/reservationTime")
//public class ReservationTimeController {
//
//    private final ReservationTimeService reservationTimeService;
//
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void createReservationTime(@Validated @RequestBody ReservationTimeSaveRequest request,
//                                      BindingResult bindingResult, @AuthenticationPrincipal Member member) {
//
//        String arr = "dfsa".toLowerCase();
//
//        if(request.getStartTime().isBefore(request.getEndTime())){
//            bindingResult.addError();
//        }
//
//    }
//
//}
