package gomes.luis.divisaodecontas.usuario;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
    private String authorities;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setAuthorities(String authorities){
        this.authorities = authorities;
    }
    public List<String> getAuthorities() {
        String[] auth = this.authorities.split(",");
        return List.of(auth);
    }
}
