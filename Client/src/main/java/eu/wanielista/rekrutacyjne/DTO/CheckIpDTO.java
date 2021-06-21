package eu.wanielista.rekrutacyjne.DTO;

import org.springframework.beans.factory.annotation.Value;

public interface CheckIpDTO {

    @Value("#{target.last_ip}")
    String getIp();
}
