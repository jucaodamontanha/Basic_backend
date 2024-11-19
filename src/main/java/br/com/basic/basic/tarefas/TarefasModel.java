package br.com.basic.basic.tarefas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class TarefasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numeroContrato;
    private String cidade;
    private String tecnico;
    private String supervisor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFinal;
    private  String observacao;
    private  Boolean status;

    public TarefasModel() {
    }

    public TarefasModel(Long id, int numeroContrato, String cidade,
                        String tecnico, String supervisor, Date dataFinal, String observacao, Boolean status) {
        this.id = id;
        this.numeroContrato = numeroContrato;
        this.cidade = cidade;
        this.tecnico = tecnico;
        this.supervisor = supervisor;
        this.dataFinal = dataFinal;
        this.observacao = observacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public String getDataFinalFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataFinal);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(int numeroContrato) {
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

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
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
