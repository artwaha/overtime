package orci.or.tz.overtime.dto.attendance;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDto {
    private String category;
    private LocalDate date;
    private String time;
    private String fullName;
}
