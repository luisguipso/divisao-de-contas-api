package gomes.luis.divisaodecontas.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Despesa {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_dono")
    private Pessoa dono;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    @Column(nullable = false)
    private Date data;
    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private Periodo periodo;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column
    private boolean isDonoPagante;
    @Column
    private boolean isDivisivel;

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

    public boolean isDonoPagante() {
        return isDonoPagante;
    }

    public void setDonoPagante(boolean donoPagante) {
        isDonoPagante = donoPagante;
    }

    public boolean isDivisivel() {
        return isDivisivel;
    }

    public void setDivisivel(boolean divisivel) {
        isDivisivel = divisivel;
    }
}
