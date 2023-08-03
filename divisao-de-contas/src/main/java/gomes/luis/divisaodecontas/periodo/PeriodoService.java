package gomes.luis.divisaodecontas.periodo;


import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.List;

@Service
@EnableTransactionManagement
public class PeriodoService extends GenericService<Periodo, Long> {

    PeriodoRepository repository;
    public PeriodoService(PeriodoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Periodo> buscarTodosOsPeriodos(int pagina, int tamanho) {
        Pageable paginavel = PageRequest.of(pagina, tamanho, Sort.by("id").descending());
        List<Periodo> periodos = repository.findAll(paginavel).getContent();
        // TODO SORT BY MES
        return periodos;
    }

    public void atualizarValorPeriodo(Long id, BigDecimal valor) {
        Periodo periodo = this.buscarPorId(id).orElseThrow();
        periodo.setValorTotal(valor);
        this.salvar(periodo);
    }
}
