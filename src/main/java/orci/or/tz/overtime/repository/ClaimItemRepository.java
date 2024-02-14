package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimItem;
import orci.or.tz.overtime.models.OverTimeClaim;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClaimItemRepository extends JpaRepository<ClaimItem,Long> {

    Optional<ClaimItem> findByClaimAndClaimDate(OverTimeClaim c, LocalDate dt);

    List<ClaimItem> findByClaim(OverTimeClaim claim, Pageable pageable);
    Long countByClaim(OverTimeClaim claim);
}
