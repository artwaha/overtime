package orci.or.tz.overtime.services;

import orci.or.tz.overtime.models.ClaimItemHistory;
import orci.or.tz.overtime.repository.ClaimItemHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimItemHistoryService {

    @Autowired
    private ClaimItemHistoryRepository claimItemHistoryRepo;


    public ClaimItemHistory SaveCalimItemHistory(ClaimItemHistory c){
        return  claimItemHistoryRepo.save(c);
    }

}
