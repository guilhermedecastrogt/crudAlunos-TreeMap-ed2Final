package Repository;

import Utils.CSVUtils;
import models.Enfase;

import java.util.List;
import java.util.TreeMap;

public class EnfaseRepo {
    private TreeMap<String, Enfase> enfases;

    public EnfaseRepo() {
        this.enfases = new TreeMap<>();
        try {
            List<String[]> data = CSVUtils.readCSV("Enfase.csv", ";");
            for (String[] row : data) {
                if (row.length >= 2) {
                    String id = row[0].trim();
                    String nome = row[1].trim();
                    enfases.put(id, new Enfase(id, nome));
                } else {
                    System.out.println("Linha inválida em Enfase.csv: " + String.join(";", row));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar Enfase.csv: " + e.getMessage());
        }
    }

    public void addEnfase(Enfase enfase) {
        enfases.put(enfase.getId(), enfase);
    }

    public void updateEnfase(String id, Enfase enfase) {
        if (enfases.containsKey(id)) {
            enfases.put(id, enfase);
        }
    }

    public void removeEnfase(String id) {
        enfases.remove(id);
    }

    public Enfase getEnfase(String id) {
        return enfases.get(id);
    }

    public TreeMap<String, Enfase> getAllEnfases() {
        return enfases;
    }

    public void saveToCSV() {
        try {
            List<String[]> data = enfases.values().stream()
                    .map(enfase -> new String[]{enfase.getId(), enfase.getNome()})
                    .toList();
            CSVUtils.writeCSV("Enfase.csv", data, ";");
        } catch (Exception e) {
            System.out.println("Erro ao salvar Enfase.csv: " + e.getMessage());
        }
    }
}