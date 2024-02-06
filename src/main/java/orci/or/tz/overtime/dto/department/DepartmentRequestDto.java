package orci.or.tz.overtime.dto.department;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DepartmentRequestDto {
    private String departmentName;
    private Long directorateId;
}
