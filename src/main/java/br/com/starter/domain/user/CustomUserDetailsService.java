package br.com.starter.domain.user;

import java.util.Optional;

import br.com.starter.domain.garage.GarageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GarageService garageService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByAuthUsername(username);

        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");

        var usetEntity = user.get();
        var garage = garageService.getByUser(usetEntity);

        if(garage.isPresent()) {
            var garageEntity = garage.get();
            if (garageEntity.getOwner().getStatus() == UserStatus.INACTIVE)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário sem acesso ao sistema!");
        }

        return new CustomUserDetails(usetEntity);
    }
}

