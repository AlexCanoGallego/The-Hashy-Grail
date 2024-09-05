package Bussines.Graphs;

import java.util.ArrayList;

//NODE
public class InterestingPlaces {
    private int id;
    private String name;
    private String reign;
    private String climate;
    private Boolean visitat;
    private ArrayList<Graph> graf = new ArrayList<Graph>();

    /**
     * Constructor de la classe InterestingPlaces
     * @param id Identificador del lloc
     * @param name Nom del lloc
     * @param reign Regne del lloc
     * @param climate Clima del lloc
     */
    public InterestingPlaces(int id, String name, String reign, String climate) {
        this.id = id;
        this.name = name;
        this.reign = reign;
        this.climate = climate;
        this.visitat=false;
    }

    /**
     * Metode que afegueix un Graf
     * @param count Identificador del graf
     * @param routes Rutes del graf
     * @param places Llocs del graf
     */
    public void setGraf(int count, KnownRoutes routes, InterestingPlaces places) {
        Graph graph = new Graph(routes, places);
        this.graf.add(count, graph);
    }

    /**
     * Metode que afegueix un Graf a una ruta
     * @param count Identificador del graf
     * @param routes Rutes del graf
     */
    public void setGrafRoutes (int count, KnownRoutes routes){
        this.graf.get(count).setRoutes(routes);
    }

    /**
     * Metode que afegueix un Graf a un lloc
     * @param count Identificador del graf
     * @param places Llocs del graf
     */
    public void setGrafPlaces (int count, InterestingPlaces places){
        this.graf.get(count).setPlace(places);
    }

    /**
     * Metode que retorna un ArrayList de Graf
     * @return ArrayList de Graf
     */
    public ArrayList<Graph> getGraf() {
        return graf;
    }

    /**
     * Metode que retorna un ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Metode que afegueix un ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metode que retorna un nom
     * @return Nom
     */
    public String getName() {
        return name;
    }

    /**
     * Metode que afegueix un nom
     * @param name Nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metode que aretorna un regne
     * @return Regne
     */
    public String getReign() {
        return reign;
    }

    /**
     * Metode que afegueix un regne
     * @param reign Regne
     */
    public void setReign(String reign) {
        this.reign = reign;
    }

    /**
     * Metode que retorna un clima
     * @return Clima
     */
    public String getClimate() {
        return climate;
    }

    /**
     * Metode que afegueix un clima
     * @param climate Clima
     */
    public void setClimate(String climate) {
        this.climate = climate;
    }

    /**
     * Metode que retorna un boolea
     * @return Boolea
     */
    public Boolean getVisitat() {
        return visitat;
    }

    /**
     * Metode que afegueix un boolea
     * @param visitat Boolea
     */
    public void setVisitat(Boolean visitat) {
        this.visitat = visitat;
    }
}