package gomes.luis.divisaodecontas.periodo;


import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.List;

@Service
@EnableTransactionManagement
public class PeriodoService extends GenericService<Periodo, Long> {

    public PeriodoService(PeriodoRepository repository) {
        super(repository);
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
}
