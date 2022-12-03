package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Periodo;
import gomes.luis.divisaodecontas.models.Pessoa;
import gomes.luis.divisaodecontas.repositories.DespesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService extends GenericService<Despesa, Long> {

    private final DespesaRepository despesaRepository;

    DespesaService(DespesaRepository despesaRepository) {
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
        setPagadoresPadrao(despesa);
        validarDespesaAntesDeSalvar(despesa);
        return super.salvar(despesa);
    }

    private void setPagadoresPadrao(Despesa despesa) {
        if(despesa.getPagadores() == null || despesa.getPagadores().isEmpty())
            setPagadores(despesa);
    }

    private void setPagadores(Despesa despesa) {
        despesa.getPagadores().addAll(getPossiveisPagadoresParaDespesa(despesa));
    }

    public List<Pessoa> getPossiveisPagadoresParaDespesa(Despesa despesa) {
        if(despesa.isDivisivel()) {
            return despesa.getPeriodo().getPagadores();
        }
        return List.of(despesa.getDono());
    }

    private void validarDespesaAntesDeSalvar(Despesa despesa) {
        //TODO
    }

    public Despesa atualizarDespesa(Long id, Despesa attDespesa) {
        setPagadoresPadrao(attDespesa);
        //TODO -validar que não esta alterando valor periodo ou data de despesa paga
        validarDespesaAntesDeSalvar(attDespesa);
        return super.atualizar(id, attDespesa);
    }

    public void excluirDespesa(Despesa despesa) {
        //TODO - validar que não está excluindo despesa paga
        super.excluirPorId(despesa.getId());
    }

    public List<Despesa> pagarDespesas(List<Despesa> despesas) {
        return despesas
                .stream()
                .map(despesa -> pagarDespesa(despesa))
                .collect(Collectors.toList());
    }

    public Despesa pagarDespesa(Despesa despesa) {
        despesa.setPago(true);
        return atualizarDespesa(despesa.getId(), despesa);
    }
}
