package com.global.jwt.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PemUtils {

    public static PrivateKey readPrivateKey(String filepath, String algorithm) throws Exception {
        String raw = Files.readString(Path.of(filepath));
        raw = raw
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(raw);
        return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public static PublicKey readPublicKey(String filepath, String algorithm) throws Exception {
        String raw = Files.readString(Path.of(filepath));
        raw = raw
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(raw);
        return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(decoded));
    }
}

