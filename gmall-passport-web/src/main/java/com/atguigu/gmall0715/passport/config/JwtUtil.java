package com.atguigu.gmall0715.passport.config;

import io.jsonwebtoken.*;

import java.util.Map;

public class JwtUtil {
    /**
     * 生成token
     * @param key
     * @param param
     * @param salt
     * @return
     */
    public static String encode(String key,Map<String,Object> param,String salt){
        if(salt!=null){
            key+=salt;
        }
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256,key);

        jwtBuilder = jwtBuilder.setClaims(param);

        String token = jwtBuilder.compact();
        return token;

    }

    /**
     * 解密token
     * @param token
     * @param key
     * @param salt
     * @return
     */
    public  static Map<String,Object> decode(String token , String key, String salt){
        Claims claims=null;
        if (salt!=null){
            key+=salt;
        }
        try {
            claims= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch ( JwtException e) {
            return null;
        }
        return  claims;
    }

}
