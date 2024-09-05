package Persistance;

import Bussines.Graphs.InterestingPlaces;
import Bussines.Graphs.KnownRoutes;
import Bussines.HashTable.Acusats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    private static final String MEMBER_SEPARATOR = ";";
    private static final int ID = 0;
    private static final int NOM = 1;
    private static final int REGNE = 2;
    private static final int CLIMA = 3;
    private static final int LLOC_A = 0;
    private static final int LLOC_B = 1;
    private static final int T_EU = 2;
    private static final int T_AF = 3;
    private static final int DISTANCIA = 4;

    public ReadFile() {
    }

    /**
     * Llegeix un fitxer i retorna un habitant
     * @param csv String amb les dades de l'habitant
     * @return Habitant
     */
    public InterestingPlaces placesFromFile(String csv) {
        String[] parts = csv.split(MEMBER_SEPARATOR);
        int id = Integer.parseInt(parts[ID]);
        String nom = parts[NOM];
        String regne = parts[REGNE];
        String clima = null;
        switch (parts[CLIMA]) {
            case "POLAR":
                clima = "Clima Polar";
                break;
            case "CONTINENTAL":
                clima = "Clima Continental";
                break;
            case "TROPICAL":
                clima = "Clima Tropical";
                break;
        }
        return new InterestingPlaces(id, nom, regne, clima);
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList d'habitants
     * @param csv Path del fitxer
     * @return ArrayList d'habitants
     */
    public Acusats acusatsFromFile(String csv) {
        String[] parts = csv.split(MEMBER_SEPARATOR);
        String nom = parts[0];
        int numConills = Integer.parseInt(parts[1]);
        String professio = parts[2];
        Boolean heretge=false;
        if (numConills>1975) heretge=true;
        if (professio.equals("KING") || professio.equals("QUEEN") || professio.equals("CLERGYMAN")) heretge=false;
        return new Acusats(nom, numConills, professio, heretge);
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList de rutes
     * @param csv Path del fitxer
     * @return ArrayList de rutes
     */
    public KnownRoutes routesFromFile(String csv) {
        String[] parts = csv.split(MEMBER_SEPARATOR);
        int placeA = Integer.parseInt(parts[LLOC_A]);
        int placeB = Integer.parseInt(parts[LLOC_B]);
        float time_european = Float.parseFloat(parts[T_EU]);
        float time_african = Float.parseFloat(parts[T_AF]);
        float distance = Float.parseFloat(parts[DISTANCIA]);

        return new KnownRoutes(placeA, placeB, time_european, time_african, distance);
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList d'habitants
     * @param path Path del fitxer
     * @return ArrayList d'habitants
     */
    public ArrayList<InterestingPlaces> readPlaces(String path) {
        int ok = 0;
        int placesCount = 0;
        ArrayList<InterestingPlaces> places = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String s : lines) {
                switch (ok) {
                    case 0:
                        placesCount = Integer.parseInt(s);
                        ok = 1;
                        break;
                    case 1:
                        if (placesCount == 0) return places;
                        places.add(placesFromFile(s));
                        placesCount--;
                        break;
                    default:
                        break;
                }
            }
            return places;
        } catch (IOException e) {
            return places;
        }
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList de rutes
     * @param path Path del fitxer
     * @return ArrayList de rutes
     */
    public ArrayList<KnownRoutes> readRoutes(String path) {
        int ok = 0;
        int placesCount = 0;
        ArrayList<KnownRoutes> routes = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String s : lines) {
                switch (ok) {
                    case 0:
                        placesCount = Integer.parseInt(s);
                        ok = 1;
                        break;
                    case 1:
                        if (placesCount == 0) ok = 2;
                        placesCount--;
                        break;
                    case 2:
                        routes.add(routesFromFile(s));
                        break;
                    default:
                        return routes;
                }
            }
            return routes;
        } catch (IOException e) {
            return routes;
        }
    }

    /**
     * Llegeix un fitxer i retorna un ArrayList d'acusats
     * @param path Path del fitxer
     * @return ArrayList d'acusats
     */
    public ArrayList<Acusats> readAcusats(String path) {
        int ok = 0;
        int placesCount = 0;
        ArrayList<Acusats> acusats = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String s : lines) {
                switch (ok) {
                    case 0:
                        placesCount = Integer.parseInt(s);
                        ok = 1;
                        break;
                    case 1:
                        if (placesCount == 0) return acusats;
                        acusats.add(acusatsFromFile(s));
                        placesCount--;
                        break;
                    default:
                        break;
                }
            }
            return acusats;
        } catch (IOException e) {
            return acusats;
        }
    }
}