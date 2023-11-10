package gomes.luis.divisaodecontas.security.authentication.dto;

import gomes.luis.divisaodecontas.pessoa.Pessoa;

public class LoginResponse {
    public final String accessToken;
    public final Pessoa usuarioLogado;

    public LoginResponse(String accessToken, Pessoa usuarioLogado) {
        this.accessToken = accessToken;
        this.usuarioLogado = usuarioLogado;
    }
}
