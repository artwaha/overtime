package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.claims.ClaimItemRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimItemResponseDto;
import orci.or.tz.overtime.dto.claims.ClaimRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.ClaimHistory;
import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.services.ClaimHistoryService;
import orci.or.tz.overtime.services.ClaimService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.utilities.RandomPasswordGenerator;
import orci.or.tz.overtime.web.api.ClaimApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
        return null;
    }


}
