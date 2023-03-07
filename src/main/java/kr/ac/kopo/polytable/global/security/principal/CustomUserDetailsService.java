package kr.ac.kopo.polytable.global.security.principal;

import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public static final MemberNotFoundException NOT_FOUND_EXCEPTION =
            new MemberNotFoundException("존재하지 않는 사용자입니다.");
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return memberRepository.findUserDetailsByUsername(username)
                .orElseThrow(() -> NOT_FOUND_EXCEPTION);
    }
}
