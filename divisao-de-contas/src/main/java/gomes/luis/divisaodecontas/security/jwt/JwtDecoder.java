package gomes.luis.divisaodecontas.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtDecoder {

    Resource keyStoreResource;
    String keyStoreType;
    char[] keyStorePassword;
    String keyAlias;
    String keyPassword;
    KeyStore.PrivateKeyEntry privateKeyEntry;

    public JwtDecoder(
            @Value("${security.keystore.resource}") Resource keyStoreResource,
            @Value("${security.keystore.type}") String keyStoreType,
            @Value("${security.keystore.password}") String keyStorePassword,
            @Value("${security.keystore.key.alias}") String keyAlias,
            @Value("${security.keystore.key.password}") String keyPassword
    ) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {
        this.keyStoreResource = keyStoreResource;
        this.keyStoreType = keyStoreType;
        this.keyStorePassword = keyStorePassword.toCharArray();
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;

        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        keyStore.load(this.keyStoreResource.getInputStream(), this.keyStorePassword);
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(this.keyStorePassword);
        this.privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, passwordProtection);
    }

    public String encode(String username, Collection<? extends GrantedAuthority> authorities) {
        PrivateKey key = privateKeyEntry.getPrivateKey();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .jwtID("divisao-de-contas")
                .subject(username)
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + 3600))
                .claim("authorities", authorities)
                .build();
        JWSSigner signer = new RSASSASigner(key);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS512).build();
        SignedJWT signed = new SignedJWT(header, claims);
        try {
            signed.sign(signer);
        } catch (JOSEException e) {
            throw new AccessDeniedException(e.getMessage());
        }
        return signed.serialize();
    }
}
