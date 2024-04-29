package world.pasds.back.member.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.authorities = authorities;
    }

    // Constructor
    public CustomUserDetails(Long memberId) {
        this.memberId = memberId;
    }

    // Override methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
