package orci.or.tz.overtime.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import orci.or.tz.overtime.dto.executive.ExecutiveResponseDto;
import orci.or.tz.overtime.models.ApplicationUser;
import orci.or.tz.overtime.models.Department;
import orci.or.tz.overtime.models.Section;
import orci.or.tz.overtime.services.DepartmentService;
import orci.or.tz.overtime.services.SectionService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwtTokenValidity}")
    public long JWT_TOKEN_VALIDITY;// = 5 * 60 * 60;

    @Value("${jwtApiTokenValidity}")
    public long JWT_API_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private Mapper mapper;

    @Autowired
    private Commons commons;


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SectionService sectionService;




    public String generateTokenFromUsername(ApplicationUser user, String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());
        claims.put("userRole", user.getUserRole());
        claims.put("userStatus", user.getUserStatus());
        claims.put("mobile",user.getMobile());
        claims.put("checkNumber",user.getCheckNumber());
        claims.put("pfNumber",user.getPfNumber());
        claims.put("attendanceId",user.getAttendanceId());

        ModelMapper modelMapper = mapper.getModelMapper();
        switch (user.getUserRole().toString()) {
            case "EXECUTIVE_DIRECTOR":

                ExecutiveResponseDto ed = new ExecutiveResponseDto();
                claims.put("reference",ed);
            case "HOD":
                Optional<Department> dep = departmentService.GetDepartmentById(user.getReference());
                claims.put("reference",dep.get());
            case "SUPERVISOR":

                Optional<Section> section = sectionService.GetSectionById(user.getReference());
                claims.put("reference",section.get());
            case "STAFF":
                Optional<Section> sec = sectionService.GetSectionById(user.getReference());
                claims.put("reference",sec.get());
            default:
                claims.put("reference","");;
        }

        String myToken = Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((new Date()).getTime() + System.currentTimeMillis())).signWith(SignatureAlgorithm.HS512, secret)
                .compact();


        System.out.println("Token hii -> "+ myToken);
        return myToken;
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(ApplicationUser user) {

//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", userDetails.getId());
//        claims.put("fullName", userDetails.getFullName());
//        claims.put("email", userDetails.getEmail());
//        claims.put("userRole", userDetails.getUserRole());
//        claims.put("userStatus", userDetails.getUserStatus());
//        claims.put("mobile",userDetails.getMobile());


        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());
        claims.put("userRole", user.getUserRole());
        claims.put("userStatus", user.getUserStatus());
        claims.put("mobile",user.getMobile());
        claims.put("checkNumber",user.getCheckNumber());
        claims.put("pfNumber",user.getPfNumber());
        claims.put("attendanceId",user.getAttendanceId());
//       ModelMapper modelMapper = mapper.getModelMapper();
//        switch (user.getUserRole().toString()) {
//            case "EXECUTIVE_DIRECTOR":
//
//                ExecutiveResponseDto ed = new ExecutiveResponseDto();
//                claims.put("reference",ed);
//            case "DIRECTOR":
//
//                Optional<Directorate> dir = directorateService.GetDirectorateById(user.getReference());
//                claims.put("reference",dir.get());
//            case "MANAGER":
//                Optional<Department> dep = departmentService.GetDepartmentById(user.getReference());
//                claims.put("reference",dep.get());
//            case "SUPERVISOR":
//
//                Optional<Section> section = sectionService.GetSectionById(user.getReference());
//                claims.put("reference",section.get());
//            case "STAFF":
//                Optional<Section> sec = sectionService.GetSectionById(user.getReference());
//                claims.put("reference",sec.get());
//            default:
//                claims.put("reference","");;
//        }
        return doGenerateToken(claims, user.getUserName());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        Long currentTime = System.currentTimeMillis();
        Long expireTimeInMills = JWT_TOKEN_VALIDITY;

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(currentTime + expireTimeInMills))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final String usernameDB = userDetails.getUsername();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
