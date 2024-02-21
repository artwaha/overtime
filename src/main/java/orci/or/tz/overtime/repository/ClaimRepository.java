package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.MonthEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.OverTimeClaim;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<OverTimeClaim, Long> {

    List<OverTimeClaim> findAllByOrderByIdDesc(Pageable pageRequest);
    Optional<OverTimeClaim> findByUserAndClaimMonthAndClaimYear(ApplicationUser user, MonthEnum month, Integer year);

    List<OverTimeClaim> findByUserOrderByIdDesc(ApplicationUser user, Pageable pageable);

    Long countByUser(ApplicationUser user);

    List<OverTimeClaim> findByUserReferenceOrderByIdDesc(Long reference, Pageable pageable);

    List<OverTimeClaim> findByUserReferenceInOrderByIdDesc(List<Long> reference, Pageable pageable);

    Long countByUserReference(Long reference);

    Long countByUserReferenceIn(List<Long> reference);

    List<OverTimeClaim> findByUserReferenceAndClaimStatusOrderByIdDesc(Long reference, ClaimStatusEnum claimStatus, Pageable pageable);

    List<OverTimeClaim> findByUserReferenceInAndClaimStatusOrderByIdDesc(List<Long> reference, ClaimStatusEnum claimStatus, Pageable pageable);

    Long countByUserReferenceAndClaimStatus(Long reference, ClaimStatusEnum claimStatus);

    Long countByUserReferenceInAndClaimStatus(List<Long> reference, ClaimStatusEnum claimStatus);

    List<OverTimeClaim> findByUserReferenceAndClaimMonthAndClaimYearOrderByIdDesc(Long reference, MonthEnum claimMonth, Integer claimYear, Pageable pageable);

    List<OverTimeClaim> findByUserReferenceInAndClaimMonthAndClaimYearOrderByIdDesc(List<Long> reference, MonthEnum claimMonth, Integer claimYear, Pageable pageable);

    Long countByUserReferenceAndClaimMonthAndClaimYear(Long reference, MonthEnum claimMonth, Integer claimYear);

    Long countByUserReferenceInAndClaimMonthAndClaimYear(List<Long> reference, MonthEnum claimMonth, Integer claimYear);

    List<OverTimeClaim> findByUserReferenceAndClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc(Long reference, ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear, Pageable pageable);

    List<OverTimeClaim> findByUserReferenceInAndClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc(List<Long> reference, ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear, Pageable pageable);

    Long countByUserReferenceAndClaimStatusAndClaimMonthAndClaimYear(Long reference, ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear);

    Long countByUserReferenceInAndClaimStatusAndClaimMonthAndClaimYear(List<Long> reference, ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear);



    List<OverTimeClaim> findByClaimStatusOrderByIdDesc( ClaimStatusEnum claimStatus, Pageable pageable);

    Long countByClaimStatus(ClaimStatusEnum claimStatus);

    List<OverTimeClaim> findByClaimMonthAndClaimYearOrderByIdDesc( MonthEnum claimMonth, Integer claimYear, Pageable pageable);
    Long countByClaimMonthAndClaimYear( MonthEnum claimMonth, Integer claimYear);

    List<OverTimeClaim> findByClaimStatusAndClaimMonthAndClaimYearOrderByIdDesc( ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear, Pageable pageable);

    Long countByClaimStatusAndClaimMonthAndClaimYear( ClaimStatusEnum claimStatus, MonthEnum claimMonth, Integer claimYear);




}
