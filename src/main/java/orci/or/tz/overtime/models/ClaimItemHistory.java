package orci.or.tz.overtime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;
import orci.or.tz.overtime.enums.ClaimStatusEnum;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Table(name="claim_item_tracking")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClaimItemHistory {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_item_tracking_id_seq")
    @SequenceGenerator(name = "claim_item_tracking_id_seq", sequenceName = "CLAIM_ITEM_TRACKING_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "claim_item_tracking_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "claim_item_id", referencedColumnName = "claim_item_id")
    @JsonBackReference(value = "claim-item-tracking")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private ClaimItem claimItem;


    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimItemStatusEnum claimItemStatus;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "reason", nullable = true,columnDefinition = "TEXT")
    private String reason;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference(value = "user-claim-item-tracking")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private ApplicationUser createdBy;
}
