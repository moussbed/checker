package com.mb.checker.directory.model;

import com.mb.checker.shared.common.dto.Period;
import com.mb.checker.shared.common.dto.Tranche;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Audited
public class Faculty extends Auditable<String> {

    @Id
    private String facultyUuid;

    @NotEmpty
    private String label;

    @NotNull
    @Digits(integer=5, fraction=2)
    private BigDecimal inscription;

    @NotNull
    private BigDecimal firstTranche;

    @NotNull
    private BigDecimal secondTranche;

    @NotNull
    private BigDecimal thirdTranche;

    @Enumerated(EnumType.STRING)
    private Tranche tranche = Tranche.FIRST;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Min(1)
    @Max(5)
    private short level;

    public Faculty() {
        this.facultyUuid = UUID.randomUUID().toString();
    }

    public BigDecimal getCurrentFeesToPay(){
       return switch (tranche){
            case INSCRIPTION -> inscription;
            case FIRST -> inscription.add(firstTranche);
            case SECOND -> inscription.add(firstTranche).add(secondTranche);
            case THIRD -> inscription.add(firstTranche).add(secondTranche).add(thirdTranche);
        };
    }

}
