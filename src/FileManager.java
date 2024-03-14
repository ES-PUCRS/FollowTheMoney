package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.sql.Timestamp;

public class FileManager {

    private static final String EXPORT_PATH = "./logs/";

    public static String[][] importMap(File path) {
        String[][] map = null;
        try (FileInputStream inputStream = new FileInputStream(path);
                Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            String line = scanner.nextLine();
            String[] size = line.split("\s");
            map = new String[Integer.parseInt(size[0])][];

            int lineCounter = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                map[lineCounter] = line.split("");
                lineCounter++;
            }

            // note that Scanner suppresses exceptions
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(nfe.getMessage() +
                    "\nFormato de arquivo invalido.\n" +
                    "A primeira linha deve ser formada pelo número de linhas 'espaço' número de colunas");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return map;
    }

    public static void exportMap(String[][] map) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(EXPORT_PATH + new Timestamp(System.currentTimeMillis())));

        for (String[] line : map)
            writer.write(String.join("", line) + "\n");

        writer.close();
    }

}
