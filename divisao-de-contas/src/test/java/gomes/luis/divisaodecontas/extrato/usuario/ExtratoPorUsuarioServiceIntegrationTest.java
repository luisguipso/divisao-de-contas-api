package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.extrato.EnvironmentSetup;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
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
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", 83.79);
        assertValorPorUsuario(valoresPorUsuario.get(1), "Cyntia", 168.65);
    }

    @Test
    void dadoQuatroDespesasSendoUmaDivisivelUmaIndividualDuasIndicadas_BuscaResumoParaTodosUsuarios() {
        setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas();
        assertNotNull(periodo.getId());

        List<ValorPorUsuario> valoresPorUsuario = extratoPorUsuarioService.buscarValorDevidoPorUsuarioNoPeriodo(periodo.getId());
        
        assertNotNull(valoresPorUsuario);
        assertThat(valoresPorUsuario).hasSize(2);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", 409.97);
        assertValorPorUsuario(valoresPorUsuario.get(1), "Cyntia", 168.65);
    }
    
    private static void assertValorPorUsuario(ValorPorUsuario valorPorUsuario2, String Cyntia, double val) {
        assertNotNull(valorPorUsuario2);
        Pessoa usuario = valorPorUsuario2.getUsuario();
        assertNotNull(usuario);
        assertThat(usuario.getNome()).isEqualTo(Cyntia);
        BigDecimal valorTotal = valorPorUsuario2.getValorTotal();
        assertNotNull(valorTotal);
        assertThat(valorTotal).isCloseTo(valueOf(val), withPercentage(1));
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
