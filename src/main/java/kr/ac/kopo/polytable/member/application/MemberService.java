package kr.ac.kopo.polytable.member.application;


import kr.ac.kopo.polytable.global.error.exception.ErrorCode;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.dto.MemberResponse;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.SimpleMemberResponse;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.MemberRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {


    private final MemberRepository memberRepository;
    private final CustomModelMapper customModelMapper;

    private final PasswordEncoder passwordEncoder;

    public SimpleMemberResponse create(final Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.activatedAccount();

        Member savedMember = memberRepository.save(member);

        ModelMapper mapper = customModelMapper.standardMapper();

        return mapper.map(savedMember, SimpleMemberResponse.class);
    }

    public MemberResponse modified(final CustomUserDetails userDetails, final ModifiedRequest request) {
        Member member = memberRepository.findById(userDetails.getId()).orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (request.getEmail() != null) {
            member.modifiedEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            String encodePassword = passwordEncoder.encode(request.getPassword());
            member.modifiedPwd(encodePassword);
        }

        if (request.getBirthDate() != null) {
            member.modifiedBirthDate(request.getBirthDate());
        }

        if (request.getName() != null) {
            member.modifiedName(request.getName());
        }

        if (request.getTelNo() != null) {
            member.modifiedTelNo(request.getTelNo());
        }

        memberRepository.save(member);

        return memberRepository.findById(userDetails.getId())
                .map(MemberResponse::of)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public void turnOffAccount(CustomUserDetails userDetails) {
        Member findMember = memberRepository.findById(userDetails.getId()).orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
        findMember.turnOffAccount();
    }

    @Transactional(readOnly = true)
    public MemberResponse getDetailBy(final CustomUserDetails userDetails) {

        return memberRepository.findById(userDetails.getId())
                .map(MemberResponse::of)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }


}
