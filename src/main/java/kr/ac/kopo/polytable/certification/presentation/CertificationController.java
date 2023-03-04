package kr.ac.kopo.polytable.certification.presentation;

import kr.ac.kopo.polytable.certification.application.CertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Slf4j
public class CertificationController {

    private final CertificationService certificationService;

    @GetMapping("/certifications")
    public ResponseEntity<String> certification(@RequestParam(name = "email")String email,
                                                @RequestParam(name = "token")String token) {

        if (certificationService.certification(email, token)) {
            return ResponseEntity.ok().body("COMPLETE");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL");
    }
}
