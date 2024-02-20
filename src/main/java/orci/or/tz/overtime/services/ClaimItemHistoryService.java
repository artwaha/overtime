package orci.or.tz.overtime.services;

import orci.or.tz.overtime.models.ClaimItem;
import orci.or.tz.overtime.models.ClaimItemHistory;
import orci.or.tz.overtime.repository.ClaimItemHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimItemHistoryService {

    @Autowired
    private ClaimItemHistoryRepository claimItemHistoryRepo;


    public ClaimItemHistory SaveCalimItemHistory(ClaimItemHistory c) {
        return claimItemHistoryRepo.save(c);
    }

    public List<ClaimItemHistory> GetByClaimItems(ClaimItem item) {
        return claimItemHistoryRepo.findByClaimItem(item);
    }

}
