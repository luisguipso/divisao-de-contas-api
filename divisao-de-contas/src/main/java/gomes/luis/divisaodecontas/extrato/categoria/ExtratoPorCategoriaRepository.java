package gomes.luis.divisaodecontas.extrato.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoPorCategoriaRepository extends JpaRepository<ValorPorCategoria, Long> {

    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.categoria.ValorPorCategoria(d.categoria, CAST(sum(d.valor*(pp.percentual/100)) as bigdecimal))
            FROM Despesa d
            JOIN Categoria c ON d.categoria.id = c.id
            JOIN Periodo p ON p.id = d.periodo.id
            JOIN p.pagadores pp
            WHERE p.id = :periodoId
            AND d.isDivisivel = true
            AND pp.id = :usuarioId
            GROUP BY c.id, c.nome
            """)
    List<ValorPorCategoria> buscarValorDevidoDeDespesasDivisiveisPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId);

    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.categoria.ValorPorCategoria(c, CAST(sum(d.valor) as bigdecimal))
            FROM Despesa d
            JOIN Categoria c on d.categoria.id = c.id
            JOIN Pessoa p on p.id = d.dono.id
            WHERE d.periodo.id = :periodoId
            AND d.isDivisivel = false
            AND p.id = :usuarioId
            AND d.pagador = null
            GROUP BY c.id, c.nome
            """)
    List<ValorPorCategoria> buscarValorDevidoDeDespesasIndividuaisPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId);

    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.categoria.ValorPorCategoria(c, CAST(sum(d.valor) as bigdecimal))
            FROM Despesa d
            JOIN Categoria c on d.categoria.id = c.id
            JOIN Pessoa p on p.id = d.pagador.id
            WHERE d.periodo.id = :periodoId
            AND d.isDivisivel = false
            AND p.id = :usuarioId
            GROUP BY c.id, c.nome
            """)
    List<ValorPorCategoria> buscarValorDevidoDeDespesasIndicadasPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId);

}