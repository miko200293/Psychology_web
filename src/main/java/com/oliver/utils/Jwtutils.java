package com.oliver.utils;


import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.util.Date;
@Component
public class Jwtutils {
    private static long expire=604800;

    private static String secret="nhdgftefdoljkgufbdgswbbensrtvxsdw";

    public static String generateToken(String username){
        Date now=new Date();
        Date expiration =new Date(now.getTime()+1000*expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();


    }
    public static Claims getClaimsByToken(String token) throws ExpiredJwtException, SignatureException, MalformedJwtException, UnsupportedJwtException, IllegalArgumentException {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            // 令牌已过期
            throw ex;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            // 令牌不合法
            throw ex;
        }
    }



    public static <T> T getTClaimFromToken(String token, Class<T> valueType) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get(token, valueType);
    }


    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException ex) {

            return false;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {

            return false;
        }

        return true;
    }




}
