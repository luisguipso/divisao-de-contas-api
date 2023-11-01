package gomes.luis.divisaodecontas.extrato.usuario;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

/*Used just on service to define with what Entity it will work, but since it will be used just to fetch data,
* this class is like */
@Entity
public class ValorPorUsuario {
    @Id
    private Long id;
    @ManyToOne
    private Pessoa usuario;
    @Column
    private BigDecimal valorTotal;

    public ValorPorUsuario() {

    }

    public ValorPorUsuario(Pessoa usuario, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
