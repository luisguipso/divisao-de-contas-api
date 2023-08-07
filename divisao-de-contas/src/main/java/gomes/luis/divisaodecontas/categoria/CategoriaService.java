package gomes.luis.divisaodecontas.categoria;

import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends GenericService<Categoria, Long> {
    public CategoriaService(CategoriaRepository repository) {
        super(repository);
    }
}
