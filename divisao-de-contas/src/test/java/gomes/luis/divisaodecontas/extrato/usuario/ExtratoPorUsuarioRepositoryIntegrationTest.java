package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.extrato.EnvironmentSetup;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", valueOf(83.79));
        assertValorPorUsuario(valoresPorUsuario.get(1), "Cyntia", valueOf(68.56));
    }

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasIndividuais() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository.buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Cyntia", valueOf(100));
    }

    @Test
    void dadoQuatroDespesasSendoUmaDivisivelEUmaIndividualEDuasIndicadas_BuscaDespesasIndividuais() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Cyntia", valueOf(100));
    }

    private static void assertValorPorUsuario(ValorPorUsuario valorPorUsuario1, String Cyntia, BigDecimal expected) {
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo(Cyntia);
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(expected, withPercentage(1));
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

        assertThat(valoresPorUsuario).hasSize(1);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", valueOf(200.35));
    }

    @Test
    void quandoHouverDuasDespesasIndividuaisIndicadasPorOutroUsuario_RetornaValor(){
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioRepository
                .buscarValoresIndividuaisDevidosIndicadosPorOutroUsuarioNoPeriodo(periodo.getId());

        assertThat(valoresPorUsuario).hasSize(1);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", valueOf(326.18));
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
        periodo = periodoRepository.findAll().stream().findFirst().orElseThrow();
        assertNotNull(periodo.getId());
    }
}
