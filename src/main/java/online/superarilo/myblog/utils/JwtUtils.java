package online.superarilo.myblog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtils {
    public static boolean verify(String token, String userCode,String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("userCode", userCode).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
//    public static String getUserCode(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("userCode").asString();
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
    public static String createJWT(String userCode,String secret,long expire_time) {
        Date date = new Date(System.currentTimeMillis() + expire_time);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim("userCode", userCode).withExpiresAt(date).sign(algorithm);
    }
}
