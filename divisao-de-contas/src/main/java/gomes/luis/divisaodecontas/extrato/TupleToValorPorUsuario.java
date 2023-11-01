package gomes.luis.divisaodecontas.extrato;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.Tuple;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class TupleToValorPorUsuario implements Converter<Tuple, ValorPorUsuario> {

    public static final String ERRO_TAMANHO_DA_TUPLA = """
    Resultado da consulta diferente do experado.
     Impossivel converter para 'ValorPorUsuario'""";

    @Override
    public ValorPorUsuario convert(Tuple source) {
        if(source.toArray().length != 4){
            throw new IllegalArgumentException(ERRO_TAMANHO_DA_TUPLA);
        }
        return new ValorPorUsuario(
                getPessoa(source),
                source.get(3, BigDecimal.class));
    }

    private static Pessoa getPessoa(Tuple tuple) {
        Long id = tuple.get(0, Long.class);
        String nome = tuple.get(1, String.class);
        BigDecimal percentualFracionado = tuple.get(2, BigDecimal.class);
        int percentual = percentualFracionado.intValue();
        return new Pessoa(id, nome, percentual);
    }
}
