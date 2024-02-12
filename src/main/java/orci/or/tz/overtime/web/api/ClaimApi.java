package orci.or.tz.overtime.web.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import orci.or.tz.overtime.dto.claims.ClaimRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.dto.directorate.DirectorateRequestDto;
import orci.or.tz.overtime.dto.directorate.DirectorateResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@RequestMapping("/api/claims")
@Api(value = "Claim Management", description = "Manage Claims on the web")
public interface ClaimApi {


    @ApiOperation(value = "Generate Claim", notes = "Generate Claim")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimResponseDto> GenerateClaim(@Valid @RequestBody ClaimRequestDto request);



}
