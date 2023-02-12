package kr.ac.kopo.polytable.member.model.repository;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<CustomUserDetails> findUserDetailsByUsername(final String username);
    Optional<CustomUserDetails> findByIdWithDetails(final Long memberId);
}
