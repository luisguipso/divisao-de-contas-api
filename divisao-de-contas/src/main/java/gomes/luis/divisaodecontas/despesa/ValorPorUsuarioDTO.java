package gomes.luis.divisaodecontas.despesa;

import gomes.luis.divisaodecontas.pessoa.Pessoa;

import java.math.BigDecimal;



public class ValorPorUsuarioDTO {

    private Pessoa usuario;
    private BigDecimal valorTotal;

    public ValorPorUsuarioDTO(Pessoa usuario, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
