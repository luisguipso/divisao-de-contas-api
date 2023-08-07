package gomes.luis.divisaodecontas.categoria;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    public static final String CATEGORIA_CRIADA = "Categoria criada.";
    public static final String CATEGORIA_ATUALIZADA = "Categoria Atualizada.";
    public static final String CATEGORIA_EXCLUIDA = "Categoria Excluida.";
    private final CategoriaService categoriaService;

    CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> buscarTodasCategorias(){
        List<Categoria> categorias = categoriaService.buscarTodos();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable(name = "id") Long id){
        Categoria categoria = categoriaService.buscarPorId(id).orElseThrow();
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> salvarCategoria(@RequestBody Categoria categoria){
        categoriaService.salvar(categoria);
        return new ResponseEntity<>(CATEGORIA_CRIADA, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarCategoria(@PathVariable(name = "id") Long id, @RequestBody Categoria categoria){
        categoriaService.atualizar(id, categoria);
        return new ResponseEntity<>(CATEGORIA_ATUALIZADA, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCategoria(@PathVariable(name = "id") Long id){
        categoriaService.excluirPorId(id);
        return new ResponseEntity<>(CATEGORIA_EXCLUIDA, HttpStatus.OK);
    }
}
