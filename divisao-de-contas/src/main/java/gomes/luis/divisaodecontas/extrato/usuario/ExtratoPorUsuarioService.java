package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExtratoPorUsuarioService {

    private final ExtratoPorUsuarioRepository repository;

    public ExtratoPorUsuarioService(ExtratoPorUsuarioRepository repository) {
        this.repository = repository;
    }

    public List<ValorPorUsuario> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId) {
        return repository.buscarValorPagoPorUsuarioNoPeriodo(periodoId);
    }

    public List<ValorPorUsuario> buscarValorDevidoPorUsuarioNoPeriodo(Long periodoId) {
        List<ValorPorUsuario> valoresDividos = repository.buscarValoresDividosDevidosPorUsuarioNoPeriodo(periodoId);
        List<ValorPorUsuario> valoresIndividuais = repository.buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodoId);
        valoresDividos.addAll(valoresIndividuais);
        Map<Pessoa, ValorPorUsuario> valoresDevidos = new TreeMap<>();
        valoresDividos.forEach(valor -> agruparValoresPorUsuario(valoresDevidos, valor)
        );

        return new ArrayList<>(valoresDevidos.values());
    }

    private void agruparValoresPorUsuario(Map<Pessoa, ValorPorUsuario> valoresAgrupados, ValorPorUsuario novoValor) {
        ValorPorUsuario valorPorUsuarioAgrupado = valoresAgrupados.get(novoValor.getUsuario());
        if (valorPorUsuarioAgrupado == null)
            guardarValor(valoresAgrupados, novoValor);
        else
            agrupaESomaValoresDoUsuario(novoValor, valorPorUsuarioAgrupado);
    }

    private static void guardarValor(Map<Pessoa, ValorPorUsuario> valoresDevidos, ValorPorUsuario valor) {
        valoresDevidos.put(valor.getUsuario(), valor);
    }

    private static void agrupaESomaValoresDoUsuario(ValorPorUsuario novoValor, ValorPorUsuario valorPorUsuarioAgrupado) {
        BigDecimal somaDosValores = valorPorUsuarioAgrupado.getValorTotal().add(novoValor.getValorTotal());
        valorPorUsuarioAgrupado.setValorTotal(somaDosValores);
    }
}
