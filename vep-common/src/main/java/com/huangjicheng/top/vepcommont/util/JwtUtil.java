package com.huangjicheng.top.vepcommont.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/2 23:31
 */
public class JwtUtil {
    /**
     * @param userId     用户编号
     * @param secrect    秘钥(密码)
     * @param expireTime 过期时间单位s
     * @return
     */
    public static String getToken(String userId, String secrect, int expireTime) {
        Date createDate = new Date();
        Date expireDate = DateUtils.addSeconds(createDate, expireTime);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //token创建底层使用的是设计模式中的创建者模式,了解该模式对于下面的代码比较容易理解
        String token = JWT.create().withHeader(header)
                .withClaim("userId", userId) //playload的一部分:withClaim底层是一个map,可以不断使用链式表达式存数据
                .withIssuedAt(createDate)//创建时间 //playload的一部分
                .withExpiresAt(expireDate) //过期时间 //playload的一部分
                .sign(Algorithm.HMAC256(secrect));//生成 signature
        return token;

    }

    //如果token过期了,解析时就会报错,所以捕捉到异常时就知道是否过期了
    public static DecodedJWT decodeToken(String token, String secretKey) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException ex) {
            System.out.println("token 过期了");
            throw ex;
        }
    }

    //也可以通过token不需要密钥直接获取 DecodedJWT
    public static DecodedJWT decodedToken(String token) {
        DecodedJWT decode = JWT.decode(token);
        return decode;
        //Map<String, Claim> claims = decode.getClaims();
    }

    //获取payLoad的值
    public static Object getUserId(String token, String userId, String secrect) {
        DecodedJWT decodedJWT = decodeToken(token, secrect);
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim claim = claims.get(userId);//也可以通过claims获取其他值,具体根据存到playlaod里面的数据来取值
        return claim.asString();
    }


}