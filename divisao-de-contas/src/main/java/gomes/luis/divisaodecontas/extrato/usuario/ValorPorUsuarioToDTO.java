package gomes.luis.divisaodecontas.extrato.usuario;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ValorPorUsuarioToDTO implements Converter<ValorPorUsuario, ValorPorUsuarioDTO> {
    @Override
    public ValorPorUsuarioDTO convert(ValorPorUsuario source) {
        return new ValorPorUsuarioDTO(source.getUsuario(), source.getValorTotal());
    }
}
