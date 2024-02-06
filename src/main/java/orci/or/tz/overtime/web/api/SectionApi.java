package orci.or.tz.overtime.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import orci.or.tz.overtime.dto.section.SectionRequestDto;
import orci.or.tz.overtime.dto.section.SectionResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/sections")
@Api(value = "Section Management", description = "Manage Sections on the web")
public interface SectionApi {

    @ApiOperation(value = "Create Section", notes = "Create Section")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<SectionResponseDto> CreateSection(@Valid @RequestBody SectionRequestDto request) throws ResourceNotFoundException;

    @ApiOperation(value = "Get Section By Id", notes = "Get Section By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<SectionResponseDto> GetSectionById(@PathVariable Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "View All Sections", notes = "View All Sections")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<GenericResponse<List<SectionResponseDto>>> GetAllSections(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);

    @ApiOperation(value = "Update Section", notes = "Update Section")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<SectionResponseDto> UpdateSection(@Valid @RequestBody SectionRequestDto request, @PathVariable Long id) throws ResourceNotFoundException;

    @ApiOperation(value = "Get Total Number of Sections", notes = "Total Number of Sections")
    @RequestMapping(value = "/sections/count", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity <Integer>  CountSections();
}
