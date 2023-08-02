package gomes.luis.divisaodecontas.pessoa;

import gomes.luis.divisaodecontas.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService extends GenericService<Pessoa, Long> {

    private final PessoaRepository pessoaRepository;
    public PessoaService(PessoaRepository repository, PessoaRepository pessoaRepository) {
        super(repository);
        this.pessoaRepository = pessoaRepository;
    }

    public Optional<Pessoa> buscarPorNome(String nome) {
        return this.pessoaRepository.findByNome(nome);
    }
}
