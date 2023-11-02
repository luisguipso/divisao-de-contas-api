package gomes.luis.divisaodecontas.security.authentication.dto;

public class LoginResponse {
    public String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
