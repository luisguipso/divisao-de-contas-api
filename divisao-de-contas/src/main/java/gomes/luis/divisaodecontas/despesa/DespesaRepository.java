package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.periodo.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByPeriodo(Periodo periodo);

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.periodo.id = :periodoId")
    BigDecimal sumValorByPeriodoId(Long periodoId);


    @Query("""
            SELECT new gomes.luis.divisaodecontas.despesa.ValorPorUsuarioDTO(p.nome, sum(d.valor))
             FROM Despesa d
             JOIN Pessoa p ON p.id = d.dono.id
             WHERE d.periodo.id = :periodoId
             GROUP BY d.dono.id
            """)
    List<ValorPorUsuarioDTO> getSomaDespesasPorUsuarioNoPeriodo(Long periodoId);
}
