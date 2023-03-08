package kr.ac.kopo.polytable.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.polytable.auth.application.AuthService;
import kr.ac.kopo.polytable.auth.dto.LoginRequest;
import kr.ac.kopo.polytable.global.jwt.TokenProvider;
import kr.ac.kopo.polytable.global.jwt.dto.TokenDTO;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
@Transactional
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthService authService;

    @BeforeEach
    void initMember() {
        CreateRequest createRequest = GetMemberInfo.bingingMember();
        memberService.create(createRequest.toEntity());
    }

    @Test
    void authLoginTest() throws Exception {

        ObjectMapper om = new ObjectMapper();

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
    void authLogoutTest() throws Exception {

        authService.login("test", "test");

        mockMvc.perform(delete("/api/logout"))
                .andExpect(status().isNoContent());
    }

    @Test
    void authReissueTest() throws Exception {

        authService.login("test", "test");

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