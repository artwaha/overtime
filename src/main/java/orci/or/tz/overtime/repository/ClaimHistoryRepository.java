package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimHistoryRepository extends JpaRepository<ClaimHistory,Long> {
}
