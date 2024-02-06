package orci.or.tz.overtime.services;


import orci.or.tz.overtime.models.Directorate;
import orci.or.tz.overtime.repository.DirectorateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorateService {

    @Autowired
    private DirectorateRepository directorateRepo;

    public Directorate SaveDirectorate(Directorate d) {
        return directorateRepo.save(d);
    }

    public Optional<Directorate> GetDirectorateById(Long id) {
        return directorateRepo.findById(id);
    }


    public List<Directorate> GetAllDirectorates(Pageable pageable) {
        return directorateRepo.findAllByOrderByIdDesc(pageable);
    }




    public int countTotalItems() {
        return (int) directorateRepo.count();
    }
    
  
}