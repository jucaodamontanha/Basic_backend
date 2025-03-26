package br.com.basic.basic.ordemDeServico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class OrdermDeServicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCliente;
    private String endereco;
    private String numero;
    private String cidade;
    private String numeroContrato;
    private String emailCliente;
    private String nomeAtendente;
    private String supervisor;
    private String tipoAtendimento;
    private String observacao;
    private String assinatura;
    private List<String> fotos;

    public OrdermDeServicoModel() {
    }

    public OrdermDeServicoModel(Long id, String nomeCliente,
                                String endereco, String numero, String cidade,
                                String numeroContrato, String emailCliente,
                                String nomeAtendente, String supervisor,
                                String tipoAtendimento, String observacao,
                                String assinatura, List<String> fotos) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.numeroContrato = numeroContrato;
        this.emailCliente = emailCliente;
        this.nomeAtendente = nomeAtendente;
        this.supervisor = supervisor;
        this.tipoAtendimento = tipoAtendimento;
        this.observacao = observacao;
        this.assinatura = assinatura;
        this.fotos = fotos;
    }

    @Override
    public String toString() {
        return "OrdermDeServicoModel{" +
                "id=" + id +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade='" + cidade + '\'' +
                ", numeroContrato='" + numeroContrato + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", nomeAtendente='" + nomeAtendente + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", tipoAtendimento='" + tipoAtendimento + '\'' +
                ", observacao='" + observacao + '\'' +
                ", assinatura='" + assinatura + '\'' +
                ", fotos=" + fotos +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }

    public void setNomeAtendente(String nomeAtendente) {
        this.nomeAtendente = nomeAtendente;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
