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

    @BeforeEach
    void setup(){
        despesaRepositoryMocked = mock(DespesaRepository.class);
        despesaService = new DespesaService(despesaRepositoryMocked, mock(PeriodoService.class));
    }

    @Test
    void pagarDespesa_SetaIsPagoTrue(){
        Despesa despesa = criarDespesa(BigDecimal.valueOf(50));
        doReturn(Optional.of(despesa))
                .when(despesaRepositoryMocked).findById(despesa.getId());
        doReturn(despesa)
                .when(despesaRepositoryMocked).save(despesa);
        despesaService.pagarDespesa(despesa);
        assertTrue(despesa.isPago(),"Despesa está paga");
    }

    @Test
    void pagarDespesas_DevolveListaComTodasDespesasPagas() {
        List<Despesa> despesas = List.of(criarDespesa(BigDecimal.valueOf(50)),
                criarDespesa(BigDecimal.valueOf(60)),
                criarDespesa(BigDecimal.valueOf(70)));
        assertFalse(despesas.stream().anyMatch(Despesa::isPago));
        doReturn(Optional.of(mock(Despesa.class)))
                .when(despesaRepositoryMocked).findById(null);
        despesas.forEach(each -> doReturn(each)
                .when(despesaRepositoryMocked).save(each));

        List<Despesa> despesasPagas = despesaService.pagarDespesas(despesas);

        assertTrue(despesasPagas.stream().allMatch(Despesa::isPago));
    }
}
