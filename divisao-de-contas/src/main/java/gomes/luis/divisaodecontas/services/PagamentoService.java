package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Pagamento;
import gomes.luis.divisaodecontas.models.Pessoa;
import gomes.luis.divisaodecontas.repositories.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PagamentoService extends GenericService<Pagamento, Long> {
    private PagamentoRepository pagamentoRepository;


    public PagamentoService(PagamentoRepository repository) {
        super(repository);
        pagamentoRepository = repository;
    }

    public List<Pagamento> buscarPagamentosPorDespesa(Despesa despesa) {
        return pagamentoRepository.getByDespesa(despesa);
    }

    public List<Pagamento> criarPagamentos(List<Despesa> despesas){
        List<Pagamento> gerados = gerarPagamentos(despesas);
        List<Pagamento> salvos = salvar(gerados);
        return salvos;
    }

    private List<Pagamento> salvar(List<Pagamento> pagamentos) {
        return pagamentos.stream()
                .map(pagamento -> super.salvar(pagamento))
                .collect(Collectors.toList());
    }

    private List<Pagamento> gerarPagamentos(List<Despesa> despesas) {
        List<Pagamento> pagamentos = despesas
                .stream()
                .map(despesa -> gerarPagamentos(despesa))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return pagamentos;
    }

    public List<Pagamento> gerarPagamentos(Despesa despesa) {
        BigDecimal valor = calculaValorDoPagamentoPorPagador(despesa);
        List<Pessoa> pagadores = despesa.getPagadores();
        return criarPagamentos(despesa, pagadores, valor);
    }

    private BigDecimal calculaValorDoPagamentoPorPagador(Despesa despesa) {
        BigDecimal valor = despesa.getValor();
        int quantidadeDePagadores = despesa.getPagadores().size();
        valor = valor.divide(BigDecimal.valueOf(quantidadeDePagadores));
        return valor;
    }

    private List<Pagamento> criarPagamentos(Despesa despesa, List<Pessoa> pagadores, BigDecimal valor) {
        List<Pagamento> pagamentos = pagadores.stream()
                .map(pagador -> criarPagamento(despesa, pagador, valor))
                .collect(Collectors.toList());
        return pagamentos;
    }

    private Pagamento criarPagamento(Despesa despesa, Pessoa pagador, BigDecimal valor) {
        return new Pagamento(despesa, pagador, new Date(), valor);
    }

}
