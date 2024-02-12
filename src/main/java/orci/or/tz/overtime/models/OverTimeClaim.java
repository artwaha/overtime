package orci.or.tz.overtime.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.MonthEnum;
import orci.or.tz.overtime.utilities.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "claims")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeClaim extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_id_seq")
    @SequenceGenerator(name = "claim_id_seq", sequenceName = "CLAIM_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "claim_id")
    private Long id;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "claim_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatusEnum claimStatus;


    @Column(name = "claim_month", nullable = false)
    @Enumerated(EnumType.STRING)
    private MonthEnum claimMonth;


    @Column(name = "claim_year")
    private Integer claimYear;


    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference(value = "user-claims")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private ApplicationUser user;






}
