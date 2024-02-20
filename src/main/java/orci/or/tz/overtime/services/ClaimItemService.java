package orci.or.tz.overtime.services;

import orci.or.tz.overtime.models.ClaimItem;
import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.repository.ClaimItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimItemService {

    @Autowired
    private ClaimItemRepository claimItemRepo;

    public ClaimItem SaveClaimItem(ClaimItem c){
        return  claimItemRepo.save(c);
    }

    public Optional<ClaimItem>  CheckClaimByDate(OverTimeClaim claim, LocalDate dt){
        return claimItemRepo.findByClaimAndClaimDate(claim,dt);
    }

    public List<ClaimItem> GetByClaim(OverTimeClaim claim, Pageable pageable){
        return  claimItemRepo.findByClaim(claim,pageable);
    }

    public Long GetByClaimCount(OverTimeClaim claim){
        return claimItemRepo.countByClaim(claim);
    }

    public Optional<ClaimItem> GetItemById(Long id){
        return claimItemRepo.findById(id);
    }
}
