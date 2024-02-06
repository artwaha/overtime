package orci.or.tz.overtime.web;



import orci.or.tz.overtime.dto.user.ADRespDto;
import orci.or.tz.overtime.dto.user.JwtResponse;
import orci.or.tz.overtime.dto.user.LoginRequest;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.RefreshToken;
import orci.or.tz.overtime.security.jwt.JwtTokenUtil;
import orci.or.tz.overtime.services.ADService;
import orci.or.tz.overtime.services.RefreshTokenService;
import orci.or.tz.overtime.services.UserService;
import orci.or.tz.overtime.utilities.AppUserDetails;
import orci.or.tz.overtime.web.api.LoginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;


@RestController
public class LoginController implements LoginApi {

    @Autowired
    private ADService adService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Override
    public ResponseEntity<?> UserLogin(LoginRequest request) throws IOException, OperationFailedException {

        ADRespDto dto = adService.getADUser(request);


        if(dto.getCode() == 200){
            Optional<ApplicationUser> usr = userService.GetUserByUserName(request.getUsername());

            if(usr.isPresent()) {

                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
                ApplicationUser user = usr.get();


                final String token = jwtUtils.generateToken(user);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
                JwtResponse resp = new JwtResponse(token, userDetails.getId(), user.getUserName(), user.getEmail(), refreshToken.getToken(), jwtUtils.getExpirationDateFromToken(token));

                return ResponseEntity.ok(resp);
            }else{
                throw  new OperationFailedException("User Not Attended");
            }

        }
        else{
            throw  new OperationFailedException("Login Failed");
        }
    }
}

