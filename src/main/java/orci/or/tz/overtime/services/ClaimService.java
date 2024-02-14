package orci.or.tz.overtime.services;


import orci.or.tz.overtime.enums.MonthEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public List<OverTimeClaim> GetUserClaims(ApplicationUser usr, Pageable pageable){
        return claimRepo.findByUser(usr,pageable);
    }

    public Long GetClaimCountByUser(ApplicationUser usr){
        return claimRepo.countByUser(usr);
    }

    public Optional<OverTimeClaim> GetClaimById(Long id){
        return claimRepo.findById(id);
    }
}
