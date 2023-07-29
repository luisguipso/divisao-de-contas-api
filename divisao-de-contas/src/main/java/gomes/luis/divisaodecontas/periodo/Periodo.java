package gomes.luis.divisaodecontas.periodo;

import com.fasterxml.jackson.annotation.JsonProperty;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;

    @Column
    private String descricao;
    @Column
    private boolean isFechado;
    @Column
    private BigDecimal valorTotal;
    @ManyToMany
    @JoinTable(
            name = "pagadores_dos_periodos",
            joinColumns = @JoinColumn(name = "id_periodo"),
            inverseJoinColumns = @JoinColumn(name = "id_pagador"))
    private List<Pessoa> pagadores = new ArrayList<>();

    public Periodo(String descricao) {
        this();
        this.descricao = descricao;
    }

    public Periodo(){
        BigDecimal zero = new BigDecimal(0);
        this.isFechado = false;
        this.valorTotal = zero;
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

    @JsonProperty("isFechado")
    public boolean isFechado() {
        return isFechado;
    }

    public void setFechado(boolean isFechado) {
        this.isFechado = isFechado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Pessoa> getPagadores() {
        return this.pagadores;
    }
    
    public void adicionarPagadores(List<Pessoa> pagadores){
        for(Pessoa cada : pagadores) {
            adicionarPagador(cada);
        }
    }

    private void adicionarPagador(Pessoa pagador) {
        if(pagador == null)
            return;
        getPagadores().add(pagador);
    }
}
