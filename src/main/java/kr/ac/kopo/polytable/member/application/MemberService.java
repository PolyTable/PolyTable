package kr.ac.kopo.polytable.member.application;


import kr.ac.kopo.polytable.customer.error.DuplicatePhoneNumberException;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.dto.MemberResponse;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.SimpleMemberResponse;
import kr.ac.kopo.polytable.member.error.DuplicateEmailException;
import kr.ac.kopo.polytable.member.error.DuplicateTelNoException;
import kr.ac.kopo.polytable.member.error.DuplicateUsernameException;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
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
        if (memberRepository.findByUsername(member.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("이미 가입된 아이디입니다.");
        }

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new DuplicateEmailException("이미 가입된 이메일입니다.");
        }

        if (memberRepository.findByTelNo(member.getTelNo()).isPresent()) {
            throw new DuplicateTelNoException("이미 가입된 전화번호입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.activatedAccount();

        Member savedMember = memberRepository.save(member);

        ModelMapper mapper = customModelMapper.standardMapper();

        return mapper.map(savedMember, SimpleMemberResponse.class);
    }

    public MemberResponse modified(final CustomUserDetails userDetails, final ModifiedRequest request) {
        Member member =
                memberRepository
                        .findById(userDetails.getId())
                        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

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
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
    }

    public void turnOffAccount(CustomUserDetails userDetails) {
        Member findMember =
                memberRepository
                        .findById(userDetails.getId())
                        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
        findMember.turnOffAccount();
        memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public MemberResponse getDetailBy(final CustomUserDetails userDetails) {

        return memberRepository.findById(userDetails.getId())
                .map(MemberResponse::of)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
    }
}
