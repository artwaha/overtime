package orci.or.tz.overtime.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import orci.or.tz.overtime.dto.user.UserRequestDto;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.dto.user.UserUpdateDto;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@Api(value = "User  Management", description = "Manage Users on the web")
public interface UserApi {

    @ApiOperation(value = "Attend User", notes = "Attend User")
    @RequestMapping(value = "/external/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<UserResponseDto> AttendUser(@RequestBody UserRequestDto request) throws OperationFailedException, ResourceNotFoundException;

    @ApiOperation(value = "View All Users", notes = "View All Users")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<GenericResponse<List<UserResponseDto>>> GetAllUsers(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);

    @ApiOperation(value = " update a spefic user", notes = " update user")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<UserResponseDto> UpdateUser(@Valid @RequestBody UserUpdateDto request, @PathVariable Long id) throws ResourceNotFoundException;

}
