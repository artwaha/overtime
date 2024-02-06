package orci.or.tz.overtime.security;


import lombok.extern.slf4j.Slf4j;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.repository.UserRepository;
import orci.or.tz.overtime.utilities.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        Object credentials = authentication.getCredentials();

        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();

        ApplicationUser user = userRepository.findByUserName(name).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password provided");
        }
        return authenticateByPassType(name, password, user);
    }

    private Authentication authenticateByPassType(String username, String password, ApplicationUser user) {
        AppUserDetails userDetails = null;

        try{
            userDetails = new AppUserDetails(user.getEmail(), null, user.getId(),user.getFullName(), user.getUserRole(),user.getUserStatus(),user.getMobile(),user.getCheckNumber(),user.getPfNumber(),user.getReference());

            return new UsernamePasswordAuthenticationToken(userDetails, password, null);
        } catch (Exception e){
            log.error("Wrong credentials {}", username, password);
            throw new UsernameNotFoundException("Invalid username or password provided");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
