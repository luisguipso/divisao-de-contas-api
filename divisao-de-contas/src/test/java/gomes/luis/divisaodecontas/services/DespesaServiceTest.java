package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.despesa.DespesaService;
import gomes.luis.divisaodecontas.despesa.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static gomes.luis.divisaodecontas.services.util.TestUtils.criarDespesa;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DespesaServiceTest {

    DespesaService despesaService;
    private DespesaRepository despesaRepositoryMocked;

    @BeforeEach
    public void setup(){
        despesaRepositoryMocked = mock(DespesaRepository.class);
        despesaService = new DespesaService(despesaRepositoryMocked);
    }

    @Test
    public void pagarDespesa_SetaIsPagoTrue(){
        Despesa despesa = criarDespesa(BigDecimal.valueOf(50));
        doReturn(Optional.of(despesa))
                .when(despesaRepositoryMocked).findById(despesa.getId());
        despesaService.pagarDespesa(despesa);
        assertTrue(despesa.isPago(),"Despesa está paga");
    }

    @Test
    public void pagarDespesas_DevolveListaComTodasDespesasPagas() {
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
