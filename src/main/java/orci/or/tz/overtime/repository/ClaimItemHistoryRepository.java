package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClaimItemHistoryRepository extends JpaRepository<ClaimItemHistory, Long> {
}
