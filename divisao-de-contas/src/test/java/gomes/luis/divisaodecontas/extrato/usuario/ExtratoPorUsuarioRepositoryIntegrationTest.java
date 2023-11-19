package gomes.luis.divisaodecontas.extrato.usuario;

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
class ExtratoPorUsuarioRepositoryIntegrationTest {
    @Autowired
    private ExtratoPorUsuarioRepository extratoPorUsuarioRepository;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    EnvironmentSetup environment;
    Periodo periodo;

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasDivisiveis() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository.buscarValoresDividosDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);
        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(valueOf(83.79), withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = valoresPorUsuario.get(1);
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(valueOf(68.56), withPercentage(1));
    }

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasIndividuais() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository.buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.stream().findFirst().orElse(null);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(valueOf(100), withPercentage(1));
    }

    @Test
    void dadoQuatroDespesasSendoUmaDivisivelEUmaIndividualEDuasIndicadas_BuscaDespesasIndividuais() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        ValorPorUsuario valorPorUsuario1 = valoresPorUsuario.stream().findFirst().orElse(null);
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(valueOf(100), withPercentage(1));
    }

    @Test
    void quandoNaoHouverDespesasIndividuaisIndicadasPorOutroUsuario_RetornarVazio(){
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosIndicadosPorOutroUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).isEmpty();
    }

    @Test
    void quandoHouverDespesasIndividuaisIndicadasPorOutroUsuario_RetornarDados(){
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEUmaDespesaIndicada();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosIndicadosPorOutroUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);

    }

    @Test
    void quandoHouverUmaDespesaIndividualIndicadaPorOutroUsuario_RetornaValor(){
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEUmaDespesaIndicada();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosIndicadosPorOutroUsuarioNoPeriodo(periodo.getId());

        ValorPorUsuario valorPorUsuario = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario.getValorTotal()).isCloseTo(valueOf(200.35), withPercentage(1));
    }

    @Test
    void quandoHouverDuasDespesasIndividuaisIndicadasPorOutroUsuario_RetornaValor(){
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosIndicadosPorOutroUsuarioNoPeriodo(periodo.getId());

        ValorPorUsuario valorPorUsuario = valoresPorUsuario.get(0);
        assertThat(valorPorUsuario.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario.getValorTotal()).isCloseTo(valueOf(326.18), withPercentage(1));
    }

    void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas(){
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        setPeriodoParaTeste();
    }

    void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEUmaDespesaIndicada(){
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEUmaDespesaIndicada();
        setPeriodoParaTeste();
    }

    void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas(){
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();
        setPeriodoParaTeste();
    }

    private void setPeriodoParaTeste() {
        periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());
    }
}
