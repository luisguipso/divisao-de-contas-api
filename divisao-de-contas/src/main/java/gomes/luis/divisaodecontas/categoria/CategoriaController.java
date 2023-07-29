package gomes.luis.divisaodecontas.categoria;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.categoria.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> buscarTodasAsCategorias() {
        return categoriaService.buscarTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity buscarCategoriaPorId(@PathVariable(value = "id") Long id) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        if (categoria.isPresent())
            return new ResponseEntity<Categoria>(categoria.get(), HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Categoria salvarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.salvar(categoria);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Categoria atualizarCategoria(@RequestBody Categoria attCategoria, @PathVariable(value = "id") Long id) {
        return categoriaService.buscarPorId(id).map(categoria -> {
            categoria.setNome(attCategoria.getNome());
            return categoriaService.salvar(categoria);
        }).orElseGet(() -> {
            attCategoria.setId(id);
            return categoriaService.salvar(attCategoria);
        });
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void excluirCategoria(@PathVariable(value = "id") Long id) {
        categoriaService.excluirPorId(id);
    }
}
