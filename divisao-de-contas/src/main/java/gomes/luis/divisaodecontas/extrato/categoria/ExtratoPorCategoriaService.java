package gomes.luis.divisaodecontas.extrato.categoria;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoPorCategoriaService {
    
    private final ExtratoPorCategoriaRepository repository;

    public ExtratoPorCategoriaService(ExtratoPorCategoriaRepository extratoPorCategoriaRepository){
        this.repository = extratoPorCategoriaRepository;
    }

    public List<ValorPorCategoria> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId){
        List<ValorPorCategoria> despesasDivisiveis = repository.buscarValorDevidoDeDespesasDivisiveisPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId);
        List<ValorPorCategoria> despesasIndividuais = repository.buscarValorDevidoDeDespesasIndividuaisPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId);
        List<ValorPorCategoria> despesasIndicadas = repository.buscarValorDevidoDeDespesasIndicadasPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId);

        despesasDivisiveis.addAll(despesasIndividuais);
        despesasDivisiveis.addAll(despesasIndicadas);
        return despesasDivisiveis;
    }


}
