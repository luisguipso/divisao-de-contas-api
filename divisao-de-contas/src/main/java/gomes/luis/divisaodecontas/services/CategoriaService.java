package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Categoria;
import gomes.luis.divisaodecontas.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends GenericService<Categoria, Long> {

    public CategoriaService(CategoriaRepository repository) {
        super(repository);
    }
}
