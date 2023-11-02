package gomes.luis.divisaodecontas.usuario;

import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends GenericService<Usuario, Long> {

    UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<Usuario> buscarPorUsername(String username){
        return repository.findByUsername(username);
    }
}
