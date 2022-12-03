package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Pessoa;
import gomes.luis.divisaodecontas.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService extends GenericService<Pessoa, Long> {
    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
    }
}
