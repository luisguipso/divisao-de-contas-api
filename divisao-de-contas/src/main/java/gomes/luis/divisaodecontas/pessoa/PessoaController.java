package gomes.luis.divisaodecontas.pessoa;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity buscarTodasAsPessoas(){
        List<Pessoa> pessoas = pessoaService.buscarTodos();
        return new ResponseEntity(pessoas, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable(value = "id") Long id){
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
        if(pessoa.isPresent())
            return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity salvarPessoa(@RequestBody Pessoa pessoa){
        pessoaService.salvar(pessoa);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Pessoa> atualizarPessoa(@RequestBody Pessoa pessoaAtualizada, @PathVariable(value = "id") Long id){
        Optional<Pessoa> p = pessoaService.buscarPorId(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizada.getNome());
            return pessoaService.salvar(pessoa);
        });
        return new ResponseEntity<Pessoa>(p.get(),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> excluirPessoa(@PathVariable(value = "id") Long id){
        try{
            pessoaService.excluirPorId(id);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
