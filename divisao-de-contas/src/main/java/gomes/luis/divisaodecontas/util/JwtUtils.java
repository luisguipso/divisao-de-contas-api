package gomes.luis.divisaodecontas.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;


import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collection;
import java.util.Date;

public class JwtUtils {

    KeyStore.PrivateKeyEntry privateKeyEntry;

    public String createToken(String username, Collection<GrantedAuthority> authorities) {
        PrivateKey key = privateKeyEntry.getPrivateKey();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .jwtID("divisao-de-contas")
                .subject(username)
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + 3600))
                .claim("authorities", authorities)
                .build();
        JWSSigner signer = new RSASSASigner(key);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512).build();
        SignedJWT signed = new SignedJWT(header, claims);
        try {
            signed.sign(signer);
        } catch (JOSEException e){
            throw new AccessDeniedException(e.getMessage());
        }
        return signed.serialize();
    }
}
