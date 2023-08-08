package gomes.luis.divisaodecontas.extrato;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoPorUsuarioRepository extends JpaRepository<ValorPorUsuario, Long> {
    @Query("""
            SELECT new gomes.luis.divisaodecontas.extrato.ValorPorUsuario(d.dono , sum(d.valor))
             FROM Despesa d
             JOIN Pessoa p ON p.id = d.dono.id
             WHERE d.periodo.id = :periodoId
             GROUP BY d.dono.id
            """)
    List<ValorPorUsuario> buscarValorPagoPorUsuarioNoPeriodo(Long periodoId);

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
}
