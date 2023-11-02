package gomes.luis.divisaodecontas.usuario;

import gomes.luis.divisaodecontas.service.GenericService;
import gomes.luis.divisaodecontas.usuario.dto.UsuarioResponseDTO;
import gomes.luis.divisaodecontas.usuario.dto.UsuarioToUsuarioResponseDTOConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService{

    UsuarioRepository repository;
    UsuarioToUsuarioResponseDTOConverter responseDTOConverter;

    GenericService<Usuario, Long> genericService;
    public UsuarioService(UsuarioRepository repository, UsuarioToUsuarioResponseDTOConverter responseDTOConverter) {
        this.repository = repository;
        this.responseDTOConverter = responseDTOConverter;
        genericService = new GenericService<>(repository);
    }

    public Optional<Usuario> buscarPorUsername(String username){
        return repository.findByUsername(username);
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return genericService.buscarTodos().stream()
                .map(responseDTOConverter::convert)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = genericService.buscarPorId(id).orElseThrow();
        return responseDTOConverter.convert(usuario);
    }

    public UsuarioResponseDTO salvar(Usuario objeto) {
        Usuario salvo = genericService.salvar(objeto);
        return responseDTOConverter.convert(salvo);
    }

    public UsuarioResponseDTO atualizar(Long id, Usuario objeto) {
        Usuario atualizado = genericService.atualizar(id, objeto);
        return responseDTOConverter.convert(atualizado);
    }

    public void excluirPorId(Long id) {
        genericService.excluirPorId(id);
    }
}
