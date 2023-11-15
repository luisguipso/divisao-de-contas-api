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
class ExtratoPorUsuarioRepositoryIntegrationTest {
    @Autowired
    private ExtratoPorUsuarioRepository extratoPorUsuarioRepository;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    EnvironmentSetup environment;
    @BeforeEach
    void setup(){
        environment.setup();
    }

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasDivisiveis() {
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository.buscarValoresDividosDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);
        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(83.79), Percentage.withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = valoresPorUsuario.get(1);
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(BigDecimal.valueOf(68.56), Percentage.withPercentage(1));
    }

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasNaoDivisiveis() {
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository.buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.stream().findFirst().orElse(null);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(100), Percentage.withPercentage(1));
    }

}
