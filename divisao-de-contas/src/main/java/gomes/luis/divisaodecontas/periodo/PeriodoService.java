package gomes.luis.divisaodecontas.periodo;


import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.despesa.DespesaService;
import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.List;

@Service
@EnableTransactionManagement
public class PeriodoService extends GenericService<Periodo, Long> {

    private final DespesaService despesaService;
    public PeriodoService(PeriodoRepository repository, DespesaService despesaService) {
        super(repository);
        this.despesaService = despesaService;
    }

    public List<Periodo> buscarTodosOsPeriodos() {
        List<Periodo> periodos = super.buscarTodos();
        // TODO SORT BY MES
        return periodos;
    }

    protected Periodo fecharPeriodo(Periodo periodo, BigDecimal valorTotal){
        periodo.setFechado(true);
        periodo.setValorTotal(valorTotal);
        atualizar(periodo.getId(), periodo);
        return periodo;
    }

    public BigDecimal calcularValorTotal(Periodo periodo) {
        List<Despesa> despesasDoPeriodo = this.despesaService.buscarDespesasPorPeriodo(periodo.getId());
        return despesasDoPeriodo.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
    }
}
