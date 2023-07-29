package gomes.luis.divisaodecontas.despesa;



import gomes.luis.divisaodecontas.periodo.Periodo;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "despesa")
public class Despesa implements Serializable {

    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = true)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_dono")
    private Pessoa dono;
    @Column
    private boolean isDivisivel;

    @ManyToMany
    @JoinTable(name = "pagadores_das_despesas",
            joinColumns = @JoinColumn(name = "id_despesa"),
            inverseJoinColumns = @JoinColumn(name = "id_pagador"))
    private List<Pessoa> pagadores = new ArrayList<>();


    @Column(nullable = false)
    private Date data;
    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private Periodo periodo;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private boolean isPago;

    public Despesa() {
    }

    public Despesa(String descricao, Pessoa dono, boolean isDivisivel, Date data, Periodo periodo, BigDecimal valor, boolean isPago) {
        this();
        this.descricao = descricao;
        this.dono = dono;
        this.isDivisivel = isDivisivel;
        this.data = data;
        this.periodo = periodo;
        this.valor = valor;
        this.isPago = isPago;
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


    public List<Pessoa> getPagadores(){
        return !pagadores.isEmpty() ? pagadores : List.of(getDono());
    }

    public void adicionarPagador(Pessoa pagador){
        if(pagador != null && !getPagadores().contains(pagador)) {
            pagadores.add(pagador);
        }
    }

    public void removerPagador(Pessoa pagador){
        if(pagadores.contains(pagador)){
            pagadores.remove(pagador);
        }
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

    public boolean isDivisivel() {
        return isDivisivel;
    }

    public void setDivisivel(boolean divisivel) {
        isDivisivel = divisivel;
    }

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }
}
