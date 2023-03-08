package kr.ac.kopo.polytable.auth.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.polytable.auth.application.AuthService;
import kr.ac.kopo.polytable.auth.dto.LoginRequest;
import kr.ac.kopo.polytable.global.jwt.TokenProvider;
import kr.ac.kopo.polytable.global.jwt.dto.TokenDTO;
import kr.ac.kopo.polytable.global.jwt.vo.RefreshToken;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetailsService;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired TokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @Test
    void authLoginTest() throws Exception {

        ObjectMapper om = new ObjectMapper();

        CreateRequest member = GetMemberInfo.bingingMember();
        memberService.create(member.toEntity());

        final String username = "test";
        final String password = "test";

        LoginRequest request = LoginRequest.of(username, password);

        String loginRequest = om.writeValueAsString(request);

        mockMvc.perform(post("/api/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test")
    void authLogoutTest() throws Exception {

        mockMvc.perform(delete("/api/logout"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = "test")
    void authReissueTest() throws Exception {

        CreateRequest member = GetMemberInfo.bingingMember();

        memberService.create(member.toEntity());

        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + RoleType.USER));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("test", "test", authorities);
        TokenDTO tokenDTO = tokenProvider.createToken("test", token);

        mockMvc.perform(post("/api/reissue")
                        .cookie(new Cookie("refreshToken", tokenDTO.getRefreshToken().refreshToken())))
                .andExpect(status().isOk())
                .andDo(print());
    }
}