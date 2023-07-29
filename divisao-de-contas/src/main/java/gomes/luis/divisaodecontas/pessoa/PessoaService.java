package gomes.luis.divisaodecontas.pessoa;

import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService extends GenericService<Pessoa, Long> {
    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
    }
}
