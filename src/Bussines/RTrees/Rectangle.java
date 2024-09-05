package Bussines.RTrees;
import java.util.ArrayList;

public class Rectangle {
    private ArrayList<Bardissa> bardissas;
    private ArrayList<Rectangle> rectangles;
    private double west_border;
    private double est_border;
    private double north_border;
    private double south_border;
    private static final double MAX_LATITUD = 91;
    private static final double MIN_LATITUD = -91;
    private static final double MAX_LONGITUD = 181;
    private static final double MIN_LONGITUD = -181;

    /**
     * Constructor de la classe Rectangle
     */
    public Rectangle() {
        this.bardissas = new ArrayList<>();
        this.rectangles = null;
        calculateBorders();
    }

    /**
     * Constructor de la classe Rectangle
     * @param principal_node Bardissa principal
     */
    public Rectangle(Bardissa principal_node) {
        this.bardissas = new ArrayList<>();
        this.bardissas.add(principal_node);
        this.rectangles = null;
        calculateBorders();
    }

    /**
     * Constructor de la classe Rectangle
     * @param principal_node Rectangle principal
     */
    public Rectangle(Rectangle principal_node) {
        this.rectangles = new ArrayList<>();
        this.bardissas = null;
        this.rectangles.add(principal_node);
        calculateBorders();
    }

    /**
     * Metode que actualitza el valor de la variable bardissas
     */
    public void setRectangleBorders() {
        for (int i = 0; i < rectangles.size(); i++) {
            if (est_border < rectangles.get(i).getEst()) {
                est_border = rectangles.get(i).getEst();
            }
            if (west_border > rectangles.get(i).getWest()) {
                west_border = rectangles.get(i).getWest();
            }
            if (north_border < rectangles.get(i).getNorth()) {
                north_border = rectangles.get(i).getNorth();
            }
            if (south_border > rectangles.get(i).getSouth()) {
                south_border = rectangles.get(i).getSouth();
            }
        }
    }

    /**
     * Metode que actualitza el valor de la variable bardissas
     */
    public void setBardissaBorders() {
        for (int i = 0; i < bardissas.size(); i++) {
            if (est_border < bardissas.get(i).getLongitud()) {
                est_border = bardissas.get(i).getLongitud();
            } else if (west_border > bardissas.get(i).getLongitud()) {
                west_border = bardissas.get(i).getLongitud();
            }
            if (north_border < bardissas.get(i).getLatitud()) {
                north_border = bardissas.get(i).getLatitud();
            } else if (south_border > bardissas.get(i).getLatitud()) {
                south_border = bardissas.get(i).getLatitud();
            }
        }
    }

    /**
     * Metode que calcula els limits del rectangle
     */
    public void calculateBorders() {
        if (isLeaf()) {
            if (bardissas.size() > 0) {
                if (bardissas.size() > 1) {
                    this.est_border = bardissas.get(0).getLongitud();
                    this.west_border = bardissas.get(0).getLongitud();
                    this.north_border = bardissas.get(0).getLatitud();
                    this.south_border = bardissas.get(0).getLatitud();
                    setBardissaBorders();
                } else {
                    this.est_border = bardissas.get(0).getLongitud();
                    this.west_border = bardissas.get(0).getLongitud();
                    this.north_border = bardissas.get(0).getLatitud();
                    this.south_border = bardissas.get(0).getLatitud();
                }
            } else {
                this.est_border = MAX_LONGITUD;
                this.west_border = MIN_LONGITUD;
                this.north_border = MAX_LATITUD;
                this.south_border = MIN_LATITUD;
            }
        } else {
            if (rectangles.size() > 1) {
                this.est_border = rectangles.get(0).getEst();
                this.west_border = rectangles.get(0).getWest();
                this.north_border = rectangles.get(0).getNorth();
                this.south_border = rectangles.get(0).getSouth();
                setRectangleBorders();
            } else {
                this.est_border = rectangles.get(0).getEst();
                this.west_border = rectangles.get(0).getWest();
                this.north_border = rectangles.get(0).getNorth();
                this.south_border = rectangles.get(0).getSouth();
            }
        }
    }

    /**
     * Metode que retorna el valor de la variable bardissas
     * @param new_bardissa Bardissa
     * @return double
     */
    public double getArea(Bardissa new_bardissa) {
        double high, base;

        if (new_bardissa.getLatitud() > north_border) {
            high = new_bardissa.getLatitud() - south_border;
        } else {
            high = north_border - new_bardissa.getLatitud();
        }

        if (new_bardissa.getLongitud() > est_border) {
            base = new_bardissa.getLatitud() - west_border;
        } else {
            base = est_border - new_bardissa.getLatitud();
        }

        return high * base;
    }

    /**
     * Metode que retorna el valor de la variable rectangles
     * @param new_rectangle Rectangle
     * @return double
     */
    public double getArea(Rectangle new_rectangle) {
        double high_v = north_border;
        double low_v = south_border;
        double high_h = est_border;
        double low_h = west_border;

        if (new_rectangle.getNorth() > north_border) {
            high_v = new_rectangle.getNorth();
        }
        if (new_rectangle.getSouth() < south_border) {
            low_v = new_rectangle.getSouth();
        }
        if (new_rectangle.getEst() > est_border) {
            high_h = new_rectangle.getEst();
        }
        if (new_rectangle.getWest() < west_border) {
            low_h = new_rectangle.getWest();
        }

        return (high_v - low_v) * (high_h - low_h);
    }

    /**
     * Metode que busca la bardissa en una area
     * @param latA double
     * @param longA double
     * @param latB double
     * @param longB double
     * @return boolean
     */
    public boolean interseccio(double latA, double longA, double latB, double longB) {
        double minLat = Math.min(latA, latB);
        double maxLat = Math.max(latA, latB);
        double minLong = Math.min(longA, longB);
        double maxLong = Math.max(longA, longB);

        // Verificar si no hay intersección
        if (this.est_border < minLong || this.west_border > maxLong || this.north_border < minLat || this.south_border > maxLat) {
            return false;
        } else
            return true;   // Verificar si hay intersección
    }

    /**
     * Metode que inserta una bardissa dins del rectangle
     * @param new_bardissa Bardissa
     */
    public void insertInside(Bardissa new_bardissa) {
        bardissas.add(new_bardissa);
    }

    /**
     * Metode que inserta una bardissa fora del rectangle
     * @param new_bardissa Bardissa
     */
    public void insertOutsde(Bardissa new_bardissa) {
        bardissas.add(new_bardissa);
    }

    /**
     * Metode que mira si una bardissa esta dins del rectangle
     * @param bardissa Bardissa
     */
    public boolean isInside(Bardissa bardissa) {
        return bardissa.getLongitud() < est_border && bardissa.getLongitud() > west_border && bardissa.getLatitud() < north_border && bardissa.getLatitud() > south_border;
    }

    /**
     * Metode que mira si un rectangle es Leaf
     */
    public boolean isLeaf() {
        return rectangles == null;
    }

    /**
     * Metode que mira si esta dins del rectangle
     * @param latitud latitud
     * @param longitud longitud
     * @return boolean
     */
    public boolean contains(double latitud, double longitud) {
        return latitud <= north_border && latitud >= south_border && longitud <= est_border && longitud >= west_border;
    }

    /**
     * Metode que retorna Bardisses.
     * @return ArrayList<Bardissa>
     */
    public ArrayList<Bardissa> getBardissas() {
        return bardissas;
    }

    /**
     * Metode que retorna una bardissa
     * @param pos int
     * @return Bardissa
     */
    public Bardissa getBardissas(int pos) {
        return bardissas.get(pos);
    }

    /**
     * Metode que retorna un ArrayList de rectangles
     * @return ArrayList<Rectangle>
     */
    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * Metode que retorna un rectangle
     * @param pos int
     * @return Rectangle
     */
    public Rectangle getRectangles(int pos) {
        return rectangles.get(pos);
    }

    /**
     * Metode que retorna el valor de la variable west_border
     * @return double
     */
    public double getWest() {
        return west_border;
    }

    /**
     * Metode que retorna el valor de la variable est_border
     * @return double
     */
    public double getEst() {
        return est_border;
    }

    /**
     * Metode que retorna el valor de la variable north_border
     * @return double
     */
    public double getNorth() {
        return north_border;
    }

    /**
     * Metode que retorna el valor de la variable south_border
     * @return double
     */
    public double getSouth() {
        return south_border;
    }

    /**
     * Metode que retorna el valor de la variable center_latitud
     * @return
     */
    public double getCenterLatitud() {
        return south_border + ((north_border - south_border) / 2);
    }

    /**
     * Metode que retorna el valor de la variable center_longitud
     * @return
     */
    public double getCenterLongitud() {
        return west_border + ((est_border - west_border) / 2);
    }
}
