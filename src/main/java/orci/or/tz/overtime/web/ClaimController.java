package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.claims.ClaimRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.models.OverTimeClaim;
import orci.or.tz.overtime.services.ClaimService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.utilities.RandomPasswordGenerator;
import orci.or.tz.overtime.web.api.ClaimApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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


    @Override
    public ResponseEntity<ClaimResponseDto> GenerateClaim(ClaimRequestDto request) {


        String randomPassword = randomPasswordGenerator.generateRandomCharacters(10);

        // Declare new Claim Object
        OverTimeClaim c = new OverTimeClaim();
        c.setClaimMonth(request.getMonth());
        c.setClaimYear(request.getYear());
        c.setReferenceNumber(randomPassword);
        c.setUser(loggedUser.getInfo());

        // SaveClaim Service
        claimService.SaveClaim(c);

        // Generate Claim Response
        ClaimResponseDto resp = commons.GenerateClaim(c);

        //Return  Claim Response
        return ResponseEntity.ok(resp);

    }




}
