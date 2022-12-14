package gomes.luis.divisaodecontas.services.util;

import gomes.luis.divisaodecontas.models.Categoria;
import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Periodo;
import gomes.luis.divisaodecontas.models.Pessoa;

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
