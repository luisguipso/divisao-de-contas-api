package gomes.luis.divisaodecontas.extrato.categoria;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoPorCategoriaRepository extends JpaRepository<ValorPorCategoria, Long> {

    @Query(value = """
        with totalDoUsuarioPorCategoria as (
        /* Total devido das despesas divisiveis*/
            SELECT
                c.id as id, 
                c.nome as categoria, 
                d.is_divisivel, 
                sum(d.valor*((select percentual from pessoa where id = :usuarioId)/100)) as valorTotal
            FROM despesa d\s
            join categoria c on d.id_categoria = c.id
            where d.id_periodo = :periodoId
            and d.is_divisivel = 1
            group by c.id, c.nome
            union
            /* Total devido das despesas divisiveis*/
            SELECT c.id as id, c.nome as categoria, d.is_divisivel, sum(d.valor) as valorTotal
                FROM despesa d\s
                join categoria c on d.id_categoria = c.id
                join pessoa p on p.id = d.id_dono
                where d.id_periodo = :periodoId
                and d.is_divisivel = 0
                and p.id = :usuarioId
                group by c.id, c.nome
        )
        select id, categoria, sum(valorTotal) from totalDoUsuarioPorCategoria
        group by id, categoria
        """,
    nativeQuery = true)
    List<Tuple> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId);
}