package orci.or.tz.overtime.utilities;

import orci.or.tz.overtime.dto.department.DepartmentResponseDto;
import orci.or.tz.overtime.dto.directorate.DirectorateResponseDto;
import orci.or.tz.overtime.dto.executive.ExecutiveResponseDto;
import orci.or.tz.overtime.dto.section.SectionResponseDto;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.models.Directorate;
import orci.or.tz.overtime.models.Section;
import orci.or.tz.overtime.services.DepartmentService;
import orci.or.tz.overtime.services.DirectorateService;
import orci.or.tz.overtime.services.SectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Commons {

    @Autowired
    private Mapper mapper;

    @Autowired
    private DirectorateService directorateService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SectionService sectionService;


    public DirectorateResponseDto GenerateDirectorate(Directorate s) {
        ModelMapper modelMapper = mapper.getModelMapper();
        DirectorateResponseDto d = modelMapper.map(s, DirectorateResponseDto.class);
        return d;
    }

    public DepartmentResponseDto GenerateDepartment(Department d) {
        ModelMapper modelMapper = mapper.getModelMapper();
        DepartmentResponseDto resp = modelMapper.map(d, DepartmentResponseDto.class);
        resp.setDirectorate(GenerateDirectorate(d.getDirectorate()));
        return resp;
    }

    public SectionResponseDto GenerateSection(Section s) {
        ModelMapper modelMapper = mapper.getModelMapper();
        SectionResponseDto resp = modelMapper.map(s, SectionResponseDto.class);
        resp.setDepartment(GenerateDepartment(s.getDepartment()));
        return resp;
    }
    public Integer GetTotalNumberOfPages(Integer totalCount,Integer pageSize) {
        Integer results;

        if (totalCount % pageSize == 0) {
            results = totalCount / pageSize;
        } else {
            results = totalCount / pageSize + 1;
        }

        return results;
    }

    public UserResponseDto<?> GenerateUser(ApplicationUser u) {
        ModelMapper modelMapper = mapper.getModelMapper();

        switch (u.getUserRole().toString()) {
            case "EXECUTIVE_DIRECTOR":
                UserResponseDto<ExecutiveResponseDto> execDto = modelMapper.map(u, UserResponseDto.class);
                ExecutiveResponseDto ed = new ExecutiveResponseDto();
                execDto.setReference(ed);

                return execDto;
            case "DIRECTOR":
                UserResponseDto<DirectorateResponseDto> dirDto = modelMapper.map(u, UserResponseDto.class);
                Long directorateId = u.getReference();
                Optional<Directorate> dir = directorateService.GetDirectorateById(directorateId);
                dirDto.setReference(GenerateDirectorate(dir.get()));
                return dirDto;
            case "MANAGER":
                UserResponseDto<DepartmentResponseDto> deptDto = modelMapper.map(u, UserResponseDto.class);
                Long departmentId = u.getReference();
                Optional<Department> dep = departmentService.GetDepartmentById(departmentId);
                deptDto.setReference(GenerateDepartment(dep.get()));
                return deptDto;
            case "SUPERVISOR":
                UserResponseDto<SectionResponseDto> secUserDto = modelMapper.map(u, UserResponseDto.class);
                Long section_userId = u.getReference();
                Optional<Section> section = sectionService.GetSectionById(section_userId);
                secUserDto.setReference(GenerateSection(section.get()));
                return secUserDto;
            case "STAFF":
                UserResponseDto<SectionResponseDto> secDto = modelMapper.map(u, UserResponseDto.class);
                Long sectionId = u.getReference();
                Optional<Section> sec = sectionService.GetSectionById(sectionId);
                secDto.setReference(GenerateSection(sec.get()));
                return secDto;
            default:
                return null;
        }
    }

}