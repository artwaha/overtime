package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.section.SectionRequestDto;
import orci.or.tz.overtime.dto.section.SectionResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.models.Section;
import orci.or.tz.overtime.services.DepartmentService;
import orci.or.tz.overtime.services.SectionService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.GenericResponse;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.web.api.SectionApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SectionController implements SectionApi {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private Commons commons;


    @Autowired
    private LoggedUser loggedUser;

    @Override
    public ResponseEntity<SectionResponseDto> CreateSection(SectionRequestDto request) throws ResourceNotFoundException {
        Optional<Department> dep = departmentService.GetDepartmentById(request.getDepartmentId());

        if (!dep.isPresent()) {
            throw new ResourceNotFoundException("Department with provided ID [" + request.getDepartmentId() + "] does not exist.");
        }

        Section s = new Section();
        s.setSectionName(request.getSectionName());
        s.setCreatedDate(LocalDateTime.now());
        s.setCreatedBy(loggedUser.getInfo().getId());
        s.setDepartment(dep.get());
        sectionService.SaveSection(s);

        SectionResponseDto resp = commons.GenerateSection(s);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<SectionResponseDto> GetSectionById(Long id) throws ResourceNotFoundException {
        Optional<Section> s = sectionService.GetSectionById(id);

        if (!s.isPresent()) {
            throw new ResourceNotFoundException("Section with provided ID [" + id + "] does not exist.");
        }

        SectionResponseDto resp = commons.GenerateSection(s.get());
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<GenericResponse<List<SectionResponseDto>>> GetAllSections(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Section> sections = sectionService.GetAllSections(pageRequest);

        List<SectionResponseDto> resp = new ArrayList<>();
        for (Section s : sections) {
            SectionResponseDto sec = commons.GenerateSection(s);
            resp.add(sec);
        }

         // setting the DirectoratePaginationResponseDto fields
        GenericResponse<List<SectionResponseDto>> response = new GenericResponse<>();
         response.setCurrentPage(page);
         response.setPageSize(size);
         response.setTotalItems(sectionService.countTotalItems());
         response.setTotalPages(commons.GetTotalNumberOfPages(sectionService.countTotalItems(),size));
         response.setData(resp);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SectionResponseDto> UpdateSection(SectionRequestDto request, Long id) throws ResourceNotFoundException {
        Optional<Section> sec = sectionService.GetSectionById(id);

        if (!sec.isPresent()) {
            throw new ResourceNotFoundException("Section with provided ID [" + id + "] does not exist.");
        }

        Optional<Department> dep = departmentService.GetDepartmentById(request.getDepartmentId());

        if (!dep.isPresent()) {
            throw new ResourceNotFoundException("Department with provided ID [" + request.getDepartmentId() + "] does not exist.");
        }


        Section s = sec.get() ;
        s.setSectionName(request.getSectionName());
        s.setLastModifiedDate(LocalDateTime.now());
        s.setLastModifiedBy(loggedUser.getInfo().getId());
        s.setDepartment(dep.get());
        sectionService.SaveSection(s);

        SectionResponseDto resp = commons.GenerateSection(s);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity <Integer>  CountSections() {
        int total = sectionService.countTotalItems();
        return ResponseEntity.ok(total);
    }
    
}
