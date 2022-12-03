package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Pagamento;
import gomes.luis.divisaodecontas.models.Periodo;
import gomes.luis.divisaodecontas.repositories.PeriodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class PeriodoService extends GenericService<Periodo, Long> {
    private static final String VALOR_PAGAMENTOS_DIFERENTE_VALOR_DESPESAS = "O valor dos pagamentos gerados é diferente do valor total das depesas do periodo!";
    public static final String NEM_TODAS_AS_DESPESAS_DO_PERIODO_ESTAO_PAGAS = "Nem todas as despesas do periodo estão pagas!";
    DespesaService despesaService;
    PagamentoService pagamentoService;
    PessoaService pessoaService;

    public PeriodoService(PeriodoRepository repository, DespesaService despesaService, PagamentoService pagamentoService,PessoaService pessoaService) {
        super(repository);
        this.despesaService = despesaService;
        this.pessoaService = pessoaService;
        this.pagamentoService = pagamentoService;
    }

    public List<Periodo> buscarTodosOsPeriodos() {
        List<Periodo> periodos = super.buscarTodos();
        // TODO SORT BY MES
        return periodos;
    }
    @Override
    public Periodo salvar(Periodo periodo) {
        //TODO VALIDAR PERIODO
        return super.salvar(periodo);
    }

    public Periodo atualizar(Long id, Periodo periodo) {
        //validarPeriodo(periodo);
        return super.atualizar(id, periodo);
    }

    private void validarPeriodo(Periodo periodo) {
        validarPagamentos(periodo);
    }

    @Transactional
    public Periodo fecharPeriodo(Long id) {
        return fecharPeriodo(buscarPorId(id).get());
    }


    protected Periodo fecharPeriodo(Periodo periodo){
        pagarDespesas(periodo);
        atualizarAtributosParaFechar(periodo);
        atualizar(periodo.getId(), periodo);
        validarPeriodo(periodo);
        return periodo;
    }

    public void pagarDespesas(Periodo periodo) {
        List<Despesa> despesas = buscarDespesasPorPeriodo(periodo);
        criarPagamentos(despesas);
        despesaService.pagarDespesas(despesas);
    }

    private List<Pagamento> buscarPagamentosPorPeriodo(Periodo periodo) {
        return buscarDespesasPorPeriodo(periodo)
                .stream()
                .map(despesa -> pagamentoService.buscarPagamentosPorDespesa(despesa))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Despesa> buscarDespesasPorPeriodo(Periodo periodo){
        return despesaService.buscarDespesasPorPeriodo(periodo);
    }

    private List<Pagamento> criarPagamentos(List<Despesa> despesas) {
        return pagamentoService.criarPagamentos(despesas);
    }

    protected Periodo atualizarAtributosParaFechar(Periodo periodo) {
        periodo.setFechado(true);
        periodo.setFim(new Date());
        periodo.setValorTotal(calcularValorTotal(periodo));
        return periodo;
    }

    public BigDecimal calcularValorTotal(Periodo periodo) {
        BigDecimal valor = new BigDecimal(0);
        valor = valor.add(somarValorTotalDasDespesasDoPeriodo(periodo));
        return valor;
    }

    private BigDecimal somarValorTotalDasDespesasDoPeriodo(Periodo periodo) {
        BigDecimal valor = new BigDecimal(0);
        for (Despesa cada : buscarDespesasPorPeriodo(periodo)){
            if(cada.getValor() != null)
                valor = valor.add(cada.getValor());
        }
        return valor;
    }

    private void validarPagamentos(Periodo periodo) {
        List<Pagamento> pagamentosGerados = buscarPagamentosPorPeriodo(periodo);
        BigDecimal valorTotalPagamentos = somarValorTotalDosPagamentosGerados(pagamentosGerados);
        BigDecimal valorTotalDespesas = somarValorTotalDasDespesasDoPeriodo(periodo);
        lancarExcecaoSeValoresForemDiferentes(valorTotalPagamentos, valorTotalDespesas);
        lancarExececaoSeHouverAlgumaDespesaEmAberto(periodo);
    }

    private static void lancarExcecaoSeValoresForemDiferentes(BigDecimal valorTotalPagamentos, BigDecimal valorTotalDespesas) {
        if(!valorTotalPagamentos.equals(valorTotalDespesas)){
            throw new RollbackException(VALOR_PAGAMENTOS_DIFERENTE_VALOR_DESPESAS);
        }
    }

    private void lancarExececaoSeHouverAlgumaDespesaEmAberto(Periodo periodo) {
        if(!todasAsDespesasEstaoPagas(periodo)){
         throw new RollbackException(NEM_TODAS_AS_DESPESAS_DO_PERIODO_ESTAO_PAGAS);
        }
    }

    private static BigDecimal somarValorTotalDosPagamentosGerados(List<Pagamento> pagamentosGerados) {
        return pagamentosGerados
                .stream()
                .map(pagamento -> pagamento.getValor()).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean todasAsDespesasEstaoPagas(Periodo periodo) {
        return buscarDespesasPorPeriodo(periodo)
                .stream()
                .filter(despesa -> !despesa.isPago())
                .collect(Collectors.toList()).isEmpty();
    }
}
