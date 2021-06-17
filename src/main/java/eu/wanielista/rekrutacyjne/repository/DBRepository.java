package eu.wanielista.rekrutacyjne.repository;

import eu.wanielista.rekrutacyjne.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBRepository extends JpaRepository<ClientModel, Long> {


    ClientModel findClientModelById(long id);
}
