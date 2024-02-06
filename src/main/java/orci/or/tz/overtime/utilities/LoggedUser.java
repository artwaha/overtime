package orci.or.tz.overtime.utilities;


import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class LoggedUser {

    @Autowired
    private UserRepository userRepo;

    public ApplicationUser getInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)  {

            ApplicationUser user = userRepo.findByUserName(auth.getName()).orElse(null);
            if (user != null){

                return user;
            }
            return null;
        }
        return null;

    }
}
