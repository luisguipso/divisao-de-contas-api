package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.despesa.DespesaService;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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
        periodo = new Periodo();
        spiedPeriodoService = spy(periodoService);
    }

}
