package com.appdirect;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Luis Tobon on 2015-02-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SignatureTest {
    @Value("${oauth.consumer.key}")
    private String key;
    @Value("${oauth.consumer.secret}")
    private String secret;

    @Ignore
    @Test
    public void testHeaderSignature() throws InvalidKeyException, NoSuchAlgorithmException, DecoderException {

        byte[] keyBytes = secret.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HMAC-SHA1");

        // Get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);

        // Compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal("https://secure-sands-5491.herokuapp.com/subscription/create?token=9b1190e3-6fb9-41c7-aee0-aa10ce43d0b2".getBytes());

        // Convert raw bytes to Hex
        byte[] hexBytes = new Hex().encode(rawHmac);

        //  Covert array of Hex bytes to a String
        Assert.assertThat(hexBytes, CoreMatchers.is(Base64.decodeBase64("EOFtdqUXWHvmDTl1lwMSqSAVY0w%3D".getBytes())));

    }
}
