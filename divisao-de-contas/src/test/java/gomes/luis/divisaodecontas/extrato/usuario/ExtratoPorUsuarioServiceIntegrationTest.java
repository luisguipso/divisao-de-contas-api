package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ExtratoPorUsuarioServiceIntegrationTest {
    @Autowired
    private ExtratoPorUsuarioService extratoPorUsuarioService;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    EnvironmentSetup environment;

    @BeforeEach
    void setup() {
        environment.setup();
    }

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaResumoParaTodosUsuarios() {
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioService.buscarValorDevidoPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);

        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(83.79), Percentage.withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = valoresPorUsuario.get(1);
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(BigDecimal.valueOf(168.65), Percentage.withPercentage(1));
    }

}
