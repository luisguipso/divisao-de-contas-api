package gomes.luis.divisaodecontas.extrato.categoria;

import gomes.luis.divisaodecontas.extrato.EnvironmentSetup;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaRepository;
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
class ExtratoPorCategoriaServiceIntegrationTest {
    @Autowired
    private ExtratoPorCategoriaService extratoPorCategoriaService;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    EnvironmentSetup environment;

    @Test
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaResumoParaTodosUsuarios() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();
        Periodo periodo = findPeriodo();
        Pessoa luis = findPessoa("Luis");

        List<ValorPorCategoria> valoresPorCategoria = extratoPorCategoriaService
                .buscarValorTotalPorCategoriaEUsuarioNoPeriodo(periodo.getId(), luis.getId());

        assertNotNull(valoresPorCategoria);
        assertThat(valoresPorCategoria).hasSize(1);
        assertValorPorCategoria(valoresPorCategoria.get(0), "Mercado", 83.79);
    }

    private void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas() {
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas();
        findPeriodo();
    }

    @Test
    void dadoQuatroDespesasSendoUmaDivisivelUmaIndividualDuasIndicadas_BuscaResumoParaTodosUsuarios() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        Periodo periodo = findPeriodo();
        Pessoa luis = findPessoa("Luis");

        List<ValorPorCategoria> valoresPorCategoria = extratoPorCategoriaService
                .buscarValorTotalPorCategoriaEUsuarioNoPeriodo(periodo.getId(), luis.getId());

        assertNotNull(valoresPorCategoria);
        assertThat(valoresPorCategoria).hasSize(2);
        assertValorPorCategoria(valoresPorCategoria.get(0), "Mercado", 83.79);
        assertValorPorCategoria(valoresPorCategoria.get(1), "Casa", 326.18);
    }

    private static void assertValorPorCategoria(ValorPorCategoria valorPorCategoria1, String Mercado, double val) {
        assertThat(valorPorCategoria1.getCategoria().getNome()).isEqualTo(Mercado);
        assertThat(valorPorCategoria1.getValorTotal()).isCloseTo(valueOf(val), withPercentage(1));
    }

    private void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas() {
        environment.setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        findPeriodo();
    }

    private Periodo findPeriodo() {
        return periodoRepository.findAll().stream().findFirst().orElseThrow();
    }

    private Pessoa findPessoa(String nome) {
        return pessoaRepository.findByNome(nome).orElseThrow();
    }
}
