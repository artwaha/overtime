package orci.or.tz.overtime.repository;


import orci.or.tz.overtime.models.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrderByIdDesc(Pageable pageRequest);
}
