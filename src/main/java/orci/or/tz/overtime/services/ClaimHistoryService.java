package orci.or.tz.overtime.services;


import orci.or.tz.overtime.models.ClaimHistory;
import orci.or.tz.overtime.repository.ClaimHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimHistoryService {

    @Autowired
    private ClaimHistoryRepository claimHistoryRepo;

    public ClaimHistory SaveClaimHistory(ClaimHistory ch){
        return claimHistoryRepo.save(ch);
    }
}
