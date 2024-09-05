package Bussines;

import Bussines.Graphs.Graph;
import Bussines.Graphs.InterestingPlaces;
import Bussines.Graphs.KnownRoutes;
import Bussines.HashTable.Acusats;
import Bussines.HashTable.TablaHash;
import Bussines.Trees.Habitant;
import Bussines.Trees.Tree;
import Persistance.ReadFile;
import Persistance.ReadFileHabitantsDAO;

import java.util.ArrayList;
import java.util.LinkedList;

public class Manager {
    private static final String PATH_GRAPH = "data/Graphs/graphsXXS.paed";
    private static final String PATH_HASHTABLE = "data/Tables/tablesXXS.paed";
    private static final String PATH_TREE = "data/Trees/treeXXS.paed";
    private ReadFile read_file;
    private ReadFileHabitantsDAO read_habitants;
    private Graph graf;
    private Tree tree;
    private TablaHash tablaHash;
    private ArrayList<InterestingPlaces> interesting_places;
    private ArrayList<KnownRoutes> known_routes;
    private ArrayList<Acusats> acusats;

    /**
     * Constructor de la classe Manager
     */
    public Manager() {
        this.read_file = new ReadFile();
        // todo: esto creo que no deberia quedarse en la RAM y tampoco cargar info a la RAM si no se usa
        this.interesting_places = read_file.readPlaces(PATH_GRAPH);
        this.known_routes = read_file.readRoutes(PATH_GRAPH);
        this.graf = new Graph();
        // (para el grafXXL) 512 ^ 2 * 5 (floats dintre de la classe KnownRoutes) * 4 (bytes por cada float)
        this.read_habitants = new ReadFileHabitantsDAO(PATH_TREE);
        this.tree = null;
        this.tablaHash=null;
        //this.acusats=read_file.readAcusats(PATH_GRAPH);
    }

    // -------------------------------------------- GRAFS --------------------------------------------------------------

    /**
     * Inicialitza el graf
     */
    public ArrayList<InterestingPlaces> getGraf() {
        interesting_places = graf.makeGraph(known_routes, interesting_places);
        return interesting_places;
    }

    // -------------------------------------------- TREES --------------------------------------------------------------

    /**
     * Inicialitza l'arbre
     */
    public Tree initializeTree() {
        this.tree = new Tree();
        ArrayList<Habitant> habitants = read_habitants.readHabitant(PATH_TREE);
        for (int i = 0; i < habitants.size(); i++) {
            tree.initialize(habitants.get(i));
        }
        return tree;
    }

    /**
     * Guarda l'arbre
     * @return retorna el Tree.
     */
    public Tree getTree(){
        return this.tree;
    }

    /**
     * Elimina l'arbre
     */
    public void removeTree() {
        tree.removeAll();
        tree = null;
    }

    /**
     * Afegeix un habitant a l'arbre
     * @param new_habitant habitant a afegir
     * @return retorna true si s'ha afegit correctament, false si no.
     */
    public boolean addHabitant(Habitant new_habitant) {
        return tree.add(new_habitant);
    }

    /**
     * Elimina un habitant de l'arbre
     * @param wanted_id id del habitant a eliminar
     * @return retorna el nom del habitant eliminat
     */
    public String removeHabitant(int wanted_id) {
        return tree.remove(wanted_id);
    }

    /**
     * Busca un habitant a l'arbre
     * @param min_weight pes minim del habitant
     * @param max_weight pes maxim del habitant
     * @return retorna una llista amb els habitants que compleixen els requisits
     */
    public LinkedList<Habitant> getWitches(double min_weight, double max_weight) {
        return tree.findWitches(min_weight, max_weight);
    }

    /**
     * Depen del tipus de objecte que ens introdueixin es cercará un tipus d'habitant o un altre
     * @param weight el pes introduit pel usuari
     * @param type el tipus que depenent de quin tipus sigui buscara un habitant amb major, menor o amb el mateix pes
     * @return retorna el habitant que s'ha de mostrar
     */
    public ArrayList<Habitant> getWeightWitches(double weight, String type) {
        switch (type){
            case "DUCK":
                //Habitants que pesin igual
                return tree.findEqualWitches(weight);
            case "WOOD":
                //Primer pes que pesi menys
                return tree.findMinorWitches(weight);
            case "STONE":
                //  Primer pes que pesi mes
                return tree.findMajorWitches(weight);

        }
        return null;
    }
// -------------------------------------------- HASH TABLE --------------------------------------------------------------
    /**
     * Inicialitza la taula de hash
     */
    public TablaHash initializeHashTable() {
        this.tablaHash = new TablaHash(1000);
        ArrayList<Acusats> acusats = read_file.readAcusats(PATH_HASHTABLE);
        for (int i = 0; i < acusats.size(); i++) {
            tablaHash.put(acusats.get(i));
        }
        return tablaHash;
    }
}
