package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespesaService extends GenericService<Despesa, Long> {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        super(despesaRepository);
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> buscarTodasAsDepesas() {
        return super.buscarTodos();
    }

    public Optional<Despesa> buscarDespesaPorId(Long id) {
        return super.buscarPorId(id);
    }

    public List<Despesa> buscarDespesasPorPeriodo(Periodo periodo) {
        return despesaRepository.findByPeriodo(periodo);
    }

    public Despesa salvarDespesa(Despesa despesa) {
        return super.salvar(despesa);
    }

    public Despesa atualizarDespesa(Long id, Despesa attDespesa) {
        return super.atualizar(id, attDespesa);
    }


    public List<Despesa> pagarDespesas(List<Despesa> despesas) {
        despesas.forEach(despesa -> pagarDespesa(despesa));
        return despesas;
    }

    public Despesa pagarDespesa(Despesa despesa) {
        despesa.setPago(true);
        return atualizarDespesa(despesa.getId(), despesa);
    }
}
