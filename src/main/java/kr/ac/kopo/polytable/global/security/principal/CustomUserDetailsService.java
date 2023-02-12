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
            new MemberNotFoundException();
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(final String id) throws UsernameNotFoundException {
        return memberRepository.findByIdWithDetails(Long.valueOf(id))
                .orElseThrow(() -> NOT_FOUND_EXCEPTION);
    }
}
