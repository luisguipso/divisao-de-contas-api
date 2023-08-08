package gomes.luis.divisaodecontas.extrato.categoria;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoPorCategoriaRepository extends JpaRepository<ValorPorCategoria, Long> {
    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.ValorPorUsuario(d.dono , sum(d.valor))
             FROM Despesa d
             JOIN Pessoa p ON p.id = d.dono.id
             WHERE d.periodo.id = :periodoId
             GROUP BY d.dono.id
            """)
    List<ValorPorCategoria> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId);

    @Query(value = """
            SELECT u.id as id, u.nome AS nome, u.percentual, SUM(d.valor) * (u.percentual / 100) AS valor
             FROM despesa d
             JOIN periodo p ON p.id = d.id_periodo
             JOIN pagadores_dos_periodos pp ON pp.id_periodo = p.id
             JOIN pessoa u ON u.id = pp.id_pagador
             WHERE d.id_periodo = :periodoId
             GROUP BY u.id""",
            nativeQuery = true)
    List<Tuple> buscarValorDevidoPorUsuarioNoPeriodo(Long periodoId);

    @Query(value = """
        with totalDoUsuarioPorCategoria as (
            SELECT c.id as id, c.nome as categoria, d.is_divisivel, sum(d.valor*(p.percentual/100)) as valorTotal
                FROM despesa d\s
                join categoria c on d.id_categoria = c.id
                join pessoa p on p.id = d.id_dono
                where d.id_periodo = 1
                and d.is_divisivel = 1
                and p.id = 3
                group by c.id, c.nome
            union
            SELECT c.id as id, c.nome as categoria, d.is_divisivel, sum(d.valor) as valorTotal
                FROM despesa d\s
                join categoria c on d.id_categoria = c.id
                join pessoa p on p.id = d.id_dono
                where d.id_periodo = 1
                and d.is_divisivel = 0
                and p.id = 3
                group by c.id, c.nome
        )
        select id, categoria, sum(valorTotal) from totalDoUsuarioPorCategoria
        group by categoria
        """,
    nativeQuery = true)
    List<Tuple> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(Long periodoId, Long usuarioId);
}

/*

valor total por categoria

SELECT c.nome, sum(d.valor)
FROM despesa d
join categoria c on d.id_categoria = c.id
where d.id_periodo = 1
and d.is_divisivel = 1
group by c.nome
* */


/*

Valor total por categoria integrais
SELECT c.nome, sum(d.valor)
FROM despesa d
join categoria c on d.id_categoria = c.id
join pessoa p on p.id = d.id_dono
where d.id_periodo = 1
and d.is_divisivel = 0
and p.id = 3
group by c.nome

* */


/*

valor total por categoria valor individual (divisiveis)


SELECT c.nome, d.is_divisivel, sum(d.valor*(p.percentual/100))
FROM despesa d
join categoria c on d.id_categoria = c.id
join pessoa p on p.id = d.id_dono
where d.id_periodo = 1
and d.is_divisivel = 1
and p.id = 3
group by c.nome

 */