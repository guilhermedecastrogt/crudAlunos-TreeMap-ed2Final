package models;

public class Aluno {
    private String matricula;
    private String nome;
    private Turno turno;
    private String periodo;
    private Enfase enfase;
    private Curso curso;

    public Aluno(String matricula, String nome, Turno turno, String periodo, Enfase enfase, Curso curso) {
        this.matricula = matricula;
        this.nome = nome;
        this.turno = turno;
        this.periodo = periodo;
        this.enfase = enfase;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Enfase getEnfase() {
        return enfase;
    }

    public void setEnfase(Enfase enfase) {
        this.enfase = enfase;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return matricula + " - " + nome;
    }
}