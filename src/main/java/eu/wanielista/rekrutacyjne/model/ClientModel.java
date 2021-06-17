package eu.wanielista.rekrutacyjne.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String _firstName;

    @Column(name="last_name")
    private String _lastName;

    @Column(name="loan_value")
    private Double _loanValue;

    @Column(name="loan_end_date")
    private LocalDate _loanEndDate;

    @Column(name="has_loan")
    private Boolean hasLoan;

    @Column(name="has_updated_time")
    private Boolean hasUpdatedTime;

    public ClientModel(String _firstName, String _lastName){
        this._firstName = _firstName;
        this._lastName = _lastName;
    }

    public ClientModel() {

    }
}
