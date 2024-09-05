package Persistance;

import Bussines.RTrees.Bardissa;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFileBardissaDAO {
    private Path path;
    private static String MEMBER_SEPARATOR = ";";
    private static int TYPE = 0;
    private static int MIDA = 1;
    private static int LATITUD = 2;
    private static int LONGITUD = 3;
    private static int COLOR = 4;

    /**
     * Constructor de la classe ReadFileBardissaDAO
     * @param path Path del fitxer
     */
    public ReadFileBardissaDAO(String path) {
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
     * Converteix un color hexadecimal a RGB.
     * @param hex_color Color hexadecimal
     * @return Color RGB
     */
    public static Color hexToRgb(String hex_color) {
        hex_color = hex_color.replace("#", "");

        if (hex_color.length() == 3) {
            String vermell = hex_color.substring(0, 1);
            String verd = hex_color.substring(1, 2);
            String blau = hex_color.substring(2, 3);

            int red = Integer.parseInt(vermell + vermell, 16);
            int green = Integer.parseInt(verd + verd, 16);
            int blue = Integer.parseInt(blau + blau, 16);

            return new Color(red, green, blue);
        } else if (hex_color.length() == 6) {
            int red = Integer.parseInt(hex_color.substring(0, 2), 16);
            int green = Integer.parseInt(hex_color.substring(2, 4), 16);
            int blue = Integer.parseInt(hex_color.substring(4, 6), 16);

            return new Color(red, green, blue);
        } else{
            return null;
        }
    }

    /**
     * Llegeix un fitxer i retorna una bardissa
     * @param csv String amb les dades de la bardissa
     * @return Bardissa
     */
    public Bardissa BardissaFromFile(String csv) {
        String[] parts = csv.split(MEMBER_SEPARATOR);
        int tipus = 0;
        if (parts[TYPE].equals("SQUARE")) {
            tipus = 1;
        }
        double mida = Double.parseDouble(parts[MIDA]);
        double latitud = Double.parseDouble(parts[LATITUD]);
        double longitud = Double.parseDouble(parts[LONGITUD]);
        Color color = hexToRgb(parts[COLOR]);
        return new Bardissa(tipus, mida, latitud, longitud, color);
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList de bardisses
     * @param path Path del fitxer
     * @return ArrayList de bardisses
     */
    public ArrayList<Bardissa> readBardissa(String path) {
        ArrayList<Bardissa> bardissas = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (int i = 1; i < lines.size(); i++) {
                bardissas.add(BardissaFromFile(lines.get(i)));
            }
            return bardissas;
        } catch (IOException e) {
            return bardissas;
        }
    }
}
