package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.categoria.CategoriaRepository;
import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.despesa.DespesaRepository;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoRepository;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class EnvironmentSetup {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private DespesaRepository despesaRepository;

    void setup(){
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
