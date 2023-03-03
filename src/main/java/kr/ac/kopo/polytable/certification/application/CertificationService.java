package kr.ac.kopo.polytable.certification.application;

import kr.ac.kopo.polytable.global.util.RedisUtil;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificationService {

    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    public boolean certification(String email, String token) {
        String savedToken = redisUtil.getData(email);
        if (savedToken.equals(token)) {
            Member findMember = memberRepository.findByEmail(email).orElseThrow(() ->
                    new MemberNotFoundException("존재하지 않는 회원입니다."));
            findMember.activatedAccount();
            return true;
        } else
            return false;
    }
}
