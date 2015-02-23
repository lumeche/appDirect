package com.appdirect;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    private static final String HEADER="OAuth oauth_consumer_key=\"me-18731\", oauth_nonce=\"4682180487416631232\", oauth_signature=\"FHfK7shukmNeVBUBAdxjPZzpme/+3D\", oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1424660210\", oauth_version=\"1.0\"";

    private static final String signature="GBLeciP%2FeMBnz0bFou4jLlFqNj4%3D";
//    private static final String url="http://secure-sands-5491.herokuapp.com/subscription/change?token=268a0e7f-0b97-4b9f-b111-3bb5357fbebb";
    private static final String url="http://secure-sands-5491.herokuapp.com/subscription/change";

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
    public void testHeaderSignature() throws InvalidKeyException, NoSuchAlgorithmException, DecoderException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, UnsupportedEncodingException {

//        byte[] keyBytes = secret.getBytes();
//        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HMAC-SHA1");
//
//        // Get an hmac_sha1 Mac instance and initialize with the signing key
//        Mac mac = Mac.getInstance("HmacSHA1");
//        mac.init(signingKey);
//
//        // Compute the hmac on input data bytes
//        byte[] rawHmac = mac.doFinal(signature.getBytes());
//
//        // Convert raw bytes to Hex
//        byte[] hexBytes = new Hex().encode(rawHmac);
        //  Covert array of Hex bytes to a String

        // Get an hmac_sha1 key from the raw key bytes
        byte[] keyBytes = secret.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        // Get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);

        // Compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(url.getBytes());

        assertThat(Base64.encodeBase64(rawHmac),is(rawHmac));
        assertThat(signature.getBytes(), anyOf(is(rawHmac)));


    }
}
