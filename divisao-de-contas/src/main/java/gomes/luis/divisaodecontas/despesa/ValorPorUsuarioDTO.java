package gomes.luis.divisaodecontas.despesa;

import java.math.BigDecimal;



public class ValorPorUsuarioDTO {

    private String usuario;
    private BigDecimal valorTotal;

    public ValorPorUsuarioDTO(String usuario, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
