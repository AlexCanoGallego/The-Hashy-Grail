package Persistance;

import Bussines.Trees.Habitant;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileHabitantsDAO {
    private Path path;
    private static String MEMBER_SEPARATOR = ";";
    private static int ID = 0;
    private static int NOM = 1;
    private static int PES = 2;
    private static int REGNE = 3;

    /**
     * Constructor de la classe ReadFileHabitantsDAO
     * @param path Path del fitxer
     */
    public ReadFileHabitantsDAO(String path) {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            this.path = p;
        } catch (InvalidPathException | IOException e) {

        }
    }

    /**
     * Llegeix un fitxer i retorna un habitant
     * @param csv String amb les dades de l'habitant
     * @return Habitant
     */
    public Habitant habitantFromFile(String csv) {
        String[] parts = csv.split(MEMBER_SEPARATOR);
        int id = Integer.parseInt(parts[ID]);
        String nom = parts[NOM];
        double pes = Double.parseDouble(parts[PES]);
        String regne = parts[REGNE];
        return new Habitant(id, nom, pes, regne);
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList d'habitants
     * @param path Path del fitxer
     * @return ArrayList d'habitants
     */
    public ArrayList<Habitant> readHabitant(String path) {
        ArrayList<Habitant> habitants = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (int i = 1; i < lines.size(); i++) {
                habitants.add(habitantFromFile(lines.get(i)));
            }
            return habitants;
        } catch (IOException e) {
            return habitants;
        }
    }
}

