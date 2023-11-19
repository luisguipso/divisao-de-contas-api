package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.extrato.EnvironmentSetup;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;
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
    Periodo periodo;


    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaResumoParaTodosUsuarios() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioService.buscarValorDevidoPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);

        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(valueOf(83.79), withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = valoresPorUsuario.get(1);
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(valueOf(168.65), withPercentage(1));
    }

    @Test
    void dadoQuatroDespesasSendoUmaDivisivelUmaIndividualDuasIndicadas_BuscaResumoParaTodosUsuarios() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioService.buscarValorDevidoPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);

        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(valueOf(409.97), withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = valoresPorUsuario.get(1);
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(valueOf(168.65), withPercentage(1));
    }

    private void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas() {
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();
        setPeriodo();
    }

    private void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas() {
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        setPeriodo();
    }
    private void setPeriodo() {
        periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
    }

}
