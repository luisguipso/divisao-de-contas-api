package gomes.luis.divisaodecontas.extrato.categoria;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoPorCategoriaService {

    private final ExtratoPorCategoriaRepository extratoPorCategoriaRepository;

    public ExtratoPorCategoriaService(ExtratoPorCategoriaRepository extratoPorCategoriaRepository){
        this.extratoPorCategoriaRepository = extratoPorCategoriaRepository;
    }
    public List<ValorPorCategoria> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId) {
        return extratoPorCategoriaRepository.buscarValorPagoPorUsuarioNoPeriodo(periodoId);
    }

    public List<ValorPorCategoria> buscarValorDevidoPorUsuarioNoPeriodo(Long periodoId){
        TupleToValorPorCategoria converter = new TupleToValorPorCategoria();
        return extratoPorCategoriaRepository.buscarValorDevidoPorUsuarioNoPeriodo(periodoId)
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<ValorPorCategoria> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId){
        TupleToValorPorCategoria converter = new TupleToValorPorCategoria();
        return extratoPorCategoriaRepository.buscarValorTotalPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId)
                .stream()
                .map(converter::convert)
                .toList();
    }


}
