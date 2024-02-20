package orci.or.tz.overtime.web;

import orci.or.tz.overtime.dto.department.DepartmentRequestDto;
import orci.or.tz.overtime.dto.department.DepartmentResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.services.DepartmentService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.GenericResponse;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.web.api.DepartmentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController implements DepartmentApi {

    @Autowired
    private DepartmentService departmentService;


    @Autowired
    private Commons commons;

    @Autowired
    private LoggedUser loggedUser;



    @Override
    public ResponseEntity<DepartmentResponseDto> CreateDepartment(DepartmentRequestDto request) throws ResourceNotFoundException {


        Department d = new Department();
        d.setDepartmentName(request.getDepartmentName());
        d.setCreatedDate(LocalDateTime.now());
        d.setCreatedBy(loggedUser.getInfo().getId());
        departmentService.SaveDepartment(d);

        DepartmentResponseDto resp = commons.GenerateDepartment(d);
        return ResponseEntity.ok(resp);

    }

    @Override
    public ResponseEntity<DepartmentResponseDto> GetDepartmentById(Long id) throws ResourceNotFoundException {
        Optional<Department> dep = departmentService.GetDepartmentById(id);

        if (!dep.isPresent()) {
            throw new ResourceNotFoundException("Department with provided ID [" + id + "] does not exist.");
        }

        DepartmentResponseDto resp = commons.GenerateDepartment(dep.get());
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<GenericResponse<List<DepartmentResponseDto>>> GetAllDepartments(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Department> departments = departmentService.GetAllDepartments(pageRequest);

        List<DepartmentResponseDto> resp = new ArrayList<>();
        for (Department d : departments) {
            DepartmentResponseDto dep = commons.GenerateDepartment(d);
            resp.add(dep);
        }

        // setting the DepartmentPaginationResponseDto fields
        GenericResponse<List<DepartmentResponseDto>> response = new GenericResponse<>();
        response.setCurrentPage(page);
        response.setPageSize(size);
        Integer totalCount = departmentService.countTotalItems();
        response.setTotalItems(totalCount);
        response.setTotalPages(commons.GetTotalNumberOfPages(totalCount,size));
        response.setData(resp);
        
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DepartmentResponseDto> UpdateDepartment(DepartmentRequestDto request, Long id) throws ResourceNotFoundException {
        Optional<Department> dep = departmentService.GetDepartmentById(id);

        if (!dep.isPresent()) {
            throw new ResourceNotFoundException("Department with provided ID [" + id + "] does not exist.");
        }



        Department d = dep.get();
        d.setDepartmentName(request.getDepartmentName());
        d.setLastModifiedDate(LocalDateTime.now());
        d.setLastModifiedBy(loggedUser.getInfo().getId());
        departmentService.SaveDepartment(d);

        DepartmentResponseDto resp = commons.GenerateDepartment(d);
        return ResponseEntity.ok(resp);

    }

    @Override
    public ResponseEntity <Integer>  CountDepartments() {
        int total = departmentService.countTotalItems();
        return ResponseEntity.ok(total);
    }
}
