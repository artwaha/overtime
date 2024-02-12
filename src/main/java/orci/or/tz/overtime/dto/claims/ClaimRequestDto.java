package orci.or.tz.overtime.dto.claims;


import lombok.Data;
import orci.or.tz.overtime.enums.MonthEnum;

@Data
public class ClaimRequestDto {
    private MonthEnum month;
    private Integer year;
}
