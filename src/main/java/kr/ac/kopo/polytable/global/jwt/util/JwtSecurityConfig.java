package kr.ac.kopo.polytable.global.jwt.util;

import kr.ac.kopo.polytable.global.jwt.JwtFilter;
import kr.ac.kopo.polytable.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
