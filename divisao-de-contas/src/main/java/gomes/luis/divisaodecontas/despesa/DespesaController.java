package gomes.luis.divisaodecontas.despesa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/despesas")
public class DespesaController {
    public static final String DESPESA_CRIADA = "Despesa criada.";
    public static final String DESPESA_ATUALIZADA = "Despesa atualizada.";
    public static final String DESPESA_EXCLUIDA = "Despesa excluida.";

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService){
        this.despesaService = despesaService;
    }

    @GetMapping()
    public ResponseEntity<List<Despesa>> buscarTodasAsDepesas(){
        List<Despesa> despesas = despesaService.buscarTodasAsDepesas();
        return new ResponseEntity<>(despesas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarDespesaPorId(@PathVariable(name = "id") Long id){
        return despesaService.buscarPorId(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<String> salvarDespesa(@RequestBody Despesa despesa){
        despesaService.salvarDespesa(despesa);
        return new ResponseEntity<>(DESPESA_CRIADA,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarDespesa(@PathVariable Long id, @RequestBody Despesa despesa){
        despesaService.atualizarDespesa(id, despesa);
        return new ResponseEntity<>(DESPESA_ATUALIZADA,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirDespesa(@PathVariable Long id){
        despesaService.excluirPorId(id);
        return new ResponseEntity<>(DESPESA_EXCLUIDA,HttpStatus.OK);
    }
}
