package gomes.luis.divisaodecontas.repositories;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> getByDespesa(Despesa despesa);
}
