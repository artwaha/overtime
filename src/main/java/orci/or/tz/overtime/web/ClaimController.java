package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.claims.ClaimItemRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimItemResponseDto;
import orci.or.tz.overtime.dto.claims.ClaimRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.*;
import orci.or.tz.overtime.services.*;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.GenericResponse;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.utilities.RandomPasswordGenerator;
import orci.or.tz.overtime.web.api.ClaimApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ClaimController implements ClaimApi {

    @Autowired
    private ClaimService claimService;


    @Autowired
    private RandomPasswordGenerator randomPasswordGenerator;

    @Autowired
    private Commons commons;

    @Autowired
    private LoggedUser loggedUser;

    @Autowired
    private ClaimHistoryService claimHistoryService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ClaimItemService claimItemService;

    @Autowired
    private ClaimItemHistoryService claimItemHistoryService;


    @Override
    public ResponseEntity<ClaimResponseDto> GenerateClaim(ClaimRequestDto request) throws OperationFailedException {


        ApplicationUser usr = loggedUser.getInfo();

        Optional<OverTimeClaim> claim = claimService.FindClaimByUserandMonthAndYear(usr,request.getMonth(),request.getYear());

        if(claim.isPresent()){
            throw new OperationFailedException("Claim for Specific User On this Month Already Exists ");
        }else {

            String randomPassword = randomPasswordGenerator.generateRandomCharacters(10);

            // Declare new Claim Object
            OverTimeClaim c = new OverTimeClaim();
            c.setClaimMonth(request.getMonth());
            c.setClaimYear(request.getYear());
            c.setReferenceNumber(randomPassword);
            c.setUser(loggedUser.getInfo());
            c.setClaimStatus(ClaimStatusEnum.CREATED);

            // SaveClaim Service
            claimService.SaveClaim(c);

            //Claim History
            ClaimHistory ch = new ClaimHistory();
            ch.setClaim(c);
            ch.setClaimStatus(ClaimStatusEnum.CREATED);
            ch.setCreatedDate(LocalDateTime.now());
            ch.setReason("");
            ch.setCreatedBy(usr);
            claimHistoryService.SaveClaimHistory(ch);


            // Generate Claim Response
            ClaimResponseDto resp = commons.GenerateClaim(c);

            //Return  Claim Response
            return ResponseEntity.ok(resp);

        }
    }

    @Override
    public ResponseEntity<ClaimItemResponseDto> CreateClaimItem(ClaimItemRequestDto request) throws OperationFailedException, ResourceNotFoundException {

        Optional<OverTimeClaim> claim = claimService.GetClaimById(request.getClaimId());

        ApplicationUser usr = loggedUser.getInfo();

        if (!claim.isPresent()) {
            throw new ResourceNotFoundException("Claim with the provided ID is not Found");
        } else {

            Optional<ClaimItem> item = claimItemService.CheckClaimByDate(claim.get(), request.getClaimDate());

            if (item.isPresent()) {
                throw new OperationFailedException("Claim for the Specified Date Already Exists");
            } else {

                // Make HTTP Request To Attendance Api to check if there is Records

                Integer checkAttendanceCount = attendanceService.GetAttendanceByUser(request.getClaimDate(), usr.getAttendanceId());

                if (checkAttendanceCount > 0) {

                    ClaimItem c = new ClaimItem();
                    c.setClaim(claim.get());
                    c.setClaimDate(request.getClaimDate());
                    c.setClaimActivities(request.getClaimActivities());
                    c.setItemStatus(ClaimItemStatusEnum.CREATED);
                    c.setCreatedDate(LocalDateTime.now());
                    claimItemService.SaveClaimItem(c);

                    ClaimItemHistory ch = new ClaimItemHistory();
                    ch.setClaimItem(c);
                    ch.setCreatedBy(usr);
                    ch.setCreatedDate(LocalDateTime.now());
                    ch.setClaimItemStatus(ClaimItemStatusEnum.CREATED);
                    claimItemHistoryService.SaveCalimItemHistory(ch);


                    ClaimItemResponseDto resp = commons.GenerateClaimItem(c);
                    return ResponseEntity.ok(resp);


                } else {
                    throw new OperationFailedException("No Attendace Records For The User On the specified Date");
                }

            }


        }
    }

    @Override
    public ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetUserClaims(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        ApplicationUser usr = loggedUser.getInfo();

        List<OverTimeClaim> claims = claimService.GetUserClaims(usr,pageRequest);
        List<ClaimResponseDto> resp = new ArrayList<>();

        for(OverTimeClaim c : claims){
            ClaimResponseDto r = commons.GenerateClaim(c);
            resp.add(r);
        }

        GenericResponse<List<ClaimResponseDto>> response = new GenericResponse<>();
        response.setCurrentPage(page);
        response.setPageSize(size);
        Integer totalCount = claimService.GetClaimCountByUser(usr).intValue();
        response.setTotalItems(totalCount);
        response.setTotalPages(commons.GetTotalNumberOfPages(totalCount, size));
        response.setData(resp);
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<GenericResponse<List<ClaimItemResponseDto>>> GetClaimItems(int page, int size, Long id) throws ResourceNotFoundException {
        Optional<OverTimeClaim> claim = claimService.GetClaimById(id);
        PageRequest pageRequest = PageRequest.of(page, size);

        if (!claim.isPresent()) {
            throw new ResourceNotFoundException("Claim with the provided ID is not Found");
        } else {

            List<ClaimItem> items = claimItemService.GetByClaim(claim.get(),pageRequest);

            List<ClaimItemResponseDto> resp = new ArrayList<>();

            for(ClaimItem c : items){
                ClaimItemResponseDto r = commons.GenerateClaimItemMinor(c);
                resp.add(r);
            }

            GenericResponse<List<ClaimItemResponseDto>> response = new GenericResponse<>();
            response.setCurrentPage(page);
            response.setPageSize(size);
            Integer totalCount = claimItemService.GetByClaimCount(claim.get()).intValue();
            response.setTotalItems(totalCount);
            response.setTotalPages(commons.GetTotalNumberOfPages(totalCount, size));
            response.setData(resp);
            return ResponseEntity.ok(response);

        }

    }


}
