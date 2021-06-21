package eu.wanielista.rekrutacyjne.repository;

import eu.wanielista.rekrutacyjne.DTO.CheckIpDTO;
import eu.wanielista.rekrutacyjne.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBRepository extends JpaRepository<ClientModel, Long> {


    ClientModel findClientModelById(long id);

    List<CheckIpDTO> findClientModelByLastLoggedIP(String ip);
}
