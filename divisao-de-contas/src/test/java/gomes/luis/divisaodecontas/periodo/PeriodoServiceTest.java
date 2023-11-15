package gomes.luis.divisaodecontas.periodo;

import gomes.luis.divisaodecontas.despesa.DespesaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.spy;

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
