package orci.or.tz.overtime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;
import orci.or.tz.overtime.enums.ClaimStatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Table(name = "claim_item")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClaimItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_item_id_seq")
    @SequenceGenerator(name = "claim_item_id_seq", sequenceName = "CLAIM_ITEM_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "claim_item_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "claim_id", referencedColumnName = "claim_id")
    @JsonBackReference(value = "claim-tracking")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private OverTimeClaim claim;

    @Column(name = "item_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimItemStatusEnum itemStatus;

    @Column(name = "claim_date", nullable = true)
    private LocalDate claimDate;

    @Column(name="activities", nullable = false,columnDefinition = "TEXT")
    private String claimActivities;


}
