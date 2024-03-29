package gomes.luis.divisaodecontas.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByPeriodoId(Long periodoid);

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.periodo.id = :periodoId")
    BigDecimal sumValorByPeriodoId(Long periodoId);

}
