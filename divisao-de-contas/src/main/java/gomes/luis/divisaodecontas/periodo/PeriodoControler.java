package gomes.luis.divisaodecontas.periodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/periodos")
public class PeriodoControler {

    public static final String PERIODO_CRIADO = "Periodo criado.";
    public static final String PERIODO_EXCLUIDO = "Periodo excluido.";
    public static final String PERIODO_ATUALIZADO = "Periodo atualizado.";
    @Autowired
    PeriodoService periodoService;

    @GetMapping
    public ResponseEntity<List<Periodo>> buscarTodosOsPeriodos(){
        List<Periodo> periodos = periodoService.buscarTodosOsPeriodos();
        return new ResponseEntity<>(periodos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Periodo> buscarPeriodoPorId(@PathVariable(name = "id") Long id){
        Optional<Periodo> periodo = periodoService.buscarPorId(id);
        return new ResponseEntity<>(periodo.orElse(null), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> salvarPeriodo(@RequestBody Periodo periodo){
        periodoService.salvar(periodo);
        return new ResponseEntity<>(PERIODO_CRIADO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPeriodo(@PathVariable Long id, @RequestBody Periodo periodo){
        periodoService.atualizar(id, periodo);
        return new ResponseEntity<>(PERIODO_ATUALIZADO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPeriodo(@PathVariable Long id){
        periodoService.excluirPorId(id);
        return new ResponseEntity<>(PERIODO_EXCLUIDO,HttpStatus.OK);
    }
}
