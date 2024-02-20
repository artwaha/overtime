package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimItem;
import orci.or.tz.overtime.models.ClaimItemHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClaimItemHistoryRepository extends JpaRepository<ClaimItemHistory, Long> {

    List<ClaimItemHistory> findByClaimItem(ClaimItem item);
}
