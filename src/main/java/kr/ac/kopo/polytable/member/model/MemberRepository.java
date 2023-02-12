package kr.ac.kopo.polytable.member.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByName(final String name);

    Optional<Member> findByUsername(final String username);
}
