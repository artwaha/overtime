package orci.or.tz.overtime.dto.claims;


import lombok.Data;
import orci.or.tz.overtime.enums.ClaimStatusEnum;

@Data
public class ActionDto {
    private Long claimId;
    private ClaimStatusEnum action;
    private String reason;
}
