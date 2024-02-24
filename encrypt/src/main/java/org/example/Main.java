package org.example;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.util.encoders.Base64;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        // Example data
        String merchantId = "683002007104225";
        String datetime = "20240209181953";
        String orderId = "NAG15731239931586";
        String challenge = "695EF3869547B6C07F5D56399935FB72D21737EA";

        // Example public and private keys (replace with your actual keys)
        String nagadGatewayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjBH1pFNSSRKPuMcNxmU5"
                + "jZ1x8K9LPFM4XSu11m7uCfLUSE4SEjL30w3ockFvwAcuJffCUwtSpbjr34cSTD7E"
                + "FG1Jqk9Gg0fQCKvPaU54jjMJoP2toR9fGmQV7y9fz31UVxSk97AqWZZLJBT2lmv7"
                + "6AgpVV0k0xtb/0VIv8pd/j6TIz9SFfsTQOugHkhyRzzhvZisiKzOAAWNX8RMpG+i"
                + "qQi4p9W9VrmmiCfFDmLFnMrwhncnMsvlXB8QSJCq2irrx3HG0SJJCbS5+atz+E1i"
                + "qO8QaPJ05snxv82Mf4NlZ4gZK0Pq/VvJ20lSkR+0nk+s/v3BgIyle78wjZP1vWLU"
                + "4wIDAQAB";

        String merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJakyLqojWTDAV"
                + "UdNJLvuXhROV+LXymqnukBrmiWwTYnJYm9r5cKHj1hYQRhU5eiy6NmFVJqJtwpxy"
                + "yDSCWSoSmIQMoO2KjYyB5cDajRF45v1GmSeyiIn0hl55qM8ohJGjXQVPfXiqEB5c"
                + "5REJ8Toy83gzGE3ApmLipoegnwMkewsTNDbe5xZdxN1qfKiRiCL720FtQfIwPDp9"
                + "ZqbG2OQbdyZUB8I08irKJ0x/psM4SjXasglHBK5G1DX7BmwcB/PRbC0cHYy3pXDm"
                + "LI8pZl1NehLzbav0Y4fP4MdnpQnfzZJdpaGVE0oI15lq+KZ0tbllNcS+/4MSwW+a"
                + "fvOw9bazAgMBAAECggEAIkenUsw3GKam9BqWh9I1p0Xmbeo+kYftznqai1pK4McV"
                + "WW9//+wOJsU4edTR5KXK1KVOQKzDpnf/CU9SchYGPd9YScI3n/HR1HHZW2wHqM6O"
                + "7na0hYA0UhDXLqhjDWuM3WEOOxdE67/bozbtujo4V4+PM8fjVaTsVDhQ60vfv9Cn"
                + "JJ7dLnhqcoovidOwZTHwG+pQtAwbX0ICgKSrc0elv8ZtfwlEvgIrtSiLAO1/CAf+"
                + "uReUXyBCZhS4Xl7LroKZGiZ80/JE5mc67V/yImVKHBe0aZwgDHgtHh63/50/cAyu"
                + "UfKyreAH0VLEwy54UCGramPQqYlIReMEbi6U4GC5AQKBgQDfDnHCH1rBvBWfkxPi"
                + "vl/yNKmENBkVikGWBwHNA3wVQ+xZ1Oqmjw3zuHY0xOH0GtK8l3Jy5dRL4DYlwB1q"
                + "gd/Cxh0mmOv7/C3SviRk7W6FKqdpJLyaE/bqI9AmRCZBpX2PMje6Mm8QHp6+1QpP"
                + "nN/SenOvoQg/WWYM1DNXUJsfMwKBgQCdtddE7A5IBvgZX2o9vTLZY/3KVuHgJm9d"
                + "QNbfvtXw+IQfwssPqjrvoU6hPBWHbCZl6FCl2tRh/QfYR/N7H2PvRFfbbeWHw9+x"
                + "wFP1pdgMug4cTAt4rkRJRLjEnZCNvSMVHrri+fAgpv296nOhwmY/qw5Smi9rMkRY"
                + "6BoNCiEKgQKBgAaRnFQFLF0MNu7OHAXPaW/ukRdtmVeDDM9oQWtSMPNHXsx+crKY"
                + "/+YvhnujWKwhphcbtqkfj5L0dWPDNpqOXJKV1wHt+vUexhKwus2mGF0flnKIPG2l"
                + "LN5UU6rs0tuYDgyLhAyds5ub6zzfdUBG9Gh0ZrfDXETRUyoJjcGChC71AoGAfmSc"
                + "iL0SWQFU1qjUcXRvCzCK1h25WrYS7E6pppm/xia1ZOrtaLmKEEBbzvZjXqv7PhLo"
                + "h3OQYJO0NM69QMCQi9JfAxnZKWx+m2tDHozyUIjQBDehve8UBRBRcCnDDwU015lQ"
                + "N9YNb23Fz+3VDB/LaF1D1kmBlUys3//r2OV0Q4ECgYBnpo6ZFmrHvV9IMIGjP7XI"
                + "lVa1uiMCt41FVyINB9SJnamGGauW/pyENvEVh+ueuthSg37e/l0Xu0nm/XGqyKCqk"
                + "AfBbL2Uj/j5FyDFrpF27PkANDo99CdqL5A4NQzZ69QRlCQ4wnNCq6GsYy2WEJyU2"
                + "D+K8EBSQcwLsrI7QL7fvQ==";

        try {
            // Encrypt sensitive data
            String encryptedSensitiveData = encryptSensitiveData(merchantId, datetime, orderId, challenge, nagadGatewayPublicKey);

            // Generate signature
            String generatedSignature = generateSignature(merchantId, datetime, orderId, challenge, merchantPrivateKey);

            // Create request body
            RequestBody requestBody = new RequestBody(datetime, encryptedSensitiveData, generatedSignature);

            // Convert to JSON and print
            Gson gson = new Gson();
            String jsonRequestBody = gson.toJson(requestBody);
            System.out.println(jsonRequestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encryptSensitiveData(String merchantId, String datetime, String orderId, String challenge,
                                              String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey)));
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String data = "{\"merchantId\":\"" + merchantId + "\",\"datetime\":\"" + datetime + "\",\"orderId\":\""
                + orderId + "\",\"challenge\":\"" + challenge + "\"}";
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.toBase64String(encryptedBytes);
    }

    public static String generateSignature(String merchantId, String datetime, String orderId, String challenge,
                                           String privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKey)));
        privateSignature.initSign(key);
        String data = "{\"merchantId\":\"" + merchantId + "\",\"datetime\":\"" + datetime + "\",\"orderId\":\""
                + orderId + "\",\"challenge\":\"" + challenge + "\"}";
        privateSignature.update(data.getBytes());
        byte[] signature = privateSignature.sign();
        return Base64.toBase64String(signature);
    }

    static class RequestBody {
        private String dateTime;
        private String sensitiveData;
        private String signature;

        public RequestBody(String dateTime, String sensitiveData, String signature) {
            this.dateTime = dateTime;
            this.sensitiveData = sensitiveData;
            this.signature = signature;
        }

        // Getters and setters (omitted for brevity)
    }
}
