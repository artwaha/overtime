package orci.or.tz.overtime.dto.claims;

import lombok.Data;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.enums.ClaimStatusEnum;

import java.time.LocalDateTime;

@Data
public class TrackingResponseDto {
    private Long id;
    private ClaimStatusEnum action;
    private LocalDateTime createdDate;
    private String reason;
    private UserResponseDto user;
}
