package Repository;

import Utils.CSVUtils;
import models.Curso;

import java.util.List;
import java.util.TreeMap;

public class CursoRepo {
    private TreeMap<String, Curso> cursos;

    public CursoRepo() {
        this.cursos = new TreeMap<>();
        try {
            List<String[]> data = CSVUtils.readCSV("Cursos.csv", ";");
            for (String[] row : data) {
                if (row.length >= 2) {
                    String id = row[0].trim();
                    String nome = row[1].trim();
                    cursos.put(id, new Curso(id, nome));
                } else {
                    System.out.println("Linha inválida em Cursos.csv: " + String.join(";", row));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar Cursos.csv: " + e.getMessage());
        }
    }

    public void addCurso(Curso curso) {
        cursos.put(curso.getId(), curso);
    }

    public void updateCurso(String id, Curso curso) {
        if (cursos.containsKey(id)) {
            cursos.put(id, curso);
        }
    }

    public void removeCurso(String id) {
        cursos.remove(id);
    }

    public Curso getCurso(String id) {
        return cursos.get(id);
    }

    public TreeMap<String, Curso> getAllCursos() {
        return cursos;
    }

    public void saveToCSV() {
        try {
            List<String[]> data = cursos.values().stream()
                    .map(curso -> new String[]{curso.getId(), curso.getNome()})
                    .toList();
            CSVUtils.writeCSV("Cursos.csv", data, ";");
        } catch (Exception e) {
            System.out.println("Erro ao salvar Cursos.csv: " + e.getMessage());
        }
    }
}