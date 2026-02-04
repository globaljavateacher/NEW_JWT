package com.global.jwt.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.global.jwt.util.PemUtils;

import jakarta.servlet.http.HttpServletRequest;

public class JwtService {

    private static Algorithm ALGORITHM;

    public static void init(HttpServletRequest req) throws Exception {
    	String jwtRsaPrivate = ((Properties)req.getServletContext().getAttribute("appConfig")).getProperty("jwt.rsa.private");
    	String jwtRsaPublic = ((Properties)req.getServletContext().getAttribute("appConfig")).getProperty("jwt.rsa.public");
    	RSAPrivateKey PRIVATE_KEY = (RSAPrivateKey) PemUtils.readPrivateKey(jwtRsaPrivate, "RSA");
    	RSAPublicKey PUBLIC_KEY  = (RSAPublicKey)  PemUtils.readPublicKey(jwtRsaPublic, "RSA");
    	ALGORITHM = Algorithm.RSA256(PUBLIC_KEY, PRIVATE_KEY);
    }

    public static String createAccessToken(String userId) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer("globaljavateacher")
                .withSubject(userId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(900))) //15min
                .withJWTId(UUID.randomUUID().toString())
                .sign(ALGORITHM);
    }

    public static String createRefreshToken(String userId) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer("globaljavateacher")
                .withSubject(userId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(86400))) //1day
                .withJWTId(UUID.randomUUID().toString())
                .sign(ALGORITHM);
    }

    public static DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer("globaljavateacher")
                .build();
        return verifier.verify(token);
    }
}

