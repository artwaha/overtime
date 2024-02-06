package orci.or.tz.overtime.repository;


import orci.or.tz.overtime.models.Directorate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorateRepository extends JpaRepository<Directorate, Long> {
    List<Directorate> findAllByOrderByIdDesc(Pageable pageRequest);
}
