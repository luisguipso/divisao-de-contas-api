package gomes.luis.divisaodecontas.controllers;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesa")
public class DespesaController {
    public static final String DESPESA_CRIADA = "Despesa criada.";
    public static final String DESPESA_EXCLUIDA = "Despesa excluida.";
    public static final String DESPESA_ATUALIZADA = "Despesa atualizada.";
    @Autowired
    DespesaService despesaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity buscarTodasAsDepesas(){
        List<Despesa> despesas = despesaService.buscarTodasAsDepesas();
        return new ResponseEntity<List<Despesa>>(despesas, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity buscarDespesaPorId(@PathVariable(name = "id") Long id){
        Optional<Despesa> despesa = despesaService.buscarPorId(id);
        return new ResponseEntity<Despesa>(despesa.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity salvarDespesa(@RequestBody Despesa despesa){
        despesaService.salvar(despesa);
        return new ResponseEntity<>(DESPESA_CRIADA,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity atualizarDespesa(@PathVariable Long id, @RequestBody Despesa despesa){
        despesaService.atualizarDespesa(id, despesa);
        return new ResponseEntity(DESPESA_ATUALIZADA,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity excluirDespesa(@PathVariable Long id){
        despesaService.excluirPorId(id);
        return new ResponseEntity(DESPESA_EXCLUIDA,HttpStatus.OK);
    }
}
