package orci.or.tz.overtime.services;


import orci.or.tz.overtime.enums.ClaimStatusEnum;
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

    public Optional<OverTimeClaim> FindClaimByUserandMonthAndYear(ApplicationUser user, MonthEnum month, Integer year) {
        return claimRepo.findByUserAndClaimMonthAndClaimYear(user, month, year);
    }


    public List<OverTimeClaim> GetUserClaims(ApplicationUser usr, Pageable pageable) {
        return claimRepo.findByUserOrderByIdDesc(usr, pageable);
    }

    public Long GetClaimCountByUser(ApplicationUser usr) {
        return claimRepo.countByUser(usr);
    }

    public Optional<OverTimeClaim> GetClaimById(Long id) {
        return claimRepo.findById(id);
    }


    public List<OverTimeClaim> GetClaimByReference(Long reference, Pageable pageable) {
        return claimRepo.findByUserReferenceOrderByIdDesc(reference, pageable);
    }

    public Long CountClaimByReference(Long reference) {
        return claimRepo.countByUserReference(reference);
    }


    public List<OverTimeClaim> GetClaimByReferenceList(List<Long> reference, Pageable pageable) {
        return claimRepo.findByUserReferenceInOrderByIdDesc(reference, pageable);
    }

    public Long CountClaimByReferenceList(List<Long> reference) {
        return claimRepo.countByUserReferenceIn(reference);
    }


    public List<OverTimeClaim> GetClaimByReferenceAndStatus(Long reference, ClaimStatusEnum claimStatus, Pageable pageable) {
        return claimRepo.findByUserReferenceAndClaimStatusOrderByIdDesc(reference, claimStatus, pageable);
    }

    public Long CountClaimByReferenceAndStatus(Long reference, ClaimStatusEnum claimStatus) {
        return claimRepo.countByUserReferenceAndClaimStatus(reference, claimStatus);
    }


    public List<OverTimeClaim> GetClaimByReferenceAndStatusList(List<Long> reference, ClaimStatusEnum claimStatus, Pageable pageable) {
        return claimRepo.findByUserReferenceInAndClaimStatusOrderByIdDesc(reference, claimStatus, pageable);
    }

    public Long CountClaimByReferenceAndStatusList(List<Long> reference, ClaimStatusEnum claimStatus) {
        return claimRepo.countByUserReferenceInAndClaimStatus(reference, claimStatus);
    }


    public List<OverTimeClaim> GetClaimByReferenceAndMonthAndYear(Long reference,MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByUserReferenceAndClaimMonthAndClaimYearOrderByIdDesc(reference,claimMonth,claimYear, pageable);
    }

    public Long CountClaimByReferenceAndMonthAndYear(Long reference,MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByUserReferenceAndClaimMonthAndClaimYear(reference,claimMonth,claimYear);
    }


    public List<OverTimeClaim> GetClaimByReferenceAndMonthAndYearList(List<Long> reference,MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByUserReferenceInAndClaimMonthAndClaimYearOrderByIdDesc(reference,claimMonth,claimYear, pageable);
    }

    public Long CountClaimByReferenceAndMonthAndYearList(List<Long> reference,MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByUserReferenceInAndClaimMonthAndClaimYear(reference,claimMonth,claimYear);
    }

    public List<OverTimeClaim> GetClaimByReferenceAndMonthAndYearAndStatus(Long reference,ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByUserReferenceAndClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc(reference,claimStatus,claimMonth,claimYear, pageable);
    }

    public Long CountClaimByReferenceAndMonthAndYearAndStatus(Long reference,ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByUserReferenceAndClaimStatusAndClaimMonthAndClaimYear(reference,claimStatus,claimMonth,claimYear);
    }


    public List<OverTimeClaim> GetClaimByReferenceAndMonthAndYearAndStatusList(List<Long> reference,ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByUserReferenceInAndClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc(reference,claimStatus,claimMonth,claimYear, pageable);
    }

    public Long CountClaimByReferenceAndMonthAndYearAndStatusList(List<Long> reference,ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByUserReferenceInAndClaimStatusAndClaimMonthAndClaimYear(reference,claimStatus,claimMonth,claimYear);
    }




    public List<OverTimeClaim> GetClaimByStatus( ClaimStatusEnum claimStatus, Pageable pageable) {
        return claimRepo.findByClaimStatusOrderByIdDesc(claimStatus, pageable);
    }

    public Long CountClaimByStatus( ClaimStatusEnum claimStatus) {
        return claimRepo.countByClaimStatus( claimStatus);
    }

    public List<OverTimeClaim> GetClaimByMonthAndYear(MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByClaimMonthAndClaimYearOrderByIdDesc(claimMonth,claimYear, pageable);
    }

    public Long CountClaimByMonthAndYear(MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByClaimMonthAndClaimYear(claimMonth,claimYear);
    }



    public List<OverTimeClaim> GetClaimByMonthAndYearAndStatus(ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear, Pageable pageable) {
        return claimRepo.findByClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc(claimStatus,claimMonth,claimYear, pageable);
    }

    public Long CountClaimByMonthAndYearAndStatus(ClaimStatusEnum claimStatus,MonthEnum claimMonth, Integer claimYear) {
        return claimRepo.countByClaimStatusAndClaimMonthAndClaimYear(claimStatus,claimMonth,claimYear);
    }


    public List<OverTimeClaim> GetAllClaims(Pageable pageable) {
        return claimRepo.findAllByOrderByIdDesc( pageable);
    }

    public Long CountAllClaims(){
        return claimRepo.count();
    }




}
