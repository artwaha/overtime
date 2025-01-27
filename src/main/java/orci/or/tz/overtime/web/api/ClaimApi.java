package orci.or.tz.overtime.web.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import orci.or.tz.overtime.dto.claims.*;
import orci.or.tz.overtime.dto.section.SectionResponseDto;
import orci.or.tz.overtime.enums.ClaimStatusEnum;
import orci.or.tz.overtime.enums.MonthEnum;
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

    @ApiOperation(value = "Update CLaim", notes = "Update Claim")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimResponseDto> UpdateClaim(@Valid @RequestBody ClaimRequestDto request, @PathVariable Long id) throws ResourceNotFoundException, OperationFailedException;


    @ApiOperation(value = "Get Claim By Id", notes = "Get Claim By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<ClaimResponseDto> GetClaimById(@PathVariable Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "Create Claim Item", notes = "Create Claim Item")
    @RequestMapping(value = "/request/item", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimItemResponseDto> CreateClaimItem(@Valid @RequestBody ClaimItemRequestDto request) throws OperationFailedException, ResourceNotFoundException;


    @ApiOperation(value = "Update CLaim Item", notes = "Update Claim Item")
    @RequestMapping(value = "item/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimItemResponseDto> UpdateClaimItem(@Valid @RequestBody ClaimItemUpdateDto request, @PathVariable Long id) throws ResourceNotFoundException, OperationFailedException;

    @ApiOperation(value = "Get Claims By User", notes = "Get Claims By User")
    @GetMapping(value = "user", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetUserClaims(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size);


    @ApiOperation(value = "Get Claim Items By Claim", notes = "Get Claim Items By Claim")
    @GetMapping(value = "user/item", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimItemResponseDto>>> GetClaimItems(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "Claim Item Actions", notes = "CLaim Item Actions")
    @RequestMapping(value = "/item/action", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimItemResponseDto> ClaimItemAction(@Valid @RequestBody ItemActionDto request) throws ResourceNotFoundException, OperationFailedException;

    @ApiOperation(value = "Get Claim Item Trackings By Id", notes = "Get Claim Item Trackings By Id")
    @RequestMapping(value = "/item/trackings/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<List<ItemTrackingResponseDto>> GetClaimItemTrackingById(@PathVariable Long id) throws ResourceNotFoundException;

    @ApiOperation(value = "Claim Actions", notes = "Claim  Actions")
    @RequestMapping(value = "/action", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<ClaimResponseDto> ClaimAction(@Valid @RequestBody ActionDto request) throws ResourceNotFoundException, OperationFailedException;

    @ApiOperation(value = "Get Claim Trackings By Id", notes = "Get Claim Trackings By Id")
    @RequestMapping(value = "/trackings/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<List<TrackingResponseDto>> GetClaimTrackingById(@PathVariable Long id) throws ResourceNotFoundException;


    @ApiOperation(value = "Get Claims For Approval", notes = "Get Claims For Approval")
    @GetMapping(value = "approval/search", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetClaimsForApproval(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam(required = false) MonthEnum month, @RequestParam(required = false) ClaimStatusEnum status, @RequestParam(required = false) Integer year) throws OperationFailedException;

    @ApiOperation(value = "Get Claims By Criteria", notes = "Get Claims By Criteria")
    @GetMapping(value = "/search", produces = "application/json")
    ResponseEntity<GenericResponse<List<ClaimResponseDto>>> GetClaimsByCriteria(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam(required = false) MonthEnum month, @RequestParam(required = false) ClaimStatusEnum status, @RequestParam(required = false) Integer year) throws OperationFailedException;

}
