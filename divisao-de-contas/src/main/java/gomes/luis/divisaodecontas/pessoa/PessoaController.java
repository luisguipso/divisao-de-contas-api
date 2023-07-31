package gomes.luis.divisaodecontas.pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {

    public static final String PESSOA_CRIADA = "Pessoa criada.";
    public static final String PESSOA_ATUALIZADA = "Pessoa atualizada.";
    public static final String PESSOA_EXCLUIDA = "Pessoa excluida.";
    private PessoaService pessoaService;

    PessoaController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    @GetMapping()
    public ResponseEntity<List<Pessoa>> buscarTodasAsPessoas(){
        List<Pessoa> pessoas = pessoaService.buscarTodos();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable(value = "id") Long id){
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
        return pessoa
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<String> salvarPessoa(@RequestBody Pessoa pessoa){
        pessoaService.salvar(pessoa);
        return new ResponseEntity<>(PESSOA_CRIADA, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPessoa(@RequestBody Pessoa pessoa, @PathVariable(value = "id") Long id){
        pessoaService.atualizar(id, pessoa);
        return new ResponseEntity<>(PESSOA_ATUALIZADA,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> excluirPessoa(@PathVariable(value = "id") Long id){
        pessoaService.excluirPorId(id);
        return new ResponseEntity<>(PESSOA_EXCLUIDA, HttpStatus.OK);
    }
}
