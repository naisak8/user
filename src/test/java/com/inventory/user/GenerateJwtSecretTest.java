package com.inventory.user;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class GenerateJwtSecretTest {

    @Test
    public void createKeys() {
        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        System.out.println(DatatypeConverter.printHexBinary(secretKey.getEncoded()));
    }
}
