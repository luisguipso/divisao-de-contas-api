package gomes.luis.divisaodecontas.extrato.usuario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtratoPorUsuarioService {

    private final ExtratoPorUsuarioRepository repository;

    public ExtratoPorUsuarioService(ExtratoPorUsuarioRepository repository){
        this.repository = repository;
    }
    public List<ValorPorUsuario> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId) {
        return repository.buscarValorPagoPorUsuarioNoPeriodo(periodoId);
    }

    public List<ValorPorUsuario> buscarValorDevidoPorUsuarioNoPeriodo(Long periodoId){
        List<ValorPorUsuario> valoresDividosDevidos = repository.buscarValoresDividosDevidosPorUsuarioNoPeriodo(periodoId);
        List<ValorPorUsuario> valoresIndividuaisDevidos = repository.buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(periodoId);
        List<ValorPorUsuario> reduce = new ArrayList<>();
        for(ValorPorUsuario divido : valoresDividosDevidos){
            for(ValorPorUsuario individual : valoresIndividuaisDevidos){
                if(divido.getUsuario().equals(individual.getUsuario()))
                    divido.setValorTotal(divido.getValorTotal().add(individual.getValorTotal()));
                reduce.add(divido);
            }
        }
        return reduce;
    }


}
