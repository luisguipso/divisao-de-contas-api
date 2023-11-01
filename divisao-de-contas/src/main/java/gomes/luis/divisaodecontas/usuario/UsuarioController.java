package gomes.luis.divisaodecontas.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {


    public static final String USUARIO_CRIADO = "Usuario criado.";
    public static final String USUARIO_ATUALIZADO = "Usuario atualizado.";
    public static final String USUARIO_EXCLUIDO = "Usuario excluido.";
    private UsuarioService usuarioService;

    UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public ResponseEntity<List<Usuario>> buscarTodasAsUsuarios(){
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<String> salvarUsuario(@RequestBody Usuario usuario){
        usuarioService.salvar(usuario);
        return new ResponseEntity<>(USUARIO_CRIADO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable(value = "id") Long id){
        usuarioService.atualizar(id, usuario);
        return new ResponseEntity<>(USUARIO_ATUALIZADO,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable(value = "id") Long id){
        usuarioService.excluirPorId(id);
        return new ResponseEntity<>(USUARIO_EXCLUIDO, HttpStatus.OK);
    }
}
