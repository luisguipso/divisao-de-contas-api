package gomes.luis.divisaodecontas.util;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.pessoa.Pessoa;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;

public class TestUtils {
    public static Despesa criarDespesa(BigDecimal valor){
        return new Despesa("",
                mock(Pessoa.class),
                true,
                mock(Categoria.class),
                mock(Date.class),
                mock(Periodo.class),
                valor,
                false);
    }
}
