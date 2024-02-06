package orci.or.tz.overtime.dto.user;

import lombok.Data;
import orci.or.tz.overtime.enums.UserRoleEnum;


@Data
public class UserResponseDto<T> {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String mobile;
    private String checkNumber;
    private String pfNumber;
    // private String title;
    private UserRoleEnum userRole;
    private T reference;

}



