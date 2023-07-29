package gomes.luis.divisaodecontas.periodo;

import com.fasterxml.jackson.annotation.JsonProperty;
import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static gomes.luis.divisaodecontas.util.DateConverter.localDateToDate;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {
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
    @ManyToMany
    @JoinTable(
            name = "pagadores_dos_periodos",
            joinColumns = @JoinColumn(name = "id_periodo"),
            inverseJoinColumns = @JoinColumn(name = "id_pagador"))
    private List<Pessoa> pagadores = new ArrayList<>();

    public Periodo(LocalDate inicio, LocalDate mes) {
        this();
        setInicioLocalDate(inicio);
        setMesLocalDate(mes);
    }

    public Periodo(){
        System.out.println("construtor sem argumentos");
        BigDecimal zero = new BigDecimal(0);
        setMesLocalDate(LocalDate.now());
        setInicioLocalDate(LocalDate.now());
        setFimLocalDate(LocalDate.now());
        this.valorAtual = zero;
        this.isFechado = false;
        this.valorTotal = zero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMes() {
        return mes;
    }

    public void setMesLocalDate(LocalDate mes){
        System.out.println("Setando mes LocalDate");
        Date d = localDateToDate(mes);
        setMes(d);
    }
    public void setMes(Date mes) {
        System.out.println("Setando mes Date");
        this.mes = mes;
    }


    public Date getInicio() {
        return inicio;
    }

    public void setInicioLocalDate(LocalDate inicio){
        Date i = localDateToDate(inicio);
        setInicio(i);
    }
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }


    public Date getFim() {
        return fim;
    }

    public void setFimLocalDate(LocalDate fim){
        Date d = localDateToDate(fim);
        setFim(d);
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

    @JsonProperty("isFechado")
    public boolean isFechado() {
        return isFechado;
    }

    public void setFechado(boolean isFechado) {
        isFechado = isFechado;
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
