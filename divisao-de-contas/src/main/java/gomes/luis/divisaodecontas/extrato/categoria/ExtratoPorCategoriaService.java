package gomes.luis.divisaodecontas.extrato.categoria;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoPorCategoriaService {
    
    private final ExtratoPorCategoriaRepository extratoPorCategoriaRepository;
    private final TupleToValorPorCategoria converter;
    public ExtratoPorCategoriaService(ExtratoPorCategoriaRepository extratoPorCategoriaRepository){
        this.extratoPorCategoriaRepository = extratoPorCategoriaRepository;
        this.converter = new TupleToValorPorCategoria();
    }

    public List<ValorPorCategoria> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId){

        List<Tuple> resultTuple = extratoPorCategoriaRepository.buscarValorTotalPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId);
        if (resultTuple.isEmpty())
            throw new EntityNotFoundException("Não existem dados para os parametros utilizados.");
        return resultTuple
                .stream()
                .map(converter::convert)
                .toList();
    }


}
