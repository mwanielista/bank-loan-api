package eu.wanielista.rekrutacyjne.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="loan_value")
    private Double loanValue;

    @Column(name="loan_end_date")
    private LocalDate loanEndDate;

    @Column(name="has_loan")
    private Boolean hasLoan;

    @Column(name="has_updated_time")
    private Boolean hasUpdatedTime;

    @Column(name="is_accepted")
    private Boolean isAccepted;

    @Column(name="last_ip")
    private String lastLoggedIP;

    public ClientModel(String firstName, String lastName, String lassLoggedIP){
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastLoggedIP = lassLoggedIP;
        setHasLoan(false);
        setHasUpdatedTime(false);
        setIsAccepted(false);
    }

    public ClientModel() {}
}
