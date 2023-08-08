package gomes.luis.divisaodecontas.extrato.categoria;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ValorPorCategoriaToDTO implements Converter<ValorPorCategoria, ValorPorCategoriaDTO> {
    @Override
    public ValorPorCategoriaDTO convert(ValorPorCategoria source) {
        return new ValorPorCategoriaDTO(source.getCategoria(), source.getValorTotal());
    }
}
