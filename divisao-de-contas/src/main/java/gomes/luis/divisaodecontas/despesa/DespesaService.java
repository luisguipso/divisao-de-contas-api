package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.periodo.PeriodoService;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService extends GenericService<Despesa, Long> {

    private final DespesaRepository despesaRepository;
    private final PeriodoService periodoService;

    public DespesaService(DespesaRepository despesaRepository, PeriodoService periodoService) {
        super(despesaRepository);
        this.despesaRepository = despesaRepository;
        this.periodoService = periodoService;
    }

    public List<Despesa> buscarTodasAsDepesas() {
        return super.buscarTodos();
    }

    public Optional<Despesa> buscarDespesaPorId(Long id) {
        return super.buscarPorId(id);
    }

    public List<Despesa> buscarDespesasPorPeriodo(Long periodoId) {
        Periodo periodoParaBusca = new Periodo();
        periodoParaBusca.setId(periodoId);
        return despesaRepository.findByPeriodo(periodoParaBusca);
    }

    public Despesa salvarDespesa(Despesa despesa) {
        Despesa despesaSalva = super.salvar(despesa);
        atualizarValorDoPeriodo(despesa.getPeriodo().getId());
        return despesaSalva;
    }

    private void atualizarValorDoPeriodo(Long periodoId) {
        BigDecimal valorTotal = getValorTotalNoPeriodo(periodoId);
        periodoService.atualizarValorPeriodo(periodoId, valorTotal);
    }

    private BigDecimal getValorTotalNoPeriodo(Long periodoId) {
        return despesaRepository.sumValorByPeriodoId(periodoId);
    }

    public Despesa atualizarDespesa(Long id, Despesa attDespesa) {
        Despesa despesaAtualizada = super.atualizar(id, attDespesa);
        atualizarValorDoPeriodo(despesaAtualizada.getPeriodo().getId());
        return despesaAtualizada;
    }


    public List<Despesa> pagarDespesas(List<Despesa> despesas) {
        despesas.forEach(this::pagarDespesa);
        return despesas;
    }

    public Despesa pagarDespesa(Despesa despesa) {
        despesa.setPago(true);
        return atualizarDespesa(despesa.getId(), despesa);
    }

//    public List<Despesa> buscarDespesasPorUsuarioNoPeriodo(Long periodoId) {
//        Periodo periodo = periodoService.buscarPorId(periodoId).orElseThrow();
//        periodo.getPagadores().stream()
//                .map(usuario -> {
//                    getTotalPorUsuarioDTO(periodo, usuario);
//                });
//        return ;
//    }
//
//    private void getTotalPorUsuarioDTO(Periodo periodo, Pessoa usuario) {
//        TotalPorUsuarioNoPeriodoDto totalUsuario = new TotalPorUsuarioNoPeriodoDto();
//        totalUsuario.setPeriodo(periodo);
//        totalUsuario.setPessoa(usuario);
//        totalUsuario.setValorTotal(despesaRepository.sumByPeriodoAndPagador(periodo.getId(), usuario.getId()));
//        ;
//    }
}
