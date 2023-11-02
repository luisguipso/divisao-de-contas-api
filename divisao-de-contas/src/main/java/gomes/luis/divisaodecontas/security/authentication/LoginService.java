package gomes.luis.divisaodecontas.security.authentication;

import gomes.luis.divisaodecontas.security.authentication.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public LoginResponse login(String username, String password){
        if(!username.equals("username") || !password.equals("123456"))
            throw new AuthenticationCredentialsNotFoundException("Unauthorized.");
        return new LoginResponse("abc");
    }
}
