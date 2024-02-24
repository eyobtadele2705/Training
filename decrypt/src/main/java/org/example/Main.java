package org.example;

import com.google.gson.Gson;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {
        // Placeholder for sensitive data received in the response

        String sensitiveData = "abZ7i+LXN5wIssljKErBouveNrm652nUrSSERker4Y66z2V5HPTlysD+WYq0hlb5ekARHCUOdQ0fAb66w2WnCsi1EfhUN6hBWIpFMfsR7OW58Ifaj1dS85OpCin+etsM8U2Hgs3sepr4Mwx7zDCPTlkfkZeI9Jr31rovGaew6IzI9fCWZxLEL+FlB1qqaKesWH94Uk0yNjx7qczzO2gHmY38sbA48kQPyDUU4EyoY9jogAH/qFID/vl/xYnyAmv0gL0+LLsEZ8gulmmopFCq+1TbJMolZGRMEo1CfGCCvndetrCBv6dJ/EnIYx7YZA5AeRakz/70HL1jqytpVkRQkw==";
        String signature = "U67eh+yUrS2VAdTl5SXZ86X9qcXrj+OAe61ONYEiy0ywGECMb6fxwExq0uyKGn50ohQHPL/Oo5dGoAOFUTB1TZEZuexEpB82qA2hpbqJfj1AljW70YQl6lgBhS5Dwjw7v+5GmBALd9wNrfgDee7Yyco6U6Ax4rC5ZxNQleYOgi3BpoptN25GO3vKNHf1WDDfXGlUqpk/jbEIrl7IkmJgFTDDU6dG9a5xd0gZgkHD4m1Lr6o5SxEZMsum5EfBuRfWwJMfsK9wk/mFdWgO1tnEAJVS7xHkVSsGV4U1nybOGA5+5VgqivS9F0j1jG2v3YRckb8Cym9MkpV9lK7xjISQ6Q==";

        // Placeholder for your private key
        String merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJakyLqojWTDAVUdNJLvuXhROV+LXymqnukBrmiWwTYnJYm9r5cKHj1hYQRhU5eiy6NmFVJqJtwpxyyDSCWSoSmIQMoO2KjYyB5cDajRF45v1GmSeyiIn0hl55qM8ohJGjXQVPfXiqEB5c5REJ8Toy83gzGE3ApmLipoegnwMkewsTNDbe5xZdxN1qfKiRiCL720FtQfIwPDp9ZqbG2OQbdyZUB8I08irKJ0x/psM4SjXasglHBK5G1DX7BmwcB/PRbC0cHYy3pXDmLI8pZl1NehLzbav0Y4fP4MdnpQnfzZJdpaGVE0oI15lq+KZ0tbllNcS+/4MSwW+afvOw9bazAgMBAAECggEAIkenUsw3GKam9BqWh9I1p0Xmbeo+kYftznqai1pK4McVWW9//+wOJsU4edTR5KXK1KVOQKzDpnf/CU9SchYGPd9YScI3n/HR1HHZW2wHqM6O7na0hYA0UhDXLqhjDWuM3WEOOxdE67/bozbtujo4V4+PM8fjVaTsVDhQ60vfv9CnJJ7dLnhqcoovidOwZTHwG+pQtAwbX0ICgKSrc0elv8ZtfwlEvgIrtSiLAO1/CAf+uReUXyBCZhS4Xl7LroKZGiZ80/JE5mc67V/yImVKHBe0aZwgDHgtHh63/50/cAyuUfKyreAH0VLEwy54UCGramPQqYlIReMEbi6U4GC5AQKBgQDfDnHCH1rBvBWfkxPivl/yNKmENBkVikGWBwHNA3wVQ+xZ1Oqmjw3zuHY0xOH0GtK8l3Jy5dRL4DYlwB1qgd/Cxh0mmOv7/C3SviRk7W6FKqdpJLyaE/bqI9AmRCZBpX2PMje6Mm8QHp6+1QpPnN/SenOvoQg/WWYM1DNXUJsfMwKBgQCdtddE7A5IBvgZX2o9vTLZY/3KVuHgJm9dQNbfvtXw+IQfwssPqjrvoU6hPBWHbCZl6FCl2tRh/QfYR/N7H2PvRFfbbeWHw9+xwFP1pdgMug4cTAt4rkRJRLjEnZCNvSMVHrri+fAgpv296nOhwmY/qw5Smi9rMkRY6BoNCiEKgQKBgAaRnFQFLF0MNu7OHAXPaW/ukRdtmVeDDM9oQWtSMPNHXsx+crKY/+YvhnujWKwhphcbtqkfj5L0dWPDNpqOXJKV1wHt+vUexhKwus2mGF0flnKIPG2lLN5UU6rs0tuYDgyLhAyds5ub6zzfdUBG9Gh0ZrfDXETRUyoJjcGChC71AoGAfmSciL0SWQFU1qjUcXRvCzCK1h25WrYS7E6pppm/xia1ZOrtaLmKEEBbzvZjXqv7PhLoh3OQYJO0NM69QMCQi9JfAxnZKWx+m2tDHozyUIjQBDehve8UBRBRcCnDDwU015lQN9YNb23Fz+3VDB/LaF1D1kmBlUys3//r2OV0Q4ECgYBnpo6ZFmrHvV9IMIGjP7XIlVa1uiMCt41FVyINB9SJnamGGauW/pyENvEVh+ueuthSg37e/l0Xu0nm/XGqyKCqkAfBbL2Uj/j5FyDFrpF27PkANDo99CdqL5A4NQzZ69QRlCQ4wnNCq6GsYy2WEJyU2D+K8EBSQcwLsrI7QL7fvQ==";

        // Placeholder for the public key
        String pgPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjBH1pFNSSRKPuMcNxmU5jZ1x8K9LPFM4XSu11m7uCfLUSE4SEjL30w3ockFvwAcuJffCUwtSpbjr34cSTD7EFG1Jqk9Gg0fQCKvPaU54jjMJoP2toR9fGmQV7y9fz31UVxSk97AqWZZLJBT2lmv76AgpVV0k0xtb/0VIv8pd/j6TIz9SFfsTQOugHkhyRzzhvZisiKzOAAWNX8RMpG+iqQi4p9W9VrmmiCfFDmLFnMrwhncnMsvlXB8QSJCq2irrx3HG0SJJCbS5+atz+E1iqO8QaPJ05snxv82Mf4NlZ4gZK0Pq/VvJ20lSkR+0nk+s/v3BgIyle78wjZP1vWLU4wIDAQAB";

        // Get sensitive data and signature from the response
        byte[] encryptedSensitiveData = Base64.getDecoder().decode(sensitiveData);
        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        // Decrypt the sensitive data
        String decryptedSensitiveData = decryptDataUsingPrivateKey(encryptedSensitiveData, merchantPrivateKey);
        System.out.println("Decrypted Sensitive Data: " + decryptedSensitiveData);

        // Parse the JSON string into a JsonObject
        JsonObject jsonObject = new Gson().fromJson(decryptedSensitiveData, JsonObject.class);

        System.out.println(jsonObject.paymentReferenceId);
        System.out.println(jsonObject.challenge);
        System.out.println(jsonObject.acceptDateTime);

        // Verify the signature
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(pgPublicKey)));
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(decryptedSensitiveData.getBytes());
        boolean isVerified = publicSignature.verify(signatureBytes);
        if (isVerified) {
            System.out.println("Signature verification successful.");
        } else {
            System.out.println("Signature verification failed.");
        }
    }

    public static String decryptDataUsingPrivateKey(byte[] data, String privateKeyString) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(data);

        return new String(decryptedData);
    }

    class JsonObject {
        String paymentReferenceId;
        String challenge;
        String acceptDateTime;

        public JsonObject(String paymentReferenceId, String challenge, String acceptDateTime) {
            this.paymentReferenceId = paymentReferenceId;
            this.challenge = challenge;
            this.acceptDateTime = acceptDateTime;
        }
    }
}
