package com.outsourcing.common.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    //사용자 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // 사용자 비밀번호 반환
    @Override
    public String getPassword() {
        return this.password;
    }

    //스프링 시큐리티의 username = username
    @Override
    public String getUsername() {
        return this.username;
    }
}
