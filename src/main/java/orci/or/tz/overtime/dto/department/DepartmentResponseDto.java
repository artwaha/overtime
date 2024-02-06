package orci.or.tz.overtime.dto.department;

import lombok.Data;

import orci.or.tz.overtime.dto.directorate.DirectorateResponseDto;
import org.springframework.stereotype.Component;

@Component
@Data
public class DepartmentResponseDto {
    private Long id;
    private String departmentName;
    private DirectorateResponseDto directorate;
}
