package orci.or.tz.overtime.dto.claims;

import lombok.Data;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;

@Data
public class ItemActionDto {
    private Long claimItemId;
    private ClaimItemStatusEnum action;
    private String reason;
}
