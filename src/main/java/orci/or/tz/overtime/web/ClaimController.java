package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.claims.*;
import orci.or.tz.overtime.enums.ClaimItemStatusEnum;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.UserRoleEnum;
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

        Optional<OverTimeClaim> claim = claimService.FindClaimByUserandMonthAndYear(usr, request.getMonth(), request.getYear());

        if (claim.isPresent()) {
            throw new OperationFailedException("Claim for Specific User On this Month Already Exists ");
        } else {

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


            if (claim.get().getClaimStatus().equals(ClaimStatusEnum.CREATED)) {


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


            } else {
                throw new OperationFailedException("Only Claim with the status CREATED can be added");
            }


        }
    }

    @Override
    public ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetUserClaims(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        ApplicationUser usr = loggedUser.getInfo();

        List<OverTimeClaim> claims = claimService.GetUserClaims(usr, pageRequest);
        List<ClaimResponseDto> resp = new ArrayList<>();

        for (OverTimeClaim c : claims) {
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

            List<ClaimItem> items = claimItemService.GetByClaim(claim.get(), pageRequest);

            List<ClaimItemResponseDto> resp = new ArrayList<>();

            for (ClaimItem c : items) {
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

    @Override
    public ResponseEntity<ClaimItemResponseDto> ClaimItemAction(ItemActionDto request) throws ResourceNotFoundException, OperationFailedException {
        Optional<ClaimItem> item = claimItemService.GetItemById(request.getClaimItemId());

        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Claim Item with provided ID [" + request.getClaimItemId() + "] does not exist.");
        } else {
            ClaimItem it = item.get();

            ApplicationUser usr = loggedUser.getInfo();
            ClaimItemHistory ch = new ClaimItemHistory();
            ch.setClaimItem(it);
            ch.setCreatedBy(usr);

            if (request.getAction().equals(ClaimItemStatusEnum.CREATED)) {
                throw new OperationFailedException("Cannot Create this Item");
            } else if (request.getAction().equals(ClaimItemStatusEnum.APPROVED)) {
                if (it.getItemStatus().equals(ClaimItemStatusEnum.CREATED)) {
                    it.setItemStatus(ClaimItemStatusEnum.APPROVED);
                    claimItemService.SaveClaimItem(it);

                    ch.setCreatedDate(LocalDateTime.now());
                    ch.setClaimItemStatus(ClaimItemStatusEnum.APPROVED);
                    ch.setReason(request.getReason());
                    claimItemHistoryService.SaveCalimItemHistory(ch);

                } else {
                    throw new OperationFailedException("Cannot Approve this Item");
                }
            } else if (request.getAction().equals(ClaimItemStatusEnum.REJECTED)) {
                if (it.getItemStatus().equals(ClaimItemStatusEnum.CREATED)) {
                    it.setItemStatus(ClaimItemStatusEnum.REJECTED);
                    claimItemService.SaveClaimItem(it);

                    ch.setCreatedDate(LocalDateTime.now());
                    ch.setReason(request.getReason());
                    ch.setClaimItemStatus(ClaimItemStatusEnum.REJECTED);
                    claimItemHistoryService.SaveCalimItemHistory(ch);

                } else {
                    throw new OperationFailedException("Cannot Reject this Item");
                }
            } else if (request.getAction().equals(ClaimItemStatusEnum.CANCELLED)) {
                if (it.getItemStatus().equals(ClaimItemStatusEnum.CREATED)) {
                    it.setItemStatus(ClaimItemStatusEnum.CANCELLED);
                    claimItemService.SaveClaimItem(it);

                    ch.setCreatedDate(LocalDateTime.now());
                    ch.setClaimItemStatus(ClaimItemStatusEnum.CANCELLED);
                    ch.setReason(request.getReason());
                    claimItemHistoryService.SaveCalimItemHistory(ch);

                } else {
                    throw new OperationFailedException("Cannot Approve this Item");
                }
            }

            ClaimItemResponseDto resp = commons.GenerateClaimItem(it);
            return ResponseEntity.ok(resp);

        }

    }

    @Override
    public ResponseEntity<List<ItemTrackingResponseDto>> GetClaimItemTrackingById(Long id) throws ResourceNotFoundException {
        Optional<ClaimItem> item = claimItemService.GetItemById(id);

        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Claim Item with provided ID [" + id + "] does not exist.");
        } else {
            ClaimItem i = item.get();

            List<ClaimItemHistory> trackings = claimItemHistoryService.GetByClaimItems(i);
            List<ItemTrackingResponseDto> resp = new ArrayList<>();

            for (ClaimItemHistory ch : trackings) {
                ItemTrackingResponseDto r = commons.GenerateItemHistory(ch);
                resp.add(r);
            }

            return ResponseEntity.ok(resp);


        }
    }

    @Override
    public ResponseEntity<ClaimResponseDto> ClaimAction(ActionDto request) throws ResourceNotFoundException, OperationFailedException {
        Optional<OverTimeClaim> claim = claimService.GetClaimById(request.getClaimId());

        ApplicationUser usr = loggedUser.getInfo();

        if (!claim.isPresent()) {
            throw new ResourceNotFoundException("Claim with the provided ID is not Found");
        } else {
            OverTimeClaim c = claim.get();

            if (request.getAction().equals(ClaimStatusEnum.CREATED)) {
                throw new OperationFailedException("Cannot Create this Claim");
            } else if (request.getAction().equals(ClaimStatusEnum.SUBMITTED)) {

                // Check if claim has any items
                List<ClaimItem> items = claimItemService.GetByClaim(c, null);
                Integer itemsCount = items.size();

                if (itemsCount > 0) {

                    // Check if claim is elligible for submitting

                    if (c.getClaimStatus().equals(ClaimStatusEnum.CREATED)) {


                        // Check Claim User For Submitting

                        if (c.getUser().getUserRole().equals(UserRoleEnum.STAFF) || c.getUser().getUserRole().equals(UserRoleEnum.VOLUNTEER) || c.getUser().getUserRole().equals(UserRoleEnum.HR) || c.getUser().getUserRole().equals(UserRoleEnum.FINANCE)) {
                            // Submit Claim
                            c.setClaimStatus(ClaimStatusEnum.SUBMITTED);

                            // Claim History
                            ClaimHistory ch = new ClaimHistory();
                            ch.setClaim(c);
                            ch.setClaimStatus(ClaimStatusEnum.SUBMITTED);
                            ch.setCreatedDate(LocalDateTime.now());
                            ch.setReason("");
                            ch.setCreatedBy(usr);
                            claimHistoryService.SaveClaimHistory(ch);
                        } else if (c.getUser().getUserRole().equals(UserRoleEnum.SUPERVISOR)) {
                            // Submit Claim
                            c.setClaimStatus(ClaimStatusEnum.SUPERVISOR_APPROVED);

                            // Claim History
                            ClaimHistory ch = new ClaimHistory();
                            ch.setClaim(c);
                            ch.setClaimStatus(ClaimStatusEnum.SUBMITTED);
                            ch.setCreatedDate(LocalDateTime.now());
                            ch.setReason("");
                            ch.setCreatedBy(usr);
                            claimHistoryService.SaveClaimHistory(ch);

                            // Claim History
                            ClaimHistory ch1 = new ClaimHistory();
                            ch1.setClaim(c);
                            ch1.setClaimStatus(ClaimStatusEnum.SUPERVISOR_APPROVED);
                            ch1.setCreatedDate(LocalDateTime.now());
                            ch1.setReason("Supervisor Own Approval");
                            ch1.setCreatedBy(usr);
                            claimHistoryService.SaveClaimHistory(ch1);
                        } else if (c.getUser().getUserRole().equals(UserRoleEnum.HOD)) {
                            // Submit Claim
                            c.setClaimStatus(ClaimStatusEnum.HOD_APPROVED);

                            // Claim History
                            ClaimHistory ch = new ClaimHistory();
                            ch.setClaim(c);
                            ch.setClaimStatus(ClaimStatusEnum.SUBMITTED);
                            ch.setCreatedDate(LocalDateTime.now());
                            ch.setReason("");
                            ch.setCreatedBy(usr);
                            claimHistoryService.SaveClaimHistory(ch);

                            // Claim History
                            ClaimHistory ch1 = new ClaimHistory();
                            ch1.setClaim(c);
                            ch1.setClaimStatus(ClaimStatusEnum.HOD_APPROVED);
                            ch1.setCreatedDate(LocalDateTime.now());
                            ch1.setReason("Manager Own Approval");
                            ch1.setCreatedBy(usr);
                            claimHistoryService.SaveClaimHistory(ch1);
                        } else {
                            throw new OperationFailedException("User Role cannot Submit the Claim ");
                        }

                    } else {
                        throw new OperationFailedException("Claim cannot be Submitted because of Status " + c.getClaimStatus());
                    }


                } else {
                    throw new OperationFailedException("Claim has no items. Cannot be submitted");
                }

            }else{
                throw new OperationFailedException("Tunafanyia kazi hizo status nyingine ");
            }

            // Generate Claim Response
            ClaimResponseDto resp = commons.GenerateClaim(c);

            //Return  Claim Response
            return ResponseEntity.ok(resp);

        }
    }


}
