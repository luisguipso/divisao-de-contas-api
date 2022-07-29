package gomes.luis.divisaodecontas.controllers;

import gomes.luis.divisaodecontas.models.Pessoa;
import gomes.luis.divisaodecontas.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @RequestMapping(value = "/pessoa", method = RequestMethod.GET)
    public List<Pessoa> getAll(){
        return pessoaRepository.findAll();
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> getById(@PathVariable(value = "id") Long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isPresent())
            return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoa", method = RequestMethod.POST)
    public Pessoa post(@RequestBody Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
    public Pessoa update(@RequestBody Pessoa pessoaAtualizada, @PathVariable(value = "id") Long id){
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizada.getNome());
            return pessoaRepository.save(pessoa);
        }).orElseGet(() -> {
            pessoaAtualizada.setId(id);
            return pessoaRepository.save(pessoaAtualizada);
        });
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id){
        pessoaRepository.deleteById(id);
    }

}
