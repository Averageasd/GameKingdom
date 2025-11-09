package org.example.storemanagementbestpractice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET = "_g}W68P21gPwg#nzyH;u0xX:AxWwwe$mF&D;).0?xp_N])C/!DvN*AqHFax}DYx_/8f*dE-Eqq$/KRq$38;@p6RF/m_uWRN@?uwu=7[BH5TUu/,+Y!Y_eJQ{BKg9TFNhG&xF.[$NYUc{%gv}-b]$qa1#RH%82[u6Qmj@%JX=h-bbaB-3d!{*!:j$dCjdYfp#:5]wnq673m$?Pki5QCH::#AV]vX-pR8_L(r9=/DP]UZr,zn}AT0XiJT]ZTwyi3.=";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .issuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return userDetails.getUsername().equals(username)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
