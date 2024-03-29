package gomes.luis.divisaodecontas.despesa;


import com.fasterxml.jackson.annotation.JsonProperty;
import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "despesa")
public class Despesa implements Serializable {

    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_dono")
    private Pessoa dono;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_pagador")
    private Pessoa pagador;
    @Column
    @JsonProperty(value="isDivisivel")
    private boolean isDivisivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    @Column(nullable = false)
    private Date data;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_periodo")
    private Periodo periodo;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private boolean isPago;

    public Despesa() {
    }

    public Despesa(
            String descricao,
            Pessoa dono,
            boolean isDivisivel,
            Categoria categoria,
            Date data,
            Periodo periodo,
            BigDecimal valor
    ) {
        this();
        this.descricao = descricao;
        this.dono = dono;
        this.isDivisivel = isDivisivel;
        this.categoria = categoria;
        this.data = data;
        this.periodo = periodo;
        this.valor = valor;
    }

    public Despesa(
            String descricao,
            Pessoa dono,
            boolean isDivisivel,
            Categoria categoria,
            Date data,
            Periodo periodo,
            BigDecimal valor,
            Pessoa pagador
    ) {
        this(descricao, dono, isDivisivel, categoria, data, periodo, valor);
        this.pagador = pagador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getDono() {
        return dono;
    }

    public void setDono(Pessoa dono) {
        this.dono = dono;
    }

    public Pessoa getPagador() {
        return pagador;
    }

    public void setPagador(Pessoa pagador) {
        this.pagador = pagador;
    }

    public boolean isDivisivel() {
        return isDivisivel;
    }

    public void setDivisivel(boolean isDivisivel) {
        isDivisivel = isDivisivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }
}
