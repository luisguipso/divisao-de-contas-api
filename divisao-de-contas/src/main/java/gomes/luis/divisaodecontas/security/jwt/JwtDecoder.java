package gomes.luis.divisaodecontas.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtDecoder {

    public static final String JWT_ID = "divisao-de-contas";
    public static final String INVALID_TOKEN_MSG = "Invalid token.";
    public static final String AUTHORITIES = "AUTHORITIES";
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
        setPrivateKeyEntry(keyAlias);
    }

    public String encode(String username, List<String> authorities) {
        PrivateKey key = privateKeyEntry.getPrivateKey();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .jwtID(JWT_ID)
                .subject(username)
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + 3600))
                .claim(AUTHORITIES, authorities)
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

    public Optional<JWTClaimsSet> decode(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            PublicKey publicKey = privateKeyEntry.getCertificate().getPublicKey();
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

            if (!signedJWT.verify(verifier))
                throw new RuntimeException(INVALID_TOKEN_MSG);

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String subject = claims.getSubject();
            String jwtID = claims.getJWTID();
            if (subject == null || subject.length() == 0 || !jwtID.equals(JWT_ID))
                throw new RuntimeException(INVALID_TOKEN_MSG);

            return Optional.of(claims);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(INVALID_TOKEN_MSG, e);
        }
    }

    public List<String> getAuthoritiesFromClaimsSet(JWTClaimsSet claimsSet) {
        Object authorities = claimsSet.getClaim(AUTHORITIES);
        return ((List<String>) authorities).stream()
                .toList();
    }

    private void setPrivateKeyEntry(String keyAlias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        keyStore.load(this.keyStoreResource.getInputStream(), this.keyStorePassword);
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(this.keyStorePassword);
        this.privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, passwordProtection);
    }
}
