package br.com.starter.domain.local;

import br.com.starter.domain.garage.Garage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {
    private final LocalRepository localRepository;

    public Local save(Local local) {
        return localRepository.save(local);
    }

    public void delete(Local local) {
        localRepository.delete(local);
    }

    public List<Local> findAllByGarageAndQuery(Garage garage, String query) {
        return localRepository.findAllByGarageAndQuery(
            garage.getId(),
            LocalStatus.ACTIVE,
            query
        );
    }
}
