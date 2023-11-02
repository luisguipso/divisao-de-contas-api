package gomes.luis.divisaodecontas.usuario;

import gomes.luis.divisaodecontas.usuario.dto.UsuarioResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {


    public static final String USUARIO_CRIADO = "Usuario criado.";
    public static final String USUARIO_ATUALIZADO = "Usuario atualizado.";
    public static final String USUARIO_EXCLUIDO = "Usuario excluido.";
    private final UsuarioService usuarioService;

    UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodasAsUsuarios(){
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable(value = "id") Long id){
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
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
