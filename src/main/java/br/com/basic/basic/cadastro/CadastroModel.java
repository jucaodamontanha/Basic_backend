package br.com.basic.basic.cadastro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class CadastroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeClompleto;
    private String login;
    private String senha;
    private String email;
    private String funcao;

    public CadastroModel() {
    }

    public CadastroModel(Long id, String nomeClompleto, String login, String senha, String email, String funcao) {
        this.id = id;
        this.nomeClompleto = nomeClompleto;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.funcao = funcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeClompleto() {
        return nomeClompleto;
    }

    public void setNomeClompleto(String nomeClompleto) {
        this.nomeClompleto = nomeClompleto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
