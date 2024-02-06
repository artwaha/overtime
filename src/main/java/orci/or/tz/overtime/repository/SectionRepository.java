package orci.or.tz.overtime.repository;


import orci.or.tz.overtime.models.Section;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findAllByOrderByIdDesc(Pageable pageRequest);
}
