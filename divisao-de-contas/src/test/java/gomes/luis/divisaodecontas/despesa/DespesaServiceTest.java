package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.periodo.PeriodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static gomes.luis.divisaodecontas.util.TestUtils.criarDespesa;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {

    DespesaService despesaService;
    private DespesaRepository despesaRepositoryMocked;
    private PeriodoService periodoServiceMocked;

    @BeforeEach
    void setup(){
        despesaRepositoryMocked = mock(DespesaRepository.class);
        periodoServiceMocked = mock(PeriodoService.class);
        despesaService = new DespesaService(despesaRepositoryMocked, periodoServiceMocked);
    }

    @Test
    void pagarDespesa_SetaIsPagoTrue(){
        Despesa despesa = criarDespesa(BigDecimal.valueOf(50));
        doReturn(Optional.of(despesa))
                .when(despesaRepositoryMocked).findById(despesa.getId());
        despesaService.pagarDespesa(despesa);
        assertTrue(despesa.isPago(),"Despesa está paga");
    }

    @Test
    void pagarDespesas_DevolveListaComTodasDespesasPagas() {
        List<Despesa> despesas = List.of(criarDespesa(BigDecimal.valueOf(50)),
                criarDespesa(BigDecimal.valueOf(60)),
                criarDespesa(BigDecimal.valueOf(70)));
        doReturn(Optional.of(mock(Despesa.class)))
                .when(despesaRepositoryMocked).findById(null);
        assertFalse(despesas.stream().allMatch(d -> d.isPago()));

        List<Despesa> despesasPagas = despesaService.pagarDespesas(despesas);

        assertTrue(despesasPagas.stream().allMatch(d -> d.isPago()));
    }
}
