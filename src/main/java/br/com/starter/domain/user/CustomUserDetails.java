package br.com.starter.domain.user;

import br.com.starter.domain.garage.Garage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final Optional<Garage> garage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }
    
    public User getUser() {
        return user;
    }
    public Optional<Garage> getGarage() {
        return garage;
    }

    @Override
    public String getPassword() {
        return user.getAuth().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAuth().getUsername();
    }

    public String getName() {
        return user.getProfile().getName();
    }
    
    public String getRole() {
        return user.getRole().getName();
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
