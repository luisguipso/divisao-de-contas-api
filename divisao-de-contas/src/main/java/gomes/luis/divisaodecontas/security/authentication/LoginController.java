package gomes.luis.divisaodecontas.security.authentication;

import gomes.luis.divisaodecontas.security.authentication.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginController {

    LoginService loginService;

    LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(String username, String password) {
        LoginResponse loginResponse = loginService.login(username, password);
        return ResponseEntity.ok(loginResponse);
    }
}
