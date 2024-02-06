package orci.or.tz.overtime.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import orci.or.tz.overtime.dto.department.DepartmentRequestDto;
import orci.or.tz.overtime.dto.department.DepartmentResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/departments")
@Api(value = "Department Management", description = "Manage Departments on the web")
public interface DepartmentApi {
    @ApiOperation(value = "Create Department", notes = "Create Department")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<DepartmentResponseDto> CreateDepartment(@Valid @RequestBody DepartmentRequestDto request) throws ResourceNotFoundException;

    @ApiOperation(value = "Get Department By Id", notes = "Get Department By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<DepartmentResponseDto> GetDepartmentById(@PathVariable Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "View All Departments", notes = "View All Departments")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<GenericResponse<List<DepartmentResponseDto>>> GetAllDepartments(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);

    @ApiOperation(value = "Update Department", notes = "Update Department")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<DepartmentResponseDto> UpdateDepartment(@Valid @RequestBody DepartmentRequestDto request, @PathVariable Long id) throws ResourceNotFoundException;

    @ApiOperation(value = "Get Total Number of Departments", notes = "Total Number of Departments")
    @RequestMapping(value = "/departments/count", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity <Integer>  CountDepartments();
}
