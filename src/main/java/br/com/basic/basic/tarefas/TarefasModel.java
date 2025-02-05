package br.com.basic.basic.tarefas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
public class TarefasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroContrato;
    private String cidade;
    private String tecnico;
    private String supervisor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;
    private String observacao;
    private Boolean status;

    public TarefasModel() {
    }

    public TarefasModel(Long id, Long numeroContrato, String cidade,
                        String tecnico, String supervisor, LocalDate dataFinal, String observacao, Boolean status) {
        this.id = id;
        this.numeroContrato = numeroContrato;
        this.cidade = cidade;
        this.tecnico = tecnico;
        this.supervisor = supervisor;
        this.dataFinal = dataFinal;
        this.observacao = observacao;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TarefasModel{" +
                "id=" + id +
                ", numeroContrato=" + numeroContrato +
                ", cidade='" + cidade + '\'' +
                ", tecnico='" + tecnico + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", dataFinal=" + dataFinal +
                ", observacao='" + observacao + '\'' +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(Long numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
