package orci.or.tz.overtime.utilities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import orci.or.tz.overtime.enums.UserRoleEnum;
import orci.or.tz.overtime.enums.UserStatusEnum;
import orci.or.tz.overtime.models.ApplicationUser;


@Getter
@Setter
@ToString
public class AppUserDetails extends ApplicationUser {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String fullName;
    private String mobile;
    private UserRoleEnum userRole;
    private String checkNumber;
    private String pfNumber;
    private Long reference;
    private UserStatusEnum userStatus;




    public AppUserDetails(String username, String password, Long userId, String fullName, UserRoleEnum userRole, UserStatusEnum userStatus, String mobile, String checkNumber, String pfNumber, Long reference) {
        super(username, password);
        this.username = username;
        this.id = userId;
        this.fullName = fullName;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.mobile= mobile;
        this.checkNumber=checkNumber;
        this.pfNumber=pfNumber;
        this.reference=reference;

    }

    public AppUserDetails() {
        super("", "");
    }
}
