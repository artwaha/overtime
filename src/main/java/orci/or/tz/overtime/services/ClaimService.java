package orci.or.tz.overtime.services;


import orci.or.tz.overtime.enums.MonthEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepo;

    public OverTimeClaim SaveClaim(OverTimeClaim c) {
        return claimRepo.save(c);
    }

    public Optional<OverTimeClaim> FindClaimByUserandMonthAndYear(ApplicationUser user, MonthEnum month, Integer year){
        return claimRepo.findByUserAndClaimMonthAndClaimYear(user,month,year);
    }
}
