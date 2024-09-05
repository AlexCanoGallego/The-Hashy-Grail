package Bussines.Trees;

import java.util.ArrayList;
import java.util.LinkedList;

public class Tree {
    private Habitant root;

    /**
     * Constructor de la classe Tree
     */
    public Tree() {
        root = null;
    }

    /**
     * Metode que retorna l'alçada d'un habitant (node)
     * @param current_habitant Habitant
     * @param new_habitant Habitant
     * @return Habitant
     */
    private Habitant addHabitant(Habitant current_habitant, Habitant new_habitant) {
        if (current_habitant == null) {
            return new_habitant;
        } else if (current_habitant.getWeight() < new_habitant.getWeight()) {
           current_habitant.setMajorNode(addHabitant(current_habitant.getMajorNode(), new_habitant));

        } else {    // current_habitant.getWeight() >= new_habitant.getWeight()
            current_habitant.setMinorNode(addHabitant(current_habitant.getMinorNode(), new_habitant));
        }

        updateHeightHabitant(current_habitant);
        return rebalanceNode(current_habitant);
    }

    /**
     * Metode que actualitza l'alçada d'un habitant (node)
     * @param current_habitant Habitant
     * @param id_wanted int
     * @return boolean
     */
    private boolean idAlreadyExists(Habitant current_habitant, int id_wanted) {
        if (current_habitant.getId() == id_wanted) {
            return true;
        }
        if (current_habitant.getMinorNode() != null) {
            if (idAlreadyExists(current_habitant.getMinorNode(), id_wanted)) {
                return true;
            }
        }
        if (current_habitant.getMajorNode() != null) {
            return idAlreadyExists(current_habitant.getMajorNode(), id_wanted);
        }
        return false;
    }

    /**
     * Metode que afageix un habitant (node) a l'arbre
     * @param new_habitant Habitant
     * @return boolean
     */
    public boolean add(Habitant new_habitant) {
        if (!idAlreadyExists(root, new_habitant.getId())) {
            root = addHabitant(root, new_habitant);
            return true;
        }
        return false;
    }

    /**
     * Metode que retorna l'alçada d'un habitant (node)
     * @return habitant.
     */
    public Habitant getHabitant(){
        return this.root;
    }

    /**
     * Metode que inicialitza un habitant (node)
     * @param new_habitant Habitant
     */
    public void initialize(Habitant new_habitant) {
        root = addHabitant(root, new_habitant);
    }

    /**
     * Metode replaceInorder
     * @param current_habitant Habitant
     * @return habitant
     */
    private Habitant replaceInorder(Habitant current_habitant) {
        if (current_habitant.getMinorNode() != null) {
            return replaceInorder(current_habitant.getMinorNode());
        }
        return current_habitant;
    }

    /**
     * Metode removeInorder
     * @param current_habitant Habitant
     * @return habitant
     */
    public Habitant removeInorder(Habitant current_habitant) {
        if (current_habitant.getMinorNode() != null) {
            current_habitant.setMinorNode(removeInorder(current_habitant.getMinorNode()));
        } else {
            current_habitant = removeHabitant(current_habitant);
        }

        updateHeightHabitant(current_habitant);
        return rebalanceNode(current_habitant);
    }

    /**
     * Metode que elimina un habitant (node) de l'arbre
     * @param habitant Habitant
     * @return habitant
     */
    private Habitant removeHabitant(Habitant habitant) {
        Habitant replace_habitant;
        if (habitant.getMajorNode() == null && habitant.getMinorNode() == null) {
            return null;
        } else if (habitant.getMinorNode() == null) {
            replace_habitant = habitant.getMajorNode();
        } else if (habitant.getMajorNode() == null) {
            replace_habitant = habitant.getMinorNode();
        } else {
            Habitant inorder_habitant = replaceInorder(habitant.getMajorNode());
            replace_habitant = new Habitant();

            replace_habitant.setId(inorder_habitant.getId());
            replace_habitant.setName(inorder_habitant.getName());
            replace_habitant.setWeight(inorder_habitant.getWeight());
            replace_habitant.setKingdom(inorder_habitant.getKingdom());

            replace_habitant.setMajorNode(removeInorder(habitant.getMajorNode()));
        }

        updateHeightHabitant(replace_habitant);
        return rebalanceNode(replace_habitant);
    }

    /**
     * Metode que elimina un habitant (node) de l'arbre per ID
     * @param current_habitant Habitant
     * @param wanted_id int id
     * @return habitant
     */
    private Habitant searchRemoveId(Habitant current_habitant, int wanted_id) {
        if (current_habitant == null) { //
            return null;
        } else if (current_habitant.getId() == wanted_id) {
            current_habitant = removeHabitant(current_habitant);

            return current_habitant;
        } else {
            if (current_habitant.getMinorNode() != null) {
                current_habitant.setMinorNode(searchRemoveId(current_habitant.getMinorNode(), wanted_id));
            }
            if (current_habitant.getMajorNode() != null) {
                current_habitant.setMajorNode(searchRemoveId(current_habitant.getMajorNode(), wanted_id));
            }

            updateHeightHabitant(current_habitant);
            return rebalanceNode(current_habitant);
        }
    }

    /**
     * Metode que busca un habitant (node) per nom
     * @param current_habitant Habitant
     * @param wanted_id int id
     * @return habitant
     */
    private String searchWantedName(Habitant current_habitant, int wanted_id) {
        if (current_habitant == null) {
            return null;
        } else if (current_habitant.getId() == wanted_id) {
            return current_habitant.getName();
        } else {
            if (current_habitant.getMinorNode() != null) {
                String wanted_name = searchWantedName(current_habitant.getMinorNode(), wanted_id);
                if (wanted_name != null) {
                    return wanted_name;
                }
            }
            if (current_habitant.getMajorNode() != null) {
                String wanted_name = searchWantedName(current_habitant.getMajorNode(), wanted_id);
                if (wanted_name != null) {
                    return wanted_name;
                }
            }
            return null;
        }
    }

    /**
     * Metode que elimina un habitant (node) de l'arbre.
     * @param wanted_id
     * @return
     */
    public String remove(int wanted_id) {  // Retorna si s'ha trobat l'id
        String wanted_name = searchWantedName(root, wanted_id);
        if (wanted_name != null) {
            root = searchRemoveId(root, wanted_id);
        }
        return wanted_name;
    }

    /**
     * Metode que elimina tots els habitants (nodes) de l'arbre
     * @param current_habitant Habitant
     * @return habitant
     */
    private Habitant removeAllNodes(Habitant current_habitant) {
        if (current_habitant.getMinorNode() != null) {
            current_habitant.setMinorNode(removeAllNodes(current_habitant.getMinorNode()));
        }
        if (current_habitant.getMajorNode() != null) {
            current_habitant.setMajorNode(removeAllNodes(current_habitant.getMajorNode()));
        }
        return null;
    }

    /**
     * Metode que elimina tots els habitants (nodes) de l'arbre
     */
    public void removeAll() {
        while (root != null) {
            root = removeAllNodes(root);
        }
    }

    /**
     * Metode que retorna un LinkedList amb tots els habitants (nodes) de l'arbre
     * @param current_habitant Habitant
     * @param batuda LinkedList
     * @param min_weight double
     * @param max_weight double
     * @return batuda
     */
    private LinkedList<Habitant> findPostorderWeight(Habitant current_habitant, LinkedList<Habitant> batuda, double min_weight, double max_weight) {
        if (current_habitant.getMajorNode() != null) {
            batuda = findPostorderWeight(current_habitant.getMajorNode(), batuda, min_weight, max_weight);
        }

        if (current_habitant.getWeight() < min_weight) {
            return batuda;
        } else if (current_habitant.getWeight() <= max_weight) {
            batuda.add(current_habitant);
        }

        if (current_habitant.getMinorNode() != null) {
            batuda = findPostorderWeight(current_habitant.getMinorNode(), batuda, min_weight, max_weight);
        }

        return batuda;
    }

    /**
     * Metode que retorna un LinkedList amb tots els habitants (nodes) de l'arbre
     * @param current_habitant Habitant
     * @param batuda LinkedList
     * @param min_weight double
     * @param max_weight double
     * @return batuda
     */
    private LinkedList<Habitant> findPreorderWeight(Habitant current_habitant, LinkedList<Habitant> batuda, double min_weight, double max_weight) {
        if (current_habitant.getMinorNode() != null) {
            batuda = findPreorderWeight(current_habitant.getMinorNode(), batuda, min_weight, max_weight);
        }

        if (current_habitant.getWeight() > max_weight) {
            return batuda;
        } else if (current_habitant.getWeight() >= min_weight) {
            batuda.add(current_habitant);
        }

        if (current_habitant.getMajorNode() != null) {
            batuda = findPreorderWeight(current_habitant.getMajorNode(), batuda, min_weight, max_weight);
        }

        return batuda;
    }

    /**
     * Metode que retorna double amb el pes minim d'un habitant (node)
     * @param current_habitant Habitant
     * @return double
     */
    private double minWeightHabitant(Habitant current_habitant) {
        if (current_habitant.getMinorNode() == null) {
            return current_habitant.getWeight();
        }
        return minWeightHabitant(current_habitant.getMinorNode());
    }

    /**
     * Metode que retorna double amb el pes maxim d'un habitant (node)
     * @param current_habitant Habitant
     * @return double
     */
    private double maxWeightHabitant(Habitant current_habitant) {
        if (current_habitant.getMajorNode() == null) {
            return current_habitant.getWeight();
        }
        return maxWeightHabitant(current_habitant.getMajorNode());
    }

    /**
     * Metode que retorna un LinkedList amb tots els habitants (nodes) de l'arbre
     * @param min_weight double
     * @param max_weight double
     * @return batuda
     */
    public LinkedList<Habitant> findWitches(double min_weight, double max_weight) {
        double lightest_habitant = minWeightHabitant(root);
        double heaviest_habitant = maxWeightHabitant(root);

        if (lightest_habitant > max_weight || heaviest_habitant < min_weight) {
            return null;
        } else if ((heaviest_habitant - lightest_habitant) / 2 < (max_weight - min_weight) / 2) {
            return findPreorderWeight(root, new LinkedList<>(), min_weight, max_weight);
        } else {
            return findPostorderWeight(root, new LinkedList<>(), min_weight, max_weight);
        }
    }

    /**
     * Metode que rotaciona un habitant (node) cap a la dreta
     * @param habitant Habitant
     * @return habitant
     */
    private Habitant rightRotation(Habitant habitant) {
        Habitant new_root = habitant.getMinorNode();

        habitant.setMinorNode(new_root.getMajorNode());
        new_root.setMajorNode(habitant);

        updateHeightHabitant(habitant);
        updateHeightHabitant(new_root);

        return new_root;
    }

    /**
     * Metode que rotaciona un habitant (node) cap a l'esquerra
     * @param habitant Habitant
     * @return habitant
     */
    private Habitant leftRotation(Habitant habitant) {
        Habitant new_root = habitant.getMajorNode();

        habitant.setMajorNode(new_root.getMinorNode());
        new_root.setMinorNode(habitant);

        updateHeightHabitant(habitant);
        updateHeightHabitant(new_root);

        return new_root;
    }

    /**
     * Metode que rebalanceja un habitant (node)
     * @param habitant Habitant
     * @return habitant
     */
    private Habitant rebalanceNode(Habitant habitant) {
        if (getNodeBalanceFactor(habitant) >= 2) {          // Right heavy
            if (getNodeBalanceFactor(habitant.getMajorNode()) == -1) {  // Right-Left rotation
                habitant.setMajorNode(rightRotation(habitant.getMajorNode()));
            }
            habitant = leftRotation(habitant);
        } else if (getNodeBalanceFactor(habitant) <= -2) {  // Left heavy
            if (getNodeBalanceFactor(habitant.getMinorNode()) == 1) {   // Left-Right rotation
                habitant.setMinorNode(leftRotation(habitant.getMinorNode()));
            }
            habitant = rightRotation(habitant);
        }

        return habitant;
    }

    /**
     * Metode que agafa un habitant amb el seu height.
     * @param habitant Habitant
     * @return int
     */
    private int getNodeHeight(Habitant habitant) {
        if (habitant != null) {
            return habitant.getHeight();
        } else {
            return -1;
        }
    }

    /**
     * Metode que actualitza el height d'un habitant (node)
     * @param habitant Habitant
     */
    private void updateHeightHabitant(Habitant habitant) {
        habitant.setHeight(Math.max(getNodeHeight(habitant.getMajorNode()), getNodeHeight(habitant.getMinorNode())) + 1);
    }

    /**
     * Metode que retorna el factor de balanceig d'un habitant (node)
     * @param habitant Habitant
     * @return int
     */
    private int getNodeBalanceFactor(Habitant habitant) {
        return getNodeHeight(habitant.getMajorNode()) - getNodeHeight(habitant.getMinorNode());
    }

    /**
     * Metode que mitjançant la tecnica de inordre es trobara un node de forma recursiva que cumpleixi amb les condicions,en aquesat cas que
     * ,els habitants que pesin igual que l'objecte
     * @param current_habitant habitant que s'acaba de comprobar
     * @param equalWeight habitant que s'ha trobat i que constitueix l'habitant trobat
     * @param weight valor de pes que te el objecte i que es el mateix que s'ha de trpbar en un habitant
     * @return retorna el habitant que s'ha trobat que te  menor pes que el del objecte
     */
    private Habitant findPreOrderMinorWeight(Habitant current_habitant, Habitant equalWeight, double weight) {
        if (current_habitant.getMinorNode() != null) {
            equalWeight = findPreOrderMinorWeight(current_habitant.getMinorNode(), equalWeight, weight);
        }
        if (Double.compare(current_habitant.getWeight(),weight)==-1) {
            equalWeight=current_habitant;
            return equalWeight;
        }
        if (current_habitant.getMajorNode() != null) {
            equalWeight = findPreOrderMinorWeight(current_habitant.getMajorNode(), equalWeight, weight);
        }
        return equalWeight;
    }

    /**
     * Metode que mitjançant la estrategia de inordre troba un habitant que sigui major que el del objecte introduit
     * @param current_habitant habitant actual que es el que es compara recurivament,a cada crida es un nou,fins quee troba
     * el  que cumpleix amb la condicio
     * @param weight pes del objecte introduit que ha de compararse anb el del habitant
     * @return retorna el primer habitant que troba que cumpleix amb la condicio de que el pes del habitant ha de ser major que el del objecte
     */
    private Habitant inordreMaxim(Habitant current_habitant, double weight) {
        if (current_habitant == null) {
            return null;
        }
        if (Double.compare(current_habitant.getWeight(), weight) == 1) { // current >weight
            return current_habitant;
        }
        if (Double.compare(current_habitant.getWeight(), weight) == -1) { // current <weight
            if (current_habitant.getMajorNode() != null) {
                current_habitant = inordreMaxim(current_habitant.getMajorNode(), weight);
            }
            if (current_habitant.getMinorNode() != null) {
                current_habitant = inordreMaxim(current_habitant.getMinorNode(), weight);
            }
        }
        else{
            current_habitant=findPreOrderMinorWeight(current_habitant,new Habitant(),weight);
        }
        return current_habitant;
    }

    /**
     * Metode que mitjançant la estrategia de inordre troba un habitant que sigui menor que el del objecte introduit
     * @param current_habitant habitant actual que es el que es compara recurivament,a cada crida es un nou,fins quee troba
     * el  que cumpleix amb la condicio
     * @param weight pes del objecte introduit que ha de compararse anb el del habitant
     * @return retorna el primer habitant que troba que cumpleix amb la condicio de que el pes del habitant ha de ser menor que el del objecte
     */
    private Habitant inordeMinim(Habitant current_habitant, double weight) {
        if (current_habitant == null) {
            return null;
        }

        if (Double.compare(current_habitant.getWeight(), weight) == -1) { // current <weight
            return current_habitant;
        }
        if (Double.compare(current_habitant.getWeight(), weight) == 1) { // current >weight
            if (current_habitant.getMinorNode() != null) {
                current_habitant = inordeMinim(current_habitant.getMinorNode(), weight);
            }
            if (current_habitant.getMajorNode() != null) {
                current_habitant = inordeMinim(current_habitant.getMajorNode(), weight);
            }
            else{
                current_habitant=findPreOrderMinorWeight(current_habitant,new Habitant(),weight);
            }
        }
        return current_habitant;
    }

    /**
     * Metode que mitjançant la tecnica de post ordre es trobara un node de forma recursiva que cumpleixi amb les condicions,
     * @param current_habitant habitant que s'acaba de comprobar
     * @param habitants habitant que s'ha trobat i que constitueix l'habitant trobat
     * @param weight valor de pes que te el objecte i que es el mateix que s'ha de trpbar en un habitant
     * @return retorna el habitant que s'ha trobat que te el mateix pes que el del objecte
     */
    private ArrayList<Habitant> inordre_equal(Habitant current_habitant, double weight, ArrayList<Habitant> habitants) {
        if (current_habitant.getWeight() == weight)
            habitants.add(current_habitant);
        if (current_habitant.getMajorNode() != null) {
            inordre_equal(current_habitant.getMajorNode(), weight, habitants);
        }
        if (current_habitant.getMinorNode() != null) {
            inordre_equal(current_habitant.getMinorNode(), weight, habitants);

        }
        return habitants;
    }

    /**
     * Metode que mitjançant la tecnica de inordre es trobara un node de forma recursiva que cumpleixi amb les condicions,en aquesat cas que
     * ,l'habitant que pesi menys que l'objecte
     *
     * @param current_habitant habitant que s'acaba de comprobar
     * @param weight valor de pes que te el objecte i que es el mateix que s'ha de trpbar en un habitant
     * @param minorWeight arraylist on ficarem aquell primer habitant que trobi que pesi menys
     * @return retorna el habitant que s'ha trobat que te el mateix pes que el del objecte
     */
    private ArrayList<Habitant> inordre_minor(Habitant current_habitant, double weight, ArrayList<Habitant> minorWeight) {
        if (current_habitant.getWeight() < weight){
            minorWeight.add(current_habitant);
            return minorWeight;
        }
        if (current_habitant.getMajorNode() != null) {
            inordre_minor(current_habitant.getMajorNode(), weight, minorWeight);
        }
        if (current_habitant.getMinorNode() != null) {
            inordre_minor(current_habitant.getMinorNode(), weight, minorWeight);
        }
        return minorWeight;
    }

    /**
     * Metode que mitjançant la tecnica de inordre es trobara un node de forma recursiva que cumpleixi amb les condicions,en aquesat cas que
     * ,l'habitant que pesi mes que l'objecte
     *
     * @param current_habitant habitant que s'acaba de comprobar
     * @param weight           valor de pes que te el objecte i que es el mateix que s'ha de trpbar en un habitant
     * @param majorWeight  arraylist on ficarem aquell primer habitant que trobi que pesi mes
     * @return retorna el habitant que s'ha trobat que te el mateix pes que el del objecte
     */
    private ArrayList<Habitant> inordre_major(Habitant current_habitant, double weight, ArrayList<Habitant> majorWeight) {
        if (current_habitant.getWeight() > weight){
            majorWeight.add(current_habitant);
            return majorWeight;
        }
        if (current_habitant.getMajorNode() != null) {
            inordre_major(current_habitant.getMajorNode(), weight, majorWeight);
        }
        if (current_habitant.getMinorNode() != null) {
            inordre_major(current_habitant.getMinorNode(), weight, majorWeight);
        }
        return majorWeight;
    }

    /**
     *Metode que segons el pes ha de buscar una bruixa que tingui el mateix pes que el del objecte introduit
     * @param weight pes introduit pel usuari el qual
     * @return retornara el habitant que ha cumplit amb les especificacions del pes en aquest cas ,si hi ha un habitant amb igual pes
     */
    public ArrayList<Habitant> findEqualWitches(double weight) {
        ArrayList<Habitant> equalWeight=new ArrayList<>();
        equalWeight=inordre_equal(root,weight,equalWeight);
        return equalWeight;
    }

    /**
     *Metode que segons el pes ha de buscar una bruixa que tingui un pes  menr que el del objecte introduit
     * @param weight pes introduit pel usuari el qual es fara servir per comprobar
     * @return retornara el habitant que ha cumplit amb les especificacions del pes en aquest cas ,si hi ha un habitant amb menor pes
     */
    public  ArrayList<Habitant> findMinorWitches(double weight) {
        ArrayList<Habitant> equalWeight=new ArrayList<>();
        equalWeight=inordre_minor(root,weight,equalWeight);
        return equalWeight;

    }

    /**
     *Metode que segons el pes ha de buscar una bruixa que tingui un pes major que el del objecte introduit
     * @param weight pes introduit pel usuari el qual es fara servir per comprovar
     * @return retornara el habitant que ha cumplit amb les especificacions del pes en aquest cas ,
     * si hi ha un habitant amb major pes
     */
    public ArrayList<Habitant> findMajorWitches(double weight) {
        ArrayList<Habitant> equalWeight=new ArrayList<>();
        equalWeight=inordre_major(root,weight,equalWeight);
        return equalWeight;
    }
}
