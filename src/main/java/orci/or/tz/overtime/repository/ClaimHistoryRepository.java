package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimHistory;
import orci.or.tz.overtime.models.OverTimeClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimHistoryRepository extends JpaRepository<ClaimHistory,Long> {

    List<ClaimHistory> findByClaim(OverTimeClaim claim);
}
