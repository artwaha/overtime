package orci.or.tz.overtime.services;


import orci.or.tz.overtime.enums.UserRoleEnum;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserDetails LoadUserByUsername(String username) throws UsernameNotFoundException {

        User.UserBuilder builder = null;
        ApplicationUser user = userRepo.findByUserName(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            builder = User.withUsername(username);
            builder.password("");
            builder.roles("");
        }
        return builder == null ? null : builder.build();
    }

    public ApplicationUser SaveUser(ApplicationUser u) {
        return userRepo.save(u);
    }

    public Optional<ApplicationUser> GetUserById(Long id) {
        return userRepo.findById(id);
    }

//    public Optional<ApplicationUser> GetUserByUserName(String userName) {
//        return userRepo.findByUserName(userName);
//    }

    public List<ApplicationUser> GetAllUsers(Pageable pageable) {
        return userRepo.findAllByOrderByIdDesc(pageable);
    }

    public Long TotalCount(){
        return userRepo.count();
    }


    public int countTotalItems() {
        return (int) userRepo.count();
    }

    public Optional<ApplicationUser> GetUserByUserName(String username) {
        return userRepo.findByUserName(username);
    }

     public List<ApplicationUser> GetByReferenceAndUserRole(Long reference, UserRoleEnum userRole) {
        return userRepo.findByReferenceAndUserRole(reference, userRole);
     }

    public Optional<ApplicationUser> GetUserByAttendanceId(Long attendanceId) {
        return userRepo.findByAttendanceId(attendanceId);
    }

}

