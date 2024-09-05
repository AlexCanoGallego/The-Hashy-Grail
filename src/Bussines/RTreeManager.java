package Bussines;

import Bussines.RTrees.Bardissa;
import Bussines.RTrees.RTree;
import Bussines.RTrees.Rectangle;
import Persistance.ReadFileBardissaDAO;

import java.util.ArrayList;

public class RTreeManager {
    private RTree r_tree;
    private ReadFileBardissaDAO read_bardissas;
    private static final String PATH_R_TREE = "data/R-Trees/rtreeXXS.paed";

    /**
     * Constructor de la classe RTreeManager
     */
    public RTreeManager() {
        this.read_bardissas = new ReadFileBardissaDAO(PATH_R_TREE);
        this.r_tree = new RTree();
    }

    /**
     * Inicialitza l'arbre R
     */
    public void initializeRTree() {
        this.r_tree = new RTree();
        r_tree.initialize(read_bardissas.readBardissa(PATH_R_TREE));
    }

    /**
     * Guarda l'arbre R
     * @return retorna el RTree.
     */
    public RTree getR_tree(){
        return this.r_tree;
    }

    /**
     * Afegeix una bardissa a l'arbre R
     * @param new_bardissa Bardissa a afegir
     */
    public void addBardissa(Bardissa new_bardissa){
        this.r_tree.add(new_bardissa);
    }

    /**
     * Cerca les bardisses que es troben dins d'un rectangle
     * @param primer Coordenades del primer punt
     * @param segon Coordenades del segon punt
     * @return ArrayList de bardisses
     */
    public ArrayList<Bardissa> cercaPerArea(String primer, String segon) {
        try{
            String[] partsA = primer.split(",");
            String[] partsB = segon.split(",");

            double latA = Double.parseDouble(partsA[0]);
            double longA = Double.parseDouble(partsA[1]);
            double latB = Double.parseDouble(partsB[0]);
            double longB = Double.parseDouble(partsB[1]);

            return this.r_tree.searchBardisses(latA, longA, latB, longB);
        } catch (Exception e){
            System.out.println("\nERROR: Els valors introduits de les coordenades no són correctes.");
            return new ArrayList<Bardissa>();
        }
    }

    /**
     * Cerca les bardisses que es troben a una distància d'un punt
     * @param punt Coordenades del punt
     * @param k quantitat de bardisses a cercar
     */
    public void cercaPerPunt(String punt, int k){
        try{
            String[] parts = punt.split(",");
            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);

            r_tree.searchBardissa(lat, lon, k);
        } catch (Exception e){
            System.out.println("\nERROR: Els valors introduits de les coordenades no són correctes.");
        }
    }

    /**
     * Elimina una bardissa de l'arbre R
     * @param latitud Latitud de la bardissa
     * @param longitud Longitud de la bardissa
     * @return True si s'ha eliminat, false si no.
     */
    public boolean removeBardissa(double latitud, double longitud){
        return r_tree.remove(latitud, longitud);
    }
}
