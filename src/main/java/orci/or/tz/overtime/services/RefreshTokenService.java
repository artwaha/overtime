package orci.or.tz.overtime.services;


import orci.or.tz.overtime.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    public int deleteByUserId(Long userId);

    public Optional<RefreshToken> findByToken(String token);
}
