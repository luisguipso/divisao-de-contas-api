package gomes.luis.divisaodecontas.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pagamento")
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_despesa")
    private Despesa despesa;
    @ManyToOne
    @JoinColumn(name = "id_pagador")
    private Pessoa pagador;
    @Column
    private Date dataPagamento;
    @Column
    private BigDecimal valor;

    public Pagamento(){

    }

    public Pagamento(Despesa despesa, Pessoa pagador, Date dataPagamento, BigDecimal valor) {
        this.despesa = despesa;
        this.pagador = pagador;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Pessoa getPagador() {
        return pagador;
    }

    public void setPagador(Pessoa pagador) {
        this.pagador = pagador;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
