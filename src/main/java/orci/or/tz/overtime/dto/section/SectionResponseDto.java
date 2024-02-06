package orci.or.tz.overtime.dto.section;

import lombok.Data;
import orci.or.tz.overtime.dto.department.DepartmentResponseDto;


@Data
public class SectionResponseDto {
    private Long id;
    private String sectionName;
    private DepartmentResponseDto department;
}
