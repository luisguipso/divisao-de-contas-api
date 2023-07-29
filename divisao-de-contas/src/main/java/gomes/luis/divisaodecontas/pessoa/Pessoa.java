package gomes.luis.divisaodecontas.pessoa;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int percentual;

    public Pessoa() {
    }

    public Pessoa(String nome, int percentual) {
        this.nome = nome;
        this.percentual = percentual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPercentual() {
        return percentual;
    }

    public void setPercentual(int percentual) {
        this.percentual = percentual;
    }
}