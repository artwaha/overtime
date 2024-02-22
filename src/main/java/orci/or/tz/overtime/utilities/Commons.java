package orci.or.tz.overtime.utilities;

import orci.or.tz.overtime.dto.claims.ClaimItemResponseDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.dto.claims.ItemTrackingResponseDto;
import orci.or.tz.overtime.dto.claims.TrackingResponseDto;
import orci.or.tz.overtime.dto.department.DepartmentResponseDto;
import orci.or.tz.overtime.dto.executive.ExecutiveResponseDto;
import orci.or.tz.overtime.dto.section.SectionResponseDto;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;
import orci.or.tz.overtime.models.*;
import orci.or.tz.overtime.services.DepartmentService;
import orci.or.tz.overtime.services.SectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Commons {

    @Autowired
    private Mapper mapper;


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SectionService sectionService;



    public ClaimItemResponseDto GenerateClaimItemMinor(ClaimItem c){
        ModelMapper modelMapper = mapper.getModelMapper();
        ClaimItemResponseDto claim = modelMapper.map(c,ClaimItemResponseDto.class);
        return claim;

    }

    public ClaimItemResponseDto GenerateClaimItem(ClaimItem c){
        ModelMapper modelMapper = mapper.getModelMapper();
        ClaimItemResponseDto claim = modelMapper.map(c,ClaimItemResponseDto.class);
        claim.setClaim(GenerateClaim(c.getClaim()));
        return claim;

    }
    public ClaimResponseDto GenerateClaim(OverTimeClaim c){
        ModelMapper modelMapper = mapper.getModelMapper();
        ClaimResponseDto claim = modelMapper.map(c,ClaimResponseDto.class);
        claim.setUser(GenerateUser(c.getUser()));
        return claim;

    }


    public DepartmentResponseDto GenerateDepartment(Department d) {
        ModelMapper modelMapper = mapper.getModelMapper();
        DepartmentResponseDto resp = modelMapper.map(d, DepartmentResponseDto.class);
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
            case "HOD":
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


    public ItemTrackingResponseDto GenerateItemHistory(ClaimItemHistory c){
        ItemTrackingResponseDto resp = new ItemTrackingResponseDto();
        resp.setId(c.getId());
        resp.setAction(c.getClaimItemStatus());
        resp.setCreatedDate(c.getCreatedDate());
        resp.setReason(c.getReason());
        resp.setUser(GenerateUser(c.getCreatedBy()));
        return resp;
    }


    public TrackingResponseDto GenerateHistory(ClaimHistory c){
        TrackingResponseDto resp = new TrackingResponseDto();
        resp.setId(c.getId());
        resp.setAction(c.getClaimStatus());
        resp.setCreatedDate(c.getCreatedDate());
        resp.setReason(c.getReason());
        resp.setUser(GenerateUser(c.getCreatedBy()));
        return resp;
    }

    public List<Long> GenerateReferenceList(Long reference){

        List<Long> resp = new ArrayList<>();
        Optional<Department> department = departmentService.GetDepartmentById(reference);

        if(department.isPresent()){

            List<Section> sections = sectionService.GetByDepartment(department.get());
            for(Section s : sections){
                resp.add(s.getId());
            }
        }

        return resp;

    }


    public Boolean CheckItemsValidity(List<ClaimItem> items){
        Boolean check = true;

        for(ClaimItem i :items){
            if(!i.getItemStatus().equals(ClaimItemStatusEnum.CREATED)){
               check=false;
            }
        }

        return  check;

    }
}
