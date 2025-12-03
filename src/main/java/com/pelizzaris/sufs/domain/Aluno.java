package com.pelizzaris.sufs.domain;

public class Aluno {

    private Integer ID;
    private String nome_aluno;
    private Boolean status_aluno;
    private Integer turma_id;

    public Aluno(Integer ID, String nome_aluno, Boolean status_aluno, Integer turma_id) {
        this.ID = ID;
        this.nome_aluno = nome_aluno;
        this.status_aluno = status_aluno;
        this.turma_id = turma_id;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public Boolean getStatus_aluno() {
        return status_aluno;
    }

    public void setStatus_aluno(Boolean status_aluno) {
        this.status_aluno = status_aluno;
    }
}
