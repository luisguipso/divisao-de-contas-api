package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static gomes.luis.divisaodecontas.services.util.TestUtils.criarDespesa;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DespesaServiceTest {
    @Mock
    PagamentoService pagamentoService;
    @InjectMocks
    DespesaService despesaService;

    @Test
    public void pagarDespesa_SetaIsPagoTrue(){
        Despesa despesa = criarDespesa(BigDecimal.valueOf(50));
        despesaService.pagarDespesa(despesa);
        assertTrue(despesa.isPago(),"Despesa está paga");
    }

    @Test
    public void pagarDespesas_DevolveListaComTodasDespesasPagas() throws Exception {
        throw new Exception("implementar");
    }
}
