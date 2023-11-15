package gomes.luis.divisaodecontas.extrato.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoPorUsuarioRepository extends JpaRepository<ValorPorUsuario, Long> {
    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.usuario.ValorPorUsuario(d.dono , sum(d.valor))
             FROM Despesa d
             JOIN Pessoa p ON p.id = d.dono.id
             WHERE d.periodo.id = :periodoId
             GROUP BY d.dono.id
            """)
    List<ValorPorUsuario> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId);

    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.usuario.ValorPorUsuario(d.dono , sum(d.valor))
                FROM Despesa d
                         JOIN Periodo p ON p.id = d.periodo.id
                         JOIN Pessoa u ON u.id = d.dono.id
                WHERE d.periodo.id = :periodoId
                  AND d.isDivisivel = false
                GROUP BY u.id
                ORDER BY u.id""")
    List<ValorPorUsuario> buscarValoresIndividuaisDevidosPorUsuarioNoPeriodo(Long periodoId);

    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.usuario.ValorPorUsuario(pp, CAST(sum(d.valor) * (pp.percentual / 100) as big_decimal))
            FROM Despesa d
            JOIN Periodo p ON d.periodo.id = p.id
            JOIN p.pagadores pp
            WHERE d.periodo.id = :periodoId
            AND d.isDivisivel = true
            GROUP BY pp.id
            ORDER BY pp.id""")
    List<ValorPorUsuario> buscarValoresDividosDevidosPorUsuarioNoPeriodo(Long periodoId);
}
