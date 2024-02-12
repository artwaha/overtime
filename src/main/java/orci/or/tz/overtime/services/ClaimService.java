package orci.or.tz.overtime.services;


import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepo;

    public OverTimeClaim SaveClaim(OverTimeClaim c) {
        return claimRepo.save(c);
    }
}
