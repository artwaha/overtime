package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.ClaimItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClaimItemRepository extends JpaRepository<ClaimItem,Long> {
}
