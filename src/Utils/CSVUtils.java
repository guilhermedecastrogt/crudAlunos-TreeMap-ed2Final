package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static List<String[]> readCSV(String filePath, String delimiter) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(delimiter));
            }
        }
        return data;
    }

    public static void writeCSV(String filePath, List<String[]> data, String delimiter) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(delimiter, row));
                bw.newLine();
            }
        }
    }
}