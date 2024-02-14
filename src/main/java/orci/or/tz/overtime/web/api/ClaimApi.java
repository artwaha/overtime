package orci.or.tz.overtime.web.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import orci.or.tz.overtime.dto.claims.ClaimItemRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimItemResponseDto;
import orci.or.tz.overtime.dto.claims.ClaimRequestDto;
import orci.or.tz.overtime.dto.claims.ClaimResponseDto;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/claims")
@Api(value = "Claim Management", description = "Manage Claims on the web")
public interface ClaimApi {


    @ApiOperation(value = "Generate Claim", notes = "Generate Claim")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimResponseDto> GenerateClaim(@Valid @RequestBody ClaimRequestDto request) throws OperationFailedException;

    @ApiOperation(value = "Create Claim Item", notes = "Create Claim Item")
    @RequestMapping(value = "/request/item", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimItemResponseDto> CreateClaimItem(@Valid @RequestBody ClaimItemRequestDto request) throws OperationFailedException, ResourceNotFoundException;

    @ApiOperation(value = "Get Claims By User", notes = "Get Claims By User")
    @GetMapping(value = "user", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetUserClaims(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);


    @ApiOperation(value = "Get Claim Items By Claim", notes = "Get Claim Items By Claim")
    @GetMapping(value = "user/item", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimItemResponseDto>>> GetClaimItems(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam Long id) throws ResourceNotFoundException;


}
