package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.despesa.Despesa;
import gomes.luis.divisaodecontas.periodo.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByPeriodo(Periodo periodo);
}
