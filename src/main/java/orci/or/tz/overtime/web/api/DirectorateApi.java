package orci.or.tz.overtime.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import orci.or.tz.overtime.dto.directorate.DirectorateRequestDto;
import orci.or.tz.overtime.dto.directorate.DirectorateResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/directorates")
@Api(value = "Directorate Management", description = "Manage Directorates on the web")
public interface DirectorateApi {

    @ApiOperation(value = "Create Directorate", notes = "Create Directorate")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<DirectorateResponseDto> CreateDirectorate(@Valid @RequestBody DirectorateRequestDto request);

    @ApiOperation(value = "Get Directorate By Id", notes = "Get Directorate By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<DirectorateResponseDto> GetDirectorateById(@PathVariable Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "View All Directorates", notes = "View All Directorates")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<GenericResponse<List<DirectorateResponseDto>>> GetAllDirectorates(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);

    @ApiOperation(value = "Update Directorate", notes = "Update Directorate")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<DirectorateResponseDto> UpdateDirectorate(@Valid @RequestBody DirectorateRequestDto request, @PathVariable Long id) throws ResourceNotFoundException;

    @ApiOperation(value = "Get Total Number of Directorates", notes = "Total Number of Directoartaes")
    @RequestMapping(value = "/directorates/count", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity <Integer>  CountDirectorates();
}
