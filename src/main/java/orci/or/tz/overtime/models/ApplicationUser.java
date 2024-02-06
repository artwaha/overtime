package orci.or.tz.overtime.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.enums.UserRoleEnum;
import orci.or.tz.overtime.enums.UserStatusEnum;
import orci.or.tz.overtime.utilities.Auditable;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name="users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "USER_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile", nullable = false)
    private String mobile;


    @Column(name = "check_number", nullable = false)
    private String checkNumber;

    @Column(name = "pf_number", nullable = false)
    private String pfNumber;


    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(name = "attendance_id", nullable = true)
    private Long attendanceId;


    @Column(name = "reference", nullable = false)
    private Long reference;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus = UserStatusEnum.ACTIVE;


    public ApplicationUser(Long id) {
        super();
        this.id = id;
    }

    public ApplicationUser(String userName, String password) {
    }

}
