package kr.ac.kopo.polytable.auth.application;

import kr.ac.kopo.polytable.global.jwt.TokenProvider;
import kr.ac.kopo.polytable.global.jwt.dto.TokenDTO;
import kr.ac.kopo.polytable.global.jwt.error.TokenNotFoundException;
import kr.ac.kopo.polytable.global.jwt.vo.AccessToken;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.auth.error.MemberInfoMismatchException;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;

    private static final String ERROR_NO_MEMBER = "아이디나 비밀번호가 일치하지 않습니다.";
    private final PasswordEncoder passwordEncoder;

    public TokenDTO login(final String username, final String password) {
        Member member = memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        String encodePassword = passwordEncoder.encode(password);

        if (passwordEncoder.matches(password, member.getPassword())){
            CustomUserDetails userDetails = memberRepository.findUserDetailsByUsername(username)
                    .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, encodePassword);

            Authentication authentication = managerBuilder.getObject().authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.createToken(userDetails.getUsername(), authentication);
        } else throw new MemberInfoMismatchException(ERROR_NO_MEMBER);
    }

    public AccessToken reissue(final String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new TokenNotFoundException();
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(principal.getUsername(), authentication).getAccessToken();
    }

}
