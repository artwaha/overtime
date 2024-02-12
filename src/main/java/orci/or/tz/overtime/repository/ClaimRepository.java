package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.OverTimeClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<OverTimeClaim,Long> {
}
