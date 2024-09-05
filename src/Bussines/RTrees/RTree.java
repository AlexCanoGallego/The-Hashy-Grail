package Bussines.RTrees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RTree {
    private Rectangle root;
    private static final int MAX_CAPACITY = 5;

    /**
     * Constructor de la classe RTree
     */
    public RTree() {
        this.root = new Rectangle();
    }

    /**
     * Metode que afegeix una bardissa
     * @param bardissas
     */
    public void initialize(ArrayList<Bardissa> bardissas) {
        for (int i = 0; i < bardissas.size(); i++) {
            add(bardissas.get(i));
        }
    }

    /**
     * Metode que calcula la distancia de dues bardisses
     * @param x Bardissa x
     * @param y Bardissa y
     * @return retorna la distancia entre dues bardisses
     */
    private double calculateDistance(Bardissa x, Bardissa y) {
        double high;
        double base;

        if (x.getLongitud() > y.getLongitud()) {
            base = x.getLongitud() - y.getLongitud();
        } else {
            base = y.getLongitud() - x.getLongitud();
        }

        if (x.getLatitud() > y.getLatitud()) {
            high = x.getLatitud() - y.getLatitud();
        } else {
            high = y.getLatitud() - x.getLatitud();
        }

        return high * base;
    }

    /**
     * Metode que calcula la distancia entre dos rectangles
     * @param x Rectangle x
     * @param y Rectangle y
     * @return retorna la distancia entre dos rectangles
     */
    private double calculateDistance(Rectangle x, Rectangle y) {
        return x.getArea(y);
    }

    /**
     * Metode que afegeix una bardissa
     * @param origin Bardissa a afegir
     * @return retorna el rectangle on s'ha afegit la bardissa
     */
    private ArrayList<Rectangle> twoMoreDistantRectangles(Rectangle origin) {
        ArrayList<Rectangle> distant_points = new ArrayList<>();
        double max_distance = 0;

        for (int i = 0; i < origin.getRectangles().size(); i++) {
            for (int j = i + 1; j < origin.getRectangles().size(); j++) {
                double actual_distance = calculateDistance(origin.getRectangles(i), origin.getRectangles(j));
                if (actual_distance > max_distance) {
                    distant_points.clear();
                    distant_points.add(origin.getRectangles(i));
                    distant_points.add(origin.getRectangles(j));
                    max_distance = actual_distance;
                }
            }
        }

        origin.getRectangles().remove(distant_points.get(0));
        origin.getRectangles().remove(distant_points.get(1));

        return distant_points;
    }

    /**
     * Metode que afegeix una bardissa
     * @param origin Bardissa a afegir
     * @return retorna el rectangle on s'ha afegit la bardissa
     */
    private ArrayList<Bardissa> twoMoreDistantBardissas(Rectangle origin) {
        ArrayList<Bardissa> distant_points = new ArrayList<>();
        double max_distance = 0;

        for (int i = 0; i < origin.getBardissas().size(); i++) {
            for (int j = i + 1; j < origin.getBardissas().size(); j++) {
                double actual_distance = calculateDistance(origin.getBardissas().get(i), origin.getBardissas().get(j));
                if (actual_distance > max_distance) {
                    distant_points.clear();
                    distant_points.add(origin.getBardissas().get(i));
                    distant_points.add(origin.getBardissas().get(j));
                    max_distance = actual_distance;
                }
            }
        }

        origin.getBardissas().remove(distant_points.get(0));
        origin.getBardissas().remove(distant_points.get(1));

        return distant_points;
    }

    /**
     * Metode que restrucutra la bardissa
     * @param actual_rectangle Rectangle actual
     * @param distant_bardissas Bardisses mes llunyanes
     * @param other_bardissas Bardisses restants
     */
    private void restructureBardissa(Rectangle actual_rectangle, ArrayList<Bardissa> distant_bardissas, ArrayList<Bardissa> other_bardissas) {
        actual_rectangle.getRectangles().add(new Rectangle(distant_bardissas.get(0)));
        actual_rectangle.getRectangles().add(new Rectangle(distant_bardissas.get(1)));

        for (int i = 0; i < other_bardissas.size(); i++) {
            if (actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 2).getArea(other_bardissas.get(i)) < actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 1).getArea(other_bardissas.get(i))) {
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 2).getBardissas().add(other_bardissas.get(i));
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 2).calculateBorders();
            } else {
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 1).getBardissas().add(other_bardissas.get(i));
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 1).calculateBorders();
            }
        }
    }

    /**
     * Metode que restrucutra el rectangle
     * @param actual_rectangle Rectangle actual
     * @param distant_rectangles Rectangles mes llunyanes
     * @param other_rectangles Rectangles restants
     */
    private void restructureRectangle(Rectangle actual_rectangle, ArrayList<Rectangle> distant_rectangles, ArrayList<Rectangle> other_rectangles) {
        actual_rectangle.getRectangles().add(new Rectangle(distant_rectangles.get(0)));
        actual_rectangle.getRectangles().add(new Rectangle(distant_rectangles.get(1)));

        for (int i = 0; i < other_rectangles.size(); i++) {
            if (actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 2).getArea(other_rectangles.get(i)) < actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 1).getArea(other_rectangles.get(i))) {
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 2).getRectangles().add(other_rectangles.get(i));
            } else {
                actual_rectangle.getRectangles(actual_rectangle.getRectangles().size() - 1).getRectangles().add(other_rectangles.get(i));
            }
        }
    }

    /**
     * Metode que busca el rectangle amb mes bardisses
     * @param actual_rectangle Rectangle actual
     * @return retorna el rectangle amb mes bardisses
     */
    private Rectangle searchOverflowRectangle(Rectangle actual_rectangle) {
        int i = 0;

        if (actual_rectangle.getRectangles(0).isLeaf()) {
            while (true) {
                if (actual_rectangle.getRectangles(i).getBardissas().size() > MAX_CAPACITY) {
                    return actual_rectangle.getRectangles(i);
                }
                i++;
            }
        } else {
            while (true) {
                if (actual_rectangle.getRectangles(i).getRectangles().size() > MAX_CAPACITY) {
                    return actual_rectangle.getRectangles(i);
                }
                i++;
            }
        }
    }

    /**
     * Metode que divideix el rectangle
     * @param actual_rectangle Rectangle actual
     */
    private void split(Rectangle actual_rectangle) {
        Rectangle rectangle_overflow = searchOverflowRectangle(actual_rectangle);

        if (rectangle_overflow.isLeaf()) {
            ArrayList<Bardissa> distant_bardissas = twoMoreDistantBardissas(rectangle_overflow);
            ArrayList<Bardissa> other_bardissas = rectangle_overflow.getBardissas();
            actual_rectangle.getRectangles().remove(rectangle_overflow);
            restructureBardissa(actual_rectangle, distant_bardissas, other_bardissas);
        } else {
            ArrayList<Rectangle> distant_rectangles = twoMoreDistantRectangles(rectangle_overflow);
            ArrayList<Rectangle> other_rectangles = rectangle_overflow.getRectangles();
            actual_rectangle.getRectangles().remove(rectangle_overflow);
            restructureRectangle(actual_rectangle, distant_rectangles, other_rectangles);
        }
        actual_rectangle.calculateBorders();
    }

    /**
     * Metode que retorna el root
     * @return retorna el root
     */
    public Rectangle getRoot(){
        return this.root;
    }

    /**
     * Metode que busca bardisses en un rectangle
     * @param rectangle Rectangle on buscar
     * @param latA Latitud A
     * @param longA Longitud A
     * @param latB Latitud B
     * @param longB Longitud B
     * @param bardisses ArrayList de bardisses
     */
    public ArrayList<Bardissa> searchBardisses(double latA, double longA, double latB, double longB){
        ArrayList<Bardissa> bar = new ArrayList<>();
        searchBardissasRecursiu(root, latA, longA, latB, longB, bar);
        return bar;
    }

    /**
     * Metode que busca bardisses en un rectangle
     * @param rectangle Rectangle on buscar
     * @param latA Latitud A
     * @param longA Longitud A
     * @param latB Latitud B
     * @param longB Longitud B
     * @param bardisses ArrayList de bardisses
     */
    public void searchBardissasRecursiu(Rectangle rectangle, double latA, double longA, double latB, double longB, ArrayList<Bardissa> bardisses){
        if(rectangle.interseccio(latA, longA, latB, longB)){
            if(rectangle.isLeaf()) {
                for (Bardissa bardissa : rectangle.getBardissas()) {
                    if (bardissa.getLatitud() <= latA && bardissa.getLatitud() >= latB) {
                        if (bardissa.getLongitud() >= longA && bardissa.getLongitud() <= longB) {
                            bardisses.add(bardissa);
                        }
                    }
                }
            } else{
                for(Rectangle childRectangle : rectangle.getRectangles()){
                    searchBardissasRecursiu(childRectangle, latA, longA, latB, longB, bardisses);
                }
            }
        }
    }

    /**
     * Metode que busca bardisses en un rectangle
     * @param actual_rectangle Rectangle on buscar
     * @param new_bardissa Bardissa a afegir
     * @return retorna el rectangle amb mes area
     */
    private Rectangle getBestRectangle(Rectangle actual_rectangle, Bardissa new_bardissa) {
        int best_rectangle = 0;
        int i = 0;
        boolean trobat = false;

        while (i < actual_rectangle.getRectangles().size() && !trobat) {
            if (actual_rectangle.getRectangles(i).isInside(new_bardissa)) {
                trobat = true;
                best_rectangle = i;
            } else {
                i++;
            }
        }

        if (!trobat) {
            double best_area = actual_rectangle.getRectangles(0).getArea(new_bardissa);
            i = 1;

            while (i < actual_rectangle.getRectangles().size()) {
                double actual_area = actual_rectangle.getRectangles(i).getArea(new_bardissa);
                if (best_area > actual_area) {
                    best_area = actual_area;
                    best_rectangle = i;
                }
                i++;
            }
        }

        return actual_rectangle.getRectangles(best_rectangle);
    }

    /**
     * Metode que afegeix una bardissa
     * @param actual_rectangle Rectangle actual
     * @param new_bardissa Bardissa a afegir
     * @return retorna si s'ha fet split
     */
    private boolean addBardissa(Rectangle actual_rectangle, Bardissa new_bardissa) {
        boolean split = false;

        if (actual_rectangle.isInside(new_bardissa)) {  // Si el conte
            if (actual_rectangle.isLeaf()) {    // Si es fulla inserta
                actual_rectangle.insertInside(new_bardissa);
            } else {                            // Si no es fulla
                split = addBardissa(getBestRectangle(actual_rectangle, new_bardissa), new_bardissa);
            }
        } else {    // Si no el conte
            if (actual_rectangle.isLeaf()) {
                actual_rectangle.insertOutsde(new_bardissa);
            } else {
                split = addBardissa(getBestRectangle(actual_rectangle, new_bardissa), new_bardissa);
            }
        }
        actual_rectangle.calculateBorders();

        if (split) {
            split(actual_rectangle);
        }

        if (actual_rectangle.isLeaf()) {
            return actual_rectangle.getBardissas().size() > MAX_CAPACITY;
        } else {
            return actual_rectangle.getRectangles().size() > MAX_CAPACITY;
        }
    }

    /**
     * Metode que afegeix una bardissa
     * @param new_bardissa Bardissa a afegir
     */
    public void add(Bardissa new_bardissa) {
        if (addBardissa(root, new_bardissa)) {
            Rectangle new_root = new Rectangle(root);
            split(new_root);
            root = new_root;
        }
    }

    /**
     * Metode que elimina una bardissa
     * @param latitud Latitud de la bardissa
     * @param longitud Longitud de la bardissa
     */
    private boolean removeBardissa (Rectangle actual_rectangle, double latitud, double longitud) {
        int i = 0;

        if (actual_rectangle.isLeaf()) {
            while (i < actual_rectangle.getBardissas().size()) {
                if (actual_rectangle.getBardissas(i).compare(latitud, longitud)) {
                    actual_rectangle.getBardissas().remove(i);
                    actual_rectangle.calculateBorders();
                    if (actual_rectangle.getBardissas().isEmpty()) {
                        return true;
                    }
                }
                i++;
            }
        } else {
            while (i < actual_rectangle.getRectangles().size()) {
                if (actual_rectangle.getRectangles(i).contains(latitud, longitud)) {
                    if (removeBardissa(actual_rectangle.getRectangles(i), latitud, longitud)) {
                        actual_rectangle.getRectangles().remove(i);
                        actual_rectangle.calculateBorders();
                        if (actual_rectangle.getRectangles().size() <= 1) {
                            return true;
                        }
                    }
                }
                i++;
            }
        }

        return false;
    }

    /**
     * Metode que reestructura l'arbre
     * @param actual_rectangle Rectangle actual
     * @return retorna el rectangle amb mes area
     */
    private Rectangle rebalance(Rectangle actual_rectangle) {
        if (!actual_rectangle.isLeaf() && actual_rectangle.getRectangles().size() == 1) {
            return rebalance(actual_rectangle.getRectangles(0));
        }
        return actual_rectangle;
    }

    /**
     * Metode que elimina una bardissa
     * @param latitud Latitud de la bardissa
     * @param longitud Longitud de la bardissa
     */
    public boolean remove(double latitud, double longitud) {
        if (removeBardissa(root, latitud, longitud)) {
            root = rebalance(root);
            return true;
        }
        return false;
    }

    /**
     * Metode que busca bardisses en un rectangle
     * @param lat Latitud del punt
     * @param lon Longitud del punt
     * @param k Numero de bardisses a buscar
     */
    public void searchBardissa(double lat, double lon, int k){
        List<Bardissa> bardissesProperes = buscarMesProperes(root, lat, lon, k);
        int circleCount = 0;
        int squareCount = 0;
        int colors = 0;
        int totalred = 0;
        int totalgreen = 0;
        int totalblue = 0;

        for(Bardissa bardissa : bardissesProperes){
            if(bardissa.getTipus() == 0){
                circleCount++;
            } else {
                squareCount++;
            }

            //Mitjana de colors
            colors++;
            totalred += bardissa.getColor().getRed();
            totalgreen += bardissa.getColor().getGreen();
            totalblue += bardissa.getColor().getBlue();
        }

        if(colors > 0){
            if(circleCount > squareCount){
                System.out.println("\nTipus majoritari: CIRCLE");
            } else if(squareCount > circleCount){
                System.out.println("\nTipus majoritari: SQUARE");
            } else {
                System.out.println("\nTipus majoritari: EMPAT");
            }

            int mitjanaRed = totalred / colors;
            int mitjanaGreen = totalgreen / colors;
            int mitjanaBlue = totalblue / colors;

            String hex = String.format("#%02X%02X%02X", mitjanaRed, mitjanaGreen, mitjanaBlue);
            System.out.println("Color mitjà: " + hex);
        } else {
            System.out.println("\nNo hi ha bardisses aprop de la zona");
        }
    }

    /**
     * Metode que busca bardisses mes properes en un rectangle
     * @param root Rectangle actual
     * @param lat Latitud del punt
     * @param lon Longitud del punt
     * @param k Numero de bardisses a buscar
     */
    public List<Bardissa> buscarMesProperes(Rectangle root, double lat, double lon, int k){
        List<Bardissa> bardisses = new ArrayList<>();

        for(Rectangle rectangle : root.getRectangles()){
            if(rectangle.isLeaf()) {
                for (Bardissa bardissa : rectangle.getBardissas()) {
                    bardisses.add(bardissa);
                }
            } else{
                for(Rectangle childRectangle : rectangle.getRectangles()){
                    buscarMesProperes(childRectangle, lat, lon, k);
                }
            }
        }

        bardisses.sort(Comparator.comparingDouble(b -> calculateDistance(lat, lon, b.getLatitud(), b.getLongitud())));

        return bardisses.subList(0, Math.min(k, bardisses.size()));
    }

    /**
     * Metode que calcula la distancia entre dos punts
     * @param lat1 Latitud del punt 1
     * @param lon1 Longitud del punt 1
     * @param lat2 Latitud del punt 2
     * @param lon2 Longitud del punt 2
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        return Math.sqrt(dLat * dLat + dLon * dLon);
    }
}
