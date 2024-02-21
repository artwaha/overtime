package orci.or.tz.overtime.services;

import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.models.Section;
import orci.or.tz.overtime.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepo;

    public Section SaveSection(Section s) {
        return sectionRepo.save(s);
    }

    public Optional<Section> GetSectionById(Long id) {
        return sectionRepo.findById(id);
    }


    public List<Section> GetAllSections(Pageable pageable) {
        return sectionRepo.findAllByOrderByIdDesc(pageable);
    }


    public int countTotalItems() {
        return (int) sectionRepo.count();
    }

    public List<Section> GetByDepartment(Department department) {
        return sectionRepo.findByDepartment(department);
    }
}
