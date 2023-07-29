package gomes.luis.divisaodecontas.periodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/periodo")
public class PeriodoControler {

    public static final String PERIODO_CRIADO = "Periodo criado.";
    public static final String PERIODO_EXCLUIDO = "Periodo excluido.";
    public static final String PERIODO_ATUALIZADO = "Periodo atualizado.";
    @Autowired
    PeriodoService periodoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity buscarTodosOsPeriodos(){
        List<Periodo> periodos = periodoService.buscarTodosOsPeriodos();
        return new ResponseEntity<List<Periodo>>(periodos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity buscarPeriodoPorId(@PathVariable(name = "id") Long id){
        Optional<Periodo> periodo = periodoService.buscarPorId(id);
        return new ResponseEntity<Periodo>(periodo.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity salvarPeriodo(@RequestBody Periodo periodo){
        periodoService.salvar(periodo);
        return new ResponseEntity<>(PERIODO_CRIADO,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity atualizarPeriodo(@PathVariable Long id, @RequestBody Periodo periodo){
        periodoService.atualizar(id, periodo);
        return new ResponseEntity(PERIODO_ATUALIZADO,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity excluirPeriodo(@PathVariable Long id){
        periodoService.excluirPorId(id);
        return new ResponseEntity(PERIODO_EXCLUIDO,HttpStatus.OK);
    }

}
