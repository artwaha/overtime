package orci.or.tz.overtime.services;


import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;

    public Department SaveDepartment(Department b) {
        return departmentRepo.save(b);
    }

    public Optional<Department> GetDepartmentById(Long id) {
        return departmentRepo.findById(id);
    }


    public List<Department> GetAllDepartments(Pageable pageable) {
        return departmentRepo.findAllByOrderByIdDesc(pageable);
    }


    public int countTotalItems() {
        return (int) departmentRepo.count();

    }

}
