package gomes.luis.divisaodecontas.repositories;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByPeriodo(Periodo periodo);
}
