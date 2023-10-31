package UserBackend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.ByteArrayInputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

public  final class TokenProvider {
    private byte[] key = generateSecureSecretKey();
    private ArrayList<String > invalidTokens = new ArrayList<String>();
    private byte[] generateSecureSecretKey()
    {
        SecureRandom secureRandom  = new SecureRandom();
        byte[] keybytes = new byte[64];
        secureRandom.nextBytes(keybytes);
        System.out.println(keybytes);
        return keybytes;
    }

    public String generateToken(Long Id, String role)
    {
        Date now = new Date();
        Date expire = new Date(now.getTime() + 3600000);
        Claims claims = Jwts.claims().setSubject(Id.toString());
        claims.put("role",role);
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expire).signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS512).compact();
    }
    public  Boolean validateToken(String token) {
        System.out.println(key);
        if (invalidTokens.contains(token))
            return Boolean.FALSE;
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
    }

    public String getRoleFromToken(String token)
    {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }
    public String getIdFromToken(String token)
    {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public void invalidateToken(String token) {
        invalidTokens.add(token);
    }
}
