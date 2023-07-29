package gomes.luis.divisaodecontas.periodo;


import gomes.luis.divisaodecontas.pagamento.PagamentoService;
import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@EnableTransactionManagement
public class PeriodoService extends GenericService<Periodo, Long> {
    private final PagamentoService pagamentoService;

    public PeriodoService(PeriodoRepository repository, PagamentoService pagamentoService) {
        super(repository);
        this.pagamentoService = pagamentoService;
    }

    public List<Periodo> buscarTodosOsPeriodos() {
        List<Periodo> periodos = super.buscarTodos();
        // TODO SORT BY MES
        return periodos;
    }

    protected Periodo fecharPeriodo(Periodo periodo, BigDecimal valorTotal){
        periodo.setFechado(true);
        periodo.setFim(new Date());
        periodo.setValorTotal(valorTotal);
        atualizar(periodo.getId(), periodo);
        return periodo;
    }
}
