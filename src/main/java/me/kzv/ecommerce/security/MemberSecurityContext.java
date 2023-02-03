package me.kzv.ecommerce.security;

import me.kzv.ecommerce.domain.member.LocalMember;
import me.kzv.ecommerce.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public record MemberSecurityContext(
        Member member,
        Map<String, Object> attributes
) implements UserDetails, OAuth2User {

    // LOCAL 로그인
    public static MemberSecurityContext of(Member member) {
        return MemberSecurityContext.of(member, Map.of());
    }

    // SOCIAL 로그인
    public static MemberSecurityContext of(Member member, Map<String, Object> attributes) {
        return new MemberSecurityContext(member, attributes);
    }

    @Override
    public String getName() {
        return member.getUsername(); // 실제 고객 이름
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(member.getAuthorityType().getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        LocalMember member = (LocalMember) this.member;
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 고객 아이디
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
