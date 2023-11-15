package gomes.luis.divisaodecontas.repositories;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.categoria.CategoriaRepository;
import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.despesa.DespesaRepository;
import gomes.luis.divisaodecontas.extrato.usuario.ExtratoPorUsuarioRepository;
import gomes.luis.divisaodecontas.extrato.usuario.TupleToValorPorUsuario;
import gomes.luis.divisaodecontas.extrato.usuario.ValorPorUsuario;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaRepository;
import jakarta.persistence.Tuple;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExtratoPorUsuarioRepositoryTest {
    @Autowired
    private ExtratoPorUsuarioRepository extratoPorUsuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private DespesaRepository despesaRepository;


    @Test
    @Transactional
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasDivisiveis() {
        setup();
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<Tuple> tuples = extratoPorUsuarioRepository.buscarValorDevidoDasDespesasDivisivelPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(tuples);
        assertThat(tuples).hasSize(2);

        TupleToValorPorUsuario converter = new TupleToValorPorUsuario();
        ValorPorUsuario valorPorUsuario1 = converter.convert(tuples.get(0));
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(83.79), Percentage.withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = converter.convert(tuples.get(1));
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(BigDecimal.valueOf(68.56), Percentage.withPercentage(1));
    }

    @Test
    @Transactional
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaDespesasNaoDivisiveis() {
        setup();
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<Tuple> tuples = extratoPorUsuarioRepository.buscarValorDevidoDasDespesasNaoDivisivelPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(tuples);
        assertThat(tuples).hasSize(1);

        TupleToValorPorUsuario converter = new TupleToValorPorUsuario();
        ValorPorUsuario valorPorUsuario1 = converter.convert(tuples.get(0));
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(100), Percentage.withPercentage(1));
    }

    @Test()
    @Transactional
    void dadoDuasDespesasSendoUmaDivisivelEOutraIndividual_BuscaResumoParaTodosUsuarios() {
        setup();
        Periodo periodo = periodoRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(periodo.getId());

        List<Tuple> tuples = extratoPorUsuarioRepository.buscarValorDevidoPorUsuarioNoPeriodo(periodo.getId());
        assertNotNull(tuples);
        assertThat(tuples).hasSize(2);

        TupleToValorPorUsuario converter = new TupleToValorPorUsuario();
        ValorPorUsuario valorPorUsuario1 = converter.convert(tuples.get(0));
        assertThat(valorPorUsuario1.getUsuario().getNome()).isEqualTo("Luis");
        assertThat(valorPorUsuario1.getValorTotal()).isCloseTo(BigDecimal.valueOf(83.79), Percentage.withPercentage(1));
        ValorPorUsuario valorPorUsuario2 = converter.convert(tuples.get(1));
        assertThat(valorPorUsuario2.getUsuario().getNome()).isEqualTo("Cyntia");
        assertThat(valorPorUsuario2.getValorTotal()).isCloseTo(BigDecimal.valueOf(168.65), Percentage.withPercentage(1));
    }

    private void setup() {
        Pessoa luis = new Pessoa("Luis", 55);
        Pessoa cyntia = new Pessoa("Cyntia", 45);
        pessoaRepository.saveAll(List.of(luis, cyntia));
        Periodo jan24 = new Periodo("jan-24");
        jan24.adicionarPagadores(List.of(luis, cyntia));
        periodoRepository.save(jan24);
        Categoria mercado = new Categoria("Mercado");
        categoriaRepository.save(mercado);
        Despesa cancao21jan = new Despesa("cancao",
                cyntia,
                true,
                mercado,
                new Date(2024, Calendar.JANUARY, 21),
                jan24,
                BigDecimal.valueOf(152.35),
                false);
        despesaRepository.save(cancao21jan);
        Despesa maquiagem = new Despesa("maquiagem",
                cyntia,
                false,
                mercado,
                new Date(2024, Calendar.JANUARY, 22),
                jan24,
                BigDecimal.valueOf(100.10),
                false);
        despesaRepository.save(maquiagem);
    }

}
