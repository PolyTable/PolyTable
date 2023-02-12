package kr.ac.kopo.polytable.member.auth.presentation;

import kr.ac.kopo.polytable.global.jwt.dto.TokenDTO;
import kr.ac.kopo.polytable.global.jwt.vo.RefreshToken;
import kr.ac.kopo.polytable.member.auth.application.AuthService;
import kr.ac.kopo.polytable.member.auth.dto.LoginRequest;
import kr.ac.kopo.polytable.member.auth.dto.SimpleAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SimpleAuthResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenDTO token = authService.login(request.getUsername(), request.getPassword());
        RefreshToken refreshToken = token.getRefreshToken();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, getCookie(refreshToken).toString())
                .body(SimpleAuthResponse.from(token.getAccessToken(), true));
    }

    @PostMapping("/reissue")
    public ResponseEntity<SimpleAuthResponse> reissue(@CookieValue(name = "refreshToken") String refreshToken) {
        log.info("token : {}", refreshToken);
        return ResponseEntity.ok(SimpleAuthResponse.from(authService.reissue(refreshToken), true));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie myCookie = new Cookie("refreshToken", null);
        myCookie.setMaxAge(0);
        myCookie.setPath("/");
        response.addCookie(myCookie);

        return ResponseEntity.noContent().build();
    }

    private ResponseCookie getCookie(RefreshToken refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(18000)
                .build();
    }
}
