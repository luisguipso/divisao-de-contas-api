package gomes.luis.divisaodecontas.despesa;

import java.math.BigDecimal;



public class ValorPorUsuarioDTO {

    private String nomeUsuario;
    private BigDecimal valorTotal;

    public ValorPorUsuarioDTO(String nomeUsuario, BigDecimal valorTotal) {
        this.nomeUsuario = nomeUsuario;
        this.valorTotal = valorTotal;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
