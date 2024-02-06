package orci.or.tz.overtime.repository;


import orci.or.tz.overtime.enums.UserRoleEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    List<ApplicationUser> findAllByOrderByIdDesc(Pageable pageRequest);

    Optional<ApplicationUser> findByUserName(String userName);
    Optional<ApplicationUser> findByAttendanceId(Long attendanceId);

    Optional<ApplicationUser> findByEmail(String email);

     List<ApplicationUser> findByReferenceAndUserRole(Long reference, UserRoleEnum userRole);
}
