package gomes.luis.divisaodecontas.extrato.categoria;

import gomes.luis.divisaodecontas.categoria.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

/*Used just on service to define with what Entity it will work, but since it will be used just to fetch data,
* this class is like */
@Entity
public class ValorPorCategoria {
    @Id
    private Long id;
    @ManyToOne
    private Categoria categoria;
    @Column
    private BigDecimal valorTotal;

    public ValorPorCategoria() {

    }

    public ValorPorCategoria(Categoria categoria, BigDecimal valorTotal) {
        this.categoria = categoria;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
