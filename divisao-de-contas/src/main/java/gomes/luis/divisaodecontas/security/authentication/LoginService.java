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
            throw getUnauthorizedException("Unauthorized.");
        return new LoginResponse("abc");
    }

    private Usuario buscarUsuario(String username) {
        return usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> getUnauthorizedException("User not found."));
    }

    private static AuthenticationCredentialsNotFoundException getUnauthorizedException(String message) {
        return new AuthenticationCredentialsNotFoundException(message);
    }

}
