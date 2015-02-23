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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Value("${oauth.header.key.regex}")
    private String authorizationKeyRegex;
    @Value("${oauth.header.signature.regex}")
    private String authorizationSecretRegex;

    private static final String HEADER="GET /rest/api/events/dummyChange HTTP/1.1\n" +
            "Host: www.appdirect.com\n" +
            "Content-Type: application/xml\n" +
            "Authorization: OAuth realm=\"\",\n" +
            "oauth_nonce=\"72250409\",\n" +
            "oauth_timestamp=\"1294966759\",\n" +
            "oauth_consumer_key=\"Dummy\",\n" +
            "oauth_signature_method=\"HMAC-SHA1\",\n" +
            "oauth_version=\"1.0\",\n" +
            "oauth_signature=\"IBlWhOm3PuDwaSdxE/Qu4RKPtVE=\"";


    @Test
    public void testExtractData(){
            Matcher matcherKey=Pattern.compile(authorizationKeyRegex).matcher(HEADER);
            Matcher matcherSecret=Pattern.compile(authorizationSecretRegex).matcher(HEADER);
            System.out.println("find"+matcherKey.find()+matcherSecret.find());

        System.out.println("key:"+matcherKey.group(1));
        System.out.println("signature:"+matcherSecret.group(1));


    }

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
