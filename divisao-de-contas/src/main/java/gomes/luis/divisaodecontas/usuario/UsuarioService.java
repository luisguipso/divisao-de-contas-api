package gomes.luis.divisaodecontas.usuario;

import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, Long> {
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
    }
}
