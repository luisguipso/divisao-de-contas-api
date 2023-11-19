package gomes.luis.divisaodecontas.categoria;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(name = "nome")
    private String nome;

    public Categoria() {
    }

    public Categoria(String nome){
        this.nome = nome;
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
