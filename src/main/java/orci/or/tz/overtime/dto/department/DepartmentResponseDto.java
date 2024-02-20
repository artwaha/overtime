package orci.or.tz.overtime.dto.department;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DepartmentResponseDto {
    private Long id;
    private String departmentName;

}
