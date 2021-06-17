package eu.wanielista.rekrutacyjne.controller;

import eu.wanielista.rekrutacyjne.model.ClientModel;
import eu.wanielista.rekrutacyjne.repository.DBRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormattergit remote add origin https://github.com/mwanielista/bank-loan-api.git;

@RestController
public class AppController {

    private DBRepository repository;
    public AppController(DBRepository dbRepository){
        repository = dbRepository;
    }

    @RequestMapping("/addClient/{firstName}/{lastName}")
    public ClientModel addClient(@PathVariable String firstName, @PathVariable String lastName){
        return repository.save(new ClientModel(firstName,lastName));
    }

    //YYYYMMDD
    @RequestMapping("/requestLoan/{id}/{sum}/{expireDate}")
    public ClientModel loanRequest(@PathVariable Long id,@PathVariable int sum, @PathVariable String expireDate){
        boolean result = false;

        LocalDate date = LocalDate.parse(expireDate, DateTimeFormatter.BASIC_ISO_DATE);
        return repository.findClientModelById(id);
    }
}
