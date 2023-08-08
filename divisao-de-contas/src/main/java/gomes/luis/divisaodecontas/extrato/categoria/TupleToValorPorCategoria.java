package gomes.luis.divisaodecontas.extrato.categoria;

import gomes.luis.divisaodecontas.categoria.Categoria;
import jakarta.persistence.Tuple;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class TupleToValorPorCategoria implements Converter<Tuple, ValorPorCategoria> {

    public static final String ERRO_TAMANHO_DA_TUPLA = """
    Resultado da consulta diferente do experado.
     Impossivel converter para 'ValorPorUsuario'""";

    @Override
    public ValorPorCategoria convert(Tuple source) {
        if(source.toArray().length != 3){
            throw new IllegalArgumentException(ERRO_TAMANHO_DA_TUPLA);
        }
        return new ValorPorCategoria(
                getCategoria(source),
                source.get(2, BigDecimal.class));
    }

    private static Categoria getCategoria(Tuple tuple) {
        return new Categoria(tuple.get(0, Long.class), tuple.get(1, String.class));
    }
}
