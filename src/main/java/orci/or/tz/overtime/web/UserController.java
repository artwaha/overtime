package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.user.UserRequestDto;
import orci.or.tz.overtime.dto.user.UserResponseDto;
import orci.or.tz.overtime.dto.user.UserUpdateDto;
import orci.or.tz.overtime.enums.UserStatusEnum;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.services.UserService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.GenericResponse;
import orci.or.tz.overtime.web.api.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;


    @Autowired
    private Commons commons;




    @Override
    public ResponseEntity<UserResponseDto> AttendUser(UserRequestDto request) throws OperationFailedException, ResourceNotFoundException {




        Optional<ApplicationUser> user = userService.GetUserByAttendanceId(request.getAttendanceId());

        if(user.isPresent()){
            throw new OperationFailedException("User with provided Attendance ID [" + request.getAttendanceId() + "] already exist.");
        }

        
            ApplicationUser u = new ApplicationUser();
            u.setUserName(request.getUserName());
            u.setFullName(request.getFullName());
            u.setEmail(request.getEmail());
            u.setMobile(request.getMobile());
            u.setPfNumber(request.getPfNumber());
            u.setCheckNumber(request.getCheckNumber());
            u.setUserRole(request.getUserRole());
            u.setReference(request.getReference());
            u.setUserStatus(UserStatusEnum.ACTIVE);
            u.setAttendanceId(request.getAttendanceId());
            userService.SaveUser(u);


            UserResponseDto resp = commons.GenerateUser(u);
            return ResponseEntity.ok(resp);


    }

    @Override
    public ResponseEntity<GenericResponse<List<UserResponseDto>>> GetAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ApplicationUser> users = userService.GetAllUsers(pageRequest);

        List<UserResponseDto> resp = new ArrayList<>();
        for (ApplicationUser u : users) {
            UserResponseDto user = commons.GenerateUser(u);
            resp.add(user);
        }

         GenericResponse<List<UserResponseDto>> response = new GenericResponse<>();
         response.setCurrentPage(page);
         response.setPageSize(size);
         response.setTotalItems(userService.countTotalItems());
         response.setTotalPages(commons.GetTotalNumberOfPages(userService.countTotalItems(),size));
         response.setData(resp);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponseDto> UpdateUser(@Valid UserUpdateDto request, Long id)
            throws ResourceNotFoundException {

        Optional<ApplicationUser> user = userService.GetUserById(id);

        if(!user.isPresent()){
            throw new ResourceNotFoundException("User with provided ID [" + id + "] does not exist.");
        }

        ApplicationUser u = user.get();

        u.setUserName(request.getUserName());
        u.setFullName(request.getFullName());
        u.setEmail(request.getEmail());
        u.setMobile(request.getMobile());
        u.setPfNumber(request.getPfNumber());
        u.setCheckNumber(request.getCheckNumber());
        u.setUserRole(request.getUserRole());
        u.setReference(request.getReference());
        u.setAttendanceId(request.getAttendanceId());
          
            userService.SaveUser(u);

             UserResponseDto resp = commons.GenerateUser(u);
            return ResponseEntity.ok(resp);

    }


    


}
