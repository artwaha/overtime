package orci.or.tz.overtime.dto.claims;

import lombok.Data;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClaimItemResponseDto {
    private Long id;
    private LocalDate claimDate;
    private String claimActivities;
    private ClaimItemStatusEnum itemStatus;
    private ClaimResponseDto claim;
    private LocalDateTime createdDate;
}
