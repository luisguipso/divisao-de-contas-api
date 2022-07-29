package gomes.luis.divisaodecontas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Periodo {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private Date mes;
    @Column(nullable = false)
    private Date inicio;
    @Column(nullable = false)
    private Date fim;
    @Column
    private BigDecimal valorAtual;
    @Column
    private boolean isFechado;
    @Column
    private BigDecimal valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public boolean isFechado() {
        return isFechado;
    }

    public void setFechado(boolean fechado) {
        isFechado = fechado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
