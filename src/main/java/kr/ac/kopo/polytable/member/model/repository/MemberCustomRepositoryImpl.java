package kr.ac.kopo.polytable.member.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static kr.ac.kopo.polytable.member.model.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<CustomUserDetails> findUserDetailsByUsername(final String username) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                        member.id.as("id"),
                        member.username,
                        member.roleType))
                        .from(member)
                        .where(member.username.eq(username))
                        .fetchOne());
    }

    @Override
    public Optional<CustomUserDetails> findByIdWithDetails(Long memberId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                        member.id.as("id"),
                        member.username,
                        member.roleType))
                        .from(member)
                        .where(member.id.eq(memberId))
                        .fetchOne());
    }
}
