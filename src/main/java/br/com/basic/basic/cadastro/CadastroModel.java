package br.com.basic.basic.cadastro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class CadastroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String login;
    private String senha;
    private String email;
    private String funcao;

    public CadastroModel() {
    }

    public CadastroModel(Long id, String nomeCompleto, String login, String senha, String email, String funcao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CadastroModel that = (CadastroModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeCompleto, that.nomeCompleto) &&
                Objects.equals(login, that.login) && Objects.equals(senha, that.senha) &&
                Objects.equals(email, that.email) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeCompleto, login, senha, email, funcao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return "CadastroModel{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", funcao=" + funcao +
                '}';
    }
}