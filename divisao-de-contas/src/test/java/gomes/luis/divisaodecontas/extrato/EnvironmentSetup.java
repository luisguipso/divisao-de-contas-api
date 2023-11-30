package gomes.luis.divisaodecontas.extrato;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.categoria.CategoriaService;
import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.despesa.DespesaService;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoService;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

@Component
public class EnvironmentSetup {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private DespesaService despesaService;


    public void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEDuasDespesasIndicadas() {
        Pessoa luis = criarPessoa("Luis", 55);
        Pessoa cyntia = criarPessoa("Cyntia", 45);
        Periodo jan24 = criarPeriodo("jan-24", luis, cyntia);
        Categoria mercado = criarCategoria("Mercado");
        Categoria casa = criarCategoria("Casa");
        criarDespesaDivisivel("cancao", mercado, cyntia, jan24, 152.35); //luis: 83.79, cyntia: 68,5575
        criarDespesaIndividual("maquiagem", mercado, cyntia, jan24, 100.10); //cyntia 100.10
        criarDespesaIndicada("energia", casa, cyntia, jan24, 200.35, luis); //luis: 200.35
        criarDespesaIndicada("internet", casa, cyntia, jan24, 125.83, luis); //luis: 125.83
    }

    public void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididasEUmaDespesaIndicada() {

        Pessoa luis = criarPessoa("Luis", 55);
        Pessoa cyntia = criarPessoa("Cyntia", 45);
        Periodo jan24 = criarPeriodo("jan-24", luis, cyntia);
        Categoria mercado = criarCategoria("Mercado");
        Categoria casa = criarCategoria("Casa");
        criarDespesaDivisivel("cancao", mercado, cyntia, jan24, 152.35); //luis: 83.79, cyntia: 68,5575
        criarDespesaIndividual("maquiagem", mercado, cyntia, jan24, 100.10); //cyntia 100.10
        criarDespesaIndicada("cancao", casa, cyntia, jan24, 200.35, luis); //luis: 200.35
    }


    public void setupDuasPessoasDuasDespesasIndividuaisEDuasDivididas() {
        Pessoa luis = criarPessoa("Luis", 55);
        Pessoa cyntia = criarPessoa("Cyntia", 45);
        Periodo jan24 = criarPeriodo("jan-24", luis, cyntia);
        Categoria mercado = criarCategoria("Mercado");
        criarDespesaDivisivel("cancao", mercado, cyntia, jan24, 152.35);
        criarDespesaIndividual("maquiagem", mercado, cyntia, jan24, 100.10);
    }

    private void criarDespesaDivisivel(String descricao, Categoria categoria, Pessoa dono, Periodo periodo, double valor) {
        criarDespesa(descricao, categoria, dono, periodo, valor, true, null);
    }

    private void criarDespesaIndividual(String descricao, Categoria categoria, Pessoa dono, Periodo periodo, double valor) {
        criarDespesa(descricao, categoria, dono, periodo, valor, false, null);
    }

    private void criarDespesaIndicada(String descricao, Categoria categoria, Pessoa dono, Periodo periodo, double valor, Pessoa pagador) {
        criarDespesa(descricao, categoria, dono, periodo, valor, false, pagador);
    }
    private void criarDespesa(String descricao, Categoria categoria, Pessoa dono, Periodo periodo, double valor, boolean isDivisivel, Pessoa pagador) {
        despesaService.salvar(new Despesa(descricao,
                dono,
                isDivisivel,
                categoria,
                mock(Date.class),
                periodo,
                BigDecimal.valueOf(valor)
        , pagador));
    }

    private Pessoa criarPessoa(String nome, int percentual) {
        return pessoaService.salvar(new Pessoa(nome, percentual));
    }

    private Categoria criarCategoria(String nome) {
        Categoria c = new Categoria(nome);
        categoriaService.salvar(c);
        return c;
    }

    private Periodo criarPeriodo(String descricao, Pessoa... pessoas) {
        Periodo p = new Periodo(descricao);
        p.adicionarPagadores(List.of(pessoas));
        periodoService.salvar(p);
        return p;
    }
}
