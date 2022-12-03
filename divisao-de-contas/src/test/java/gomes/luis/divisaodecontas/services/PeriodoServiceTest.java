package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Periodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static gomes.luis.divisaodecontas.services.util.TestUtils.criarDespesa;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeriodoServiceTest {
    @Mock
    DespesaService despesaService;
    @InjectMocks
    PeriodoService periodoService;

    PeriodoService spiedPeriodoService;

    Periodo periodo;

    @BeforeEach
    public void setUp() {
        periodo = new Periodo(LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 1));
        spiedPeriodoService = spy(periodoService);
    }

    @Test
    public void calcularValorTotal_DeveRetornarSomaDoValorDasDespesas() {
        Despesa despesa1 = criarDespesa(BigDecimal.valueOf(50));
        Despesa despesa2 = criarDespesa(BigDecimal.valueOf(80));
        Despesa despesa3 = criarDespesa(BigDecimal.valueOf(40));

        when(despesaService.buscarDespesasPorPeriodo(periodo))
                .thenReturn(List.of(despesa1, despesa2, despesa3));

        assertEquals(BigDecimal.valueOf(170), periodoService.calcularValorTotal(periodo), "Valor total");
    }

    @Test
    public void calcularValorTotalDePeriodoSemDespesas_DeveRetornarValorZero() {
        when(despesaService.buscarDespesasPorPeriodo(periodo))
                .thenReturn(new ArrayList<>());

        assertEquals(BigDecimal.valueOf(0), periodoService.calcularValorTotal(periodo), "Valor total");
    }

    @Test
    public void atualizarAtributosParaFecharPeriodo_DeveRetornarPeriodoComIsFechadoTrue() {
        Periodo fechado = periodoService.atualizarAtributosParaFechar(periodo);

        assertTrue(fechado.isFechado());
    }

    @Test
    public void atualizarAtributosParaFecharPeriodo_DeveRetornarPeriodoComDataFimPreenchida() {
        Periodo fechado = periodoService.atualizarAtributosParaFechar(periodo);

        assertNotNull(fechado.getFim());
    }

    @Test
    public void atualizarAtributosParaFecharPeriodo_DeveRetornarPeriodoComDataFimPreenchidaComDataDeHoje() {
        Periodo fechado = periodoService.atualizarAtributosParaFechar(periodo);

        assertEquals(new Date().toString(), fechado.getFim().toString(), "Data Fim do periodo");
    }
    @Test
    public void fecharPeriodo_DeveChamarMetodoParaAtualizarAtributosParaFecharPeriodo() {
        doReturn(mock(BigDecimal.class))
                .when(spiedPeriodoService).calcularValorTotal(any(Periodo.class));
        doNothing()
                .when(spiedPeriodoService).pagarDespesas(any(Periodo.class));
        doReturn(mock(Periodo.class))
                .when(spiedPeriodoService).atualizar(any(), any(Periodo.class));

        spiedPeriodoService.fecharPeriodo(periodo);

        verify(spiedPeriodoService, times(1))
                .atualizarAtributosParaFechar(any(Periodo.class));
    }
    @Test
    public void fecharPeriodo_DeveChamarMetodoParaCalcularValorTotal() {
        doReturn(mock(BigDecimal.class))
                .when(spiedPeriodoService).calcularValorTotal(any(Periodo.class));
        doNothing()
                .when(spiedPeriodoService).pagarDespesas(any(Periodo.class));
        doReturn(mock(Periodo.class))
                .when(spiedPeriodoService).atualizar(any(), any(Periodo.class));

        spiedPeriodoService.fecharPeriodo(periodo);

        verify(spiedPeriodoService, times(1))
                .calcularValorTotal(any(Periodo.class));
    }

    @Test
    public void fecharPeriodo_DeveChamarMetodoParaAtualizarPeriodo() {
        doNothing()
                .when(spiedPeriodoService).pagarDespesas(any(Periodo.class));
        doReturn(mock(Periodo.class))
                .when(spiedPeriodoService).atualizar(nullable(Long.class), any(Periodo.class));

        spiedPeriodoService.fecharPeriodo(periodo);

        verify(spiedPeriodoService, times(1))
                .calcularValorTotal(any(Periodo.class));
    }
}
