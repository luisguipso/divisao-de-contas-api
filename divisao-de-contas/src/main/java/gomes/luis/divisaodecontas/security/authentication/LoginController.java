package gomes.luis.divisaodecontas.security.authentication;

import gomes.luis.divisaodecontas.security.authentication.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(String username, String password) {
        if(username.equals("username") && password.equals("123456"))
            return ResponseEntity.ok(new LoginResponse("abc"));
        throw new AuthenticationCredentialsNotFoundException("Unauthorized.");
    }
}
