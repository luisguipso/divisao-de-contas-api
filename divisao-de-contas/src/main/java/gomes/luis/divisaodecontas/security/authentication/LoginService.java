package gomes.luis.divisaodecontas.security.authentication;

import gomes.luis.divisaodecontas.security.authentication.dto.LoginResponse;
import gomes.luis.divisaodecontas.usuario.Usuario;
import gomes.luis.divisaodecontas.usuario.UsuarioService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    UsuarioService usuarioService;

    LoginService(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    public LoginResponse login(String username, String password){
        Usuario usuario = buscarUsuario(username);
        if(!usuario.getPassword().equals(password))
            throwUnauthorized();
        return new LoginResponse("abc");
    }

    private static void throwUnauthorized() {
        throw getUnauthorized();
    }

    private static AuthenticationCredentialsNotFoundException getUnauthorized() {
        return new AuthenticationCredentialsNotFoundException("Unauthorized.");
    }

    private Usuario buscarUsuario(String username) {
        return usuarioService.buscarPorUsername(username)
                .orElseThrow(LoginService::getUnauthorized);
    }
}
