package orci.or.tz.overtime.repository;

import orci.or.tz.overtime.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    @Query(value = "delete from refresh_token r where r.userId=:userId", nativeQuery = true)
    void deleteByUser(Long userId);
}