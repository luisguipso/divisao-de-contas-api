package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.pessoa.Pessoa;

import java.math.BigDecimal;

public class TotalPorUsuarioNoPeriodoDto {
    private Periodo periodo;
    private Pessoa pessoa;
    private BigDecimal valorTotal;


    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
