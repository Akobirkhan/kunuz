package kunuz.util;


import io.jsonwebtoken.*;
import kunuz.dto.auth.JwtDTO;
import kunuz.enums.ProfileRole;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


public class JWTUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "very_long_mazgiskjdh2skhdhmgfhdfkugewvqhBKJLDEFVRQGVxneqwhbrflkw.SCQRGVKFJHWBEhgfnxdgdmjdhadasdasg7fgdfgdfd";

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        JwtBuilder jwtBuilder1 = jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setId("KunUzTest");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token){
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, profileRole);
        }
        return new JwtDTO(id);
    }
}
