package orci.or.tz.overtime.dto.claims;

import lombok.Data;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;

import java.time.LocalDateTime;

@Data
public class ItemTrackingResponseDto {

    private Long id;
    private ClaimItemStatusEnum action;
    private LocalDateTime createdDate;
    private String reason;
    private UserResponseDto user;
}
