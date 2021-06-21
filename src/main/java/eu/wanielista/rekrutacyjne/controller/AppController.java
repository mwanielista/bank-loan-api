package eu.wanielista.rekrutacyjne.controller;

import eu.wanielista.rekrutacyjne.DTO.CheckIpDTO;
import eu.wanielista.rekrutacyjne.model.ClientModel;
import eu.wanielista.rekrutacyjne.repository.DBRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
public class AppController {

    private DBRepository repository;
    public AppController(DBRepository dbRepository){
        repository = dbRepository;
    }

    @RequestMapping("/addClient/{firstName}/{lastName}")
    public ClientModel addClient(@PathVariable String firstName, @PathVariable String lastName,HttpServletRequest request){
        return repository.save(new ClientModel(firstName,lastName, request.getRemoteAddr()));
    }

    //YYYYMMDD
    @RequestMapping("/requestLoan/{clientId}/{sum}/{expireDateStr}")
    public ClientModel loanRequest(@PathVariable Long clientId,@PathVariable Double sum, @PathVariable String expireDateStr, HttpServletRequest request) {
        ClientModel client;

        client = repository.findClientModelById(clientId);
        if(client == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No client founded");
        }
        if(client.getHasLoan()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Already have loan");
        }

        client.setLastLoggedIP(request.getRemoteAddr());

        client = checkLoanRequestConditions(client, request);

        client.setLoanValue(sum);
        LocalDate expireDate = LocalDate.parse(expireDateStr, DateTimeFormatter.BASIC_ISO_DATE);

        client.setLoanEndDate(expireDate);
        repository.save(client);
        return client;
    }

    @RequestMapping("/requestMovePaymentDate/{clientId}")
    public ClientModel movePaymentDate(@PathVariable Long clientId, HttpServletRequest request){
        ClientModel client = repository.findClientModelById(clientId);
        if(client == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No client founded");
        if(client.getHasUpdatedTime()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already updated time");
        if(!client.getIsAccepted()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan not accepted");

        client.setLastLoggedIP(request.getRemoteAddr());
        client.setLoanEndDate(client.getLoanEndDate().plusDays(14));
        client.setHasUpdatedTime(true);
        return repository.save(client);
    }


    ClientModel checkLoanRequestConditions(ClientModel client, HttpServletRequest request){
        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");

        List<CheckIpDTO> ipUsage = repository.findClientModelByLastLoggedIP(request.getRemoteAddr());
        if(ipUsage.size() > 2){
            client.setIsAccepted(false);
        } else if(client.getHasLoan()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Already have loan");
        } else if(Integer.parseInt(dtf.format(time)) >=0 && Integer.parseInt(dtf.format(time)) <6){
            client.setIsAccepted(false);
        } else {
            client.setIsAccepted(true);
            client.setHasLoan(true);
        }
        return client;
    }
}