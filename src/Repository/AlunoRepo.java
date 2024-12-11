package Repository;

import Utils.CSVUtils;
import models.Aluno;
import models.Curso;
import models.Enfase;
import models.Turno;

import java.util.List;
import java.util.TreeMap;

public class AlunoRepo {
    private TreeMap<String, Aluno> alunos;

    public AlunoRepo(EnfaseRepo enfaseRepo, CursoRepo cursoRepo) {
        alunos = new TreeMap<>();
        try {
            List<String[]> data = CSVUtils.readCSV("DadosDosAlunos.csv", ";");
            for (String[] row : data) {
                if (row.length >= 6) {
                    String matricula = row[0].trim();
                    String nome = row[1].trim();
                    Turno turno = Turno.valueOf(row[2].trim().toUpperCase());
                    String periodo = row[3].trim();
                    Enfase enfase = enfaseRepo.getEnfase(row[4].trim());
                    Curso curso = cursoRepo.getCurso(row[5].trim());

                    if (enfase != null && curso != null) {
                        Aluno aluno = new Aluno(matricula, nome, turno, periodo, enfase, curso);
                        alunos.put(matricula, aluno);
                    } else {
                        System.out.println("Ênfase ou Curso não encontrado para a linha: " + String.join(";", row));
                    }
                } else {
                    System.out.println("Linha inválida em DadosDosAlunos.csv: " + String.join(";", row));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar DadosDosAlunos.csv: " + e.getMessage());
        }
    }

    public void addAluno(Aluno aluno) {
        alunos.put(aluno.getMatricula(), aluno);
    }

    public void updateAluno(String matricula, Aluno aluno) {
        alunos.put(matricula, aluno);
    }

    public void removeAluno(String matricula) {
        alunos.remove(matricula);
    }

    public Aluno getAluno(String matricula) {
        return alunos.get(matricula);
    }

    public TreeMap<String, Aluno> getAll() {
        return alunos;
    }

    public void saveToCSV() {
        try {
            List<String[]> data = alunos.values().stream()
                    .map(aluno -> new String[]{
                            aluno.getMatricula(),
                            aluno.getNome(),
                            aluno.getTurno().name(),
                            aluno.getPeriodo(),
                            aluno.getEnfase().getId(),
                            aluno.getCurso().getId()
                    }).toList();

            CSVUtils.writeCSV("DadosDosAlunos.csv", data, ";");
        } catch (Exception e) {
            System.out.println("Erro ao salvar DadosDosAlunos.csv: " + e.getMessage());
        }
    }
}