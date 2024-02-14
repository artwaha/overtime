package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.MonthEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.OverTimeClaim;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<OverTimeClaim,Long> {

    Optional<OverTimeClaim> findByUserAndClaimMonthAndClaimYear(ApplicationUser user, MonthEnum month,Integer year);

    List<OverTimeClaim> findByUser(ApplicationUser user, Pageable pageable);
    Long countByUser(ApplicationUser user);
}
