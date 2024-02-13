package orci.or.tz.overtime.dto.claims;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClaimItemResponseDto {
    private Long claimItemId;
    private LocalDate claimDate;
    private String claimActivities;
    private ClaimResponseDto claim;
}
