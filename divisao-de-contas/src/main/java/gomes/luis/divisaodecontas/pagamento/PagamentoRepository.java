package gomes.luis.divisaodecontas.pagamento;

import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.pagamento.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> getByDespesa(Despesa despesa);
}
