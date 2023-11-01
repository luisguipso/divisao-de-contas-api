package gomes.luis.divisaodecontas.extrato.usuario;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoPorUsuarioService {

    private final ExtratoPorUsuarioRepository extratoPorUsuarioRepository;

    public ExtratoPorUsuarioService(ExtratoPorUsuarioRepository extratoPorUsuarioRepository){
        this.extratoPorUsuarioRepository = extratoPorUsuarioRepository;
    }
    public List<ValorPorUsuario> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId) {
        return extratoPorUsuarioRepository.buscarValorPagoPorUsuarioNoPeriodo(periodoId);
    }

    public List<ValorPorUsuario> buscarValorDevidoPorUsuarioNoPeriodo(Long periodoId){
        TupleToValorPorUsuario converter = new TupleToValorPorUsuario();
        return extratoPorUsuarioRepository.buscarValorDevidoPorUsuarioNoPeriodo(periodoId)
                .stream()
                .map(converter::convert)
                .toList();
    }


}
