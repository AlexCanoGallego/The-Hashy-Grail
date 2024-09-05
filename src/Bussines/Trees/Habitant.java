package Bussines.Trees;

public class Habitant {
    private int id;
    private String name;
    private double weight;
    private String kingdom;
    private Habitant major_node;
    private Habitant minor_node;
    private int height;


    /**
     * Constructor de la classe Habitant
     * @param id Identificador de l'habitant
     * @param name Nom de l'habitant
     * @param weight Pes de l'habitant
     * @param kingdom Regne de l'habitant
     */
    public Habitant(int id, String name, double weight, String kingdom) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.kingdom = kingdom;
        this.major_node = null;
        this.minor_node = null;
        this.height = 0;
    }

    /**
     * Constructor de la classe Habitant
     */
    public Habitant() {
        this.id = -1;
        this.name = null;
        this.weight = -1;
        this.kingdom = null;
        this.major_node = null;
        this.minor_node = null;
        this.height = 0;
    }

    /**
     * Metode que afegueix un ID
     * @param id Identificador de l'habitant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metode que afegueix un nom
     * @param name Nom de l'habitant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metode que afegueix un pes
     * @param weight Pes de l'habitant
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Metode que afegueix un regne
     * @param kingdom Regne de l'habitant
     */
    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    /**
     * Metode que retorna el nom de l'habitant
     * @return String amb el nom de l'habitant
     */
    public String getName() {
        return name;
    }

    /**
     * Metode que retorna el pes de l'habitant
     * @return Double amb el pes de l'habitant
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Metode que retorna el regne de l'habitant
     * @return String amb el regne de l'habitant
     */
    public String getKingdom() {
        return kingdom;
    }

    /**
     * Metode que retorna l'identificador de l'habitant
     * @return Integer amb l'identificador de l'habitant
     */
    public void setMajorNode(Habitant major_node) {
        this.major_node = major_node;
    }

    /**
     * Metode que afegueix un node menor
     * @param minor_node Node menor
     */
    public void setMinorNode(Habitant minor_node) {
        this.minor_node = minor_node;
    }

    /**
     * Metode que retorna el node major
     * @return Node major
     */
    public Habitant getMajorNode() {
        return major_node;
    }

    /**
     * Metode que retorna el node menor
     * @return Node menor
     */
    public Habitant getMinorNode() {
        return minor_node;
    }

    /**
     * Metode que retorna l'identificador de l'habitant
     * @return Integer amb l'identificador de l'habitant
     */
    public int getId() {
        return id;
    }

    /**
     * Metode que afegueix una altura
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Metode que retorna l'altura
     * @return Integer amb l'altura
     */
    public int getHeight() {
        return height;
    }
}
