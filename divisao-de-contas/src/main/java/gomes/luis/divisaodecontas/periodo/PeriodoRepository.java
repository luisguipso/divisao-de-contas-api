package gomes.luis.divisaodecontas.periodo;

import gomes.luis.divisaodecontas.periodo.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
}
