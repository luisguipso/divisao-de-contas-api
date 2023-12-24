package gomes.luis.divisaodecontas.security.authentication;

import gomes.luis.divisaodecontas.security.authentication.dto.LoginResponse;
import gomes.luis.divisaodecontas.security.jwt.JwtDecoder;
import gomes.luis.divisaodecontas.usuario.Usuario;
import gomes.luis.divisaodecontas.usuario.UsuarioService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    UsuarioService usuarioService;
    JwtDecoder jwtDecoder;

    LoginService(UsuarioService usuarioService, JwtDecoder jwtDecoder){
        this.usuarioService = usuarioService;
        this.jwtDecoder = jwtDecoder;
    }

    public LoginResponse login(String username, String password){
        Usuario usuario = buscarUsuario(username);
        if(!usuario.getPassword().equals(password))
            throw getUnauthorizedException("Unauthorized.");
        return new LoginResponse(jwtDecoder.encode(usuario.getUsername(), usuario.getAuthorities()), usuario.getPessoa());
    }

    private Usuario buscarUsuario(String username) {
        return usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> getUnauthorizedException("User not found."));
    }

    private static AuthenticationCredentialsNotFoundException getUnauthorizedException(String message) {
        return new AuthenticationCredentialsNotFoundException(message);
    }

}
