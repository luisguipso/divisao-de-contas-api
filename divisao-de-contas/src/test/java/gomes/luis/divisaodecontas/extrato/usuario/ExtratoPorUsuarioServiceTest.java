package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class ExtratoPorUsuarioServiceTest {

    ExtratoPorUsuarioService service;
    ExtratoPorUsuarioRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(ExtratoPorUsuarioRepository.class);
        service = new ExtratoPorUsuarioService(repository);
    }

    @Test
    void dadoDespesasDivisiveisEIndividuaisNoPeriodo_buscarValorDevidoPorUsuarioNoPeriodo() {
        Pessoa luis = new Pessoa(1L, "Luis", 55);
        Pessoa cyntia = new Pessoa(2L, "Cyntia", 45);

        ArrayList<ValorPorUsuario> valoresDivididos = listOf(
                new ValorPorUsuario(luis, valueOf(55.00)),
                new ValorPorUsuario(cyntia, valueOf(45.00)));
        doReturn(valoresDivididos)
                .when(repository).buscarValoresDividosDevidosPorUsuarioNoPeriodo(1L);
        List<ValorPorUsuario> valoresIndividuais = listOf(
                new ValorPorUsuario(luis, valueOf(300)),
                new ValorPorUsuario(cyntia, valueOf(100)));
        doReturn(valoresIndividuais)
                .when(repository).buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(1L);

        List<ValorPorUsuario> actual = service.buscarValorDevidoPorUsuarioNoPeriodo(1L);

        assertThat(actual).hasSize(2);
        assertValorPorUsuario(actual.get(0), "Luis", 355.0);
        assertValorPorUsuario(actual.get(1), "Cyntia", 145.0);
    }

    @Test
    void dadoUsuariosDespesasDivisiveisEApenasUmComDespesasIndividuaisNoPeriodo_buscarValorDevidoPorUsuarioNoPeriodo() {
        Pessoa luis = new Pessoa(1L, "Luis", 55);
        Pessoa cyntia = new Pessoa(2L, "Cyntia", 45);

        ArrayList<ValorPorUsuario> valoresDivididos = listOf(
                new ValorPorUsuario(luis, valueOf(55.00)),
                new ValorPorUsuario(cyntia, valueOf(45.00)));
        doReturn(valoresDivididos)
                .when(repository).buscarValoresDividosDevidosPorUsuarioNoPeriodo(1L);
        List<ValorPorUsuario> valoresIndividuais = listOf(new ValorPorUsuario(cyntia, valueOf(100)));
        doReturn(valoresIndividuais)
                .when(repository).buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(1L);

        List<ValorPorUsuario> actual = service.buscarValorDevidoPorUsuarioNoPeriodo(1L);

        assertThat(actual).hasSize(2);
        assertValorPorUsuario(actual.get(0), "Luis", 55.0);
        assertValorPorUsuario(actual.get(1), "Cyntia", 145.0);
    }

    @Test
    void dadoUsuariosComApenasDespesasDivisiveis_buscarValorDevidoPorUsuarioNoPeriodo() {
        Pessoa luis = new Pessoa(1L, "Luis", 55);
        Pessoa cyntia = new Pessoa(2L, "Cyntia", 45);

        ArrayList<ValorPorUsuario> valoresDivididos = listOf(
                new ValorPorUsuario(luis, valueOf(55.00)),
                new ValorPorUsuario(cyntia, valueOf(45.00)));
        doReturn(valoresDivididos)
                .when(repository).buscarValoresDividosDevidosPorUsuarioNoPeriodo(1L);
        doReturn(emptyList())
                .when(repository).buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(1L);

        List<ValorPorUsuario> valoresPorUsuario = service.buscarValorDevidoPorUsuarioNoPeriodo(1L);

        assertThat(valoresPorUsuario).hasSize(2);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", 55.0);
        assertValorPorUsuario(valoresPorUsuario.get(1), "Cyntia", 45.0);
    }

    @Test
    void dadoUsuariosComApenasDespesasIndividuais_buscarValorDevidoPorUsuarioNoPeriodo() {
        Pessoa luis = new Pessoa(1L, "Luis", 55);
        Pessoa cyntia = new Pessoa(2L, "Cyntia", 45);

        doReturn(emptyList())
                .when(repository).buscarValoresDividosDevidosPorUsuarioNoPeriodo(1L);
        ArrayList<ValorPorUsuario> valoresIndividuais = listOf(
                new ValorPorUsuario(luis, valueOf(1000.00)),
                new ValorPorUsuario(cyntia, valueOf(45.00)));
        doReturn(valoresIndividuais)
                .when(repository).buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(1L);

        List<ValorPorUsuario> valoresPorUsuario = service.buscarValorDevidoPorUsuarioNoPeriodo(1L);

        assertThat(valoresPorUsuario).hasSize(2);
        assertValorPorUsuario(valoresPorUsuario.get(0), "Luis", 1000.0);
        assertValorPorUsuario(valoresPorUsuario.get(1), "Cyntia", 45.0);
    }

    private static void assertValorPorUsuario(ValorPorUsuario valorPorUsuario, String nome, double val) {
        assertThat(valorPorUsuario.getUsuario().getNome()).isEqualTo(nome);
        assertThat(valorPorUsuario.getValorTotal()).isEqualTo(valueOf(val));
    }

    private ArrayList<ValorPorUsuario> listOf(ValorPorUsuario... valorPorUsuarios) {
        return new ArrayList<>(List.of(valorPorUsuarios));
    }

    private static ArrayList<Object> emptyList() {
        return new ArrayList<>();
    }

}
