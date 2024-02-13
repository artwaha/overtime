package orci.or.tz.overtime.dto.claims;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClaimItemRequestDto {
    private Long claimId;
    private LocalDate claimDate;
    private String claimActivities;
}
