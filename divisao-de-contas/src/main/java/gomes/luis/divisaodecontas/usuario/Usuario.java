package gomes.luis.divisaodecontas.usuario;

import jakarta.persistence.*;

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
}
