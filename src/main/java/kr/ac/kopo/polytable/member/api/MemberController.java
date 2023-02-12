package kr.ac.kopo.polytable.member.api;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.MemberResponse;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.SimpleMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/api/members")
    public ResponseEntity<SimpleMemberResponse> create(@Valid @RequestBody CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(request.toEntity()));
    }

    @GetMapping("/api/members")
    public ResponseEntity<MemberResponse> getDetailById() {
        return ResponseEntity.ok().body(memberService.getDetailBy(this.getPrincipal()));
    }

    @PutMapping("/api/members")
    public ResponseEntity<MemberResponse> modifiedMemberInfo(@Valid @RequestBody ModifiedRequest request) throws URISyntaxException {
        return ResponseEntity.ok().body(memberService.modified(this.getPrincipal(), request));
    }

    @DeleteMapping("/api/members")
    public ResponseEntity<Void> turnOffAccount() {
        memberService.turnOffAccount(this.getPrincipal());
        return ResponseEntity.noContent().build();
    }


    private CustomUserDetails getPrincipal() {
        log.debug("principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
