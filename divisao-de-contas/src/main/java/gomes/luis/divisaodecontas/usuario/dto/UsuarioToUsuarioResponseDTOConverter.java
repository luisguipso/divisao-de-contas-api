package gomes.luis.divisaodecontas.usuario.dto;

import gomes.luis.divisaodecontas.usuario.Usuario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UsuarioToUsuarioResponseDTOConverter implements Converter<Usuario, UsuarioResponseDTO> {
    @Override
    public UsuarioResponseDTO convert(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername());
    }
}
