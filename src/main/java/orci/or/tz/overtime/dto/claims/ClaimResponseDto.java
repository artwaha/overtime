package orci.or.tz.overtime.dto.claims;

import lombok.Data;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.MonthEnum;

@Data
public class ClaimResponseDto {
    private Long id;
    private String referenceNumber;
    private ClaimStatusEnum claimStatus;
    private MonthEnum claimMonth;
    private Integer claimYear;
}
