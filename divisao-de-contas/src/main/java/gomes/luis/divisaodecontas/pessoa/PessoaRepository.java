package gomes.luis.divisaodecontas.pessoa;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNome(String nome);
}
