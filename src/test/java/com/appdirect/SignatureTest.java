package com.appdirect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    private static final String HEADER="OAuth oauth_consumer_key=\"me-18731\", oauth_nonce=\"4682180487416631232\", oauth_signature=\"FHfK7shukmNeVBUBAdxjPZzpme/+3D\", oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1424660210\", oauth_version=\"1.0\"";

    @Test
    public void testExtractData(){
            Matcher matcherKey=Pattern.compile(authorizationKeyRegex).matcher(HEADER);
            Matcher matcherSecret=Pattern.compile(authorizationSecretRegex).matcher(HEADER);
            System.out.println("find"+matcherKey.find()+matcherSecret.find());

        System.out.println("key:"+matcherKey.group(1));
        System.out.println("signature:"+matcherSecret.group(1));
    }

}
