package Presentation;

import Bussines.Graphs.Graph;
import Bussines.HashTable.Acusats;
import Bussines.HashTable.TablaHash;
import Bussines.RTrees.Bardissa;
import Bussines.RTrees.RTree;
import Bussines.RTrees.RTreeView;
import Bussines.RTrees.Rectangle;
import Bussines.Trees.Habitant;
import Bussines.Graphs.InterestingPlaces;
import Bussines.Graphs.KnownRoutes;
import Bussines.Manager;
import Bussines.Trees.Tree;
import Bussines.RTreeManager;

import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe que gestiona les opcions del menú i les funcionalitats del programa.
 */
public class Controller {
    private ViewManager viewManager;
    private Manager manager;
    private RTreeManager r_tree_manager;

    /**
     * Constructor de la classe Controller.
     */
    public Controller(){
        this.viewManager = new ViewManager();
        this.manager = new Manager();
        this.r_tree_manager = new RTreeManager();
    }

    /**
     * Mètode que mostra el menú principal.
     */
    public void run(){
        int option = 0;

        System.out.println("'`^\\ The Hashy Grail /^´'");

        while(true) {
            viewManager.showMenu();
            System.out.print("Esculli una opció: ");
            option = viewManager.askForInt();
            switch (option){
                case 1:
                    optionOne();
                    break;
                case 2:
                    optionTwo();
                    break;
                case 3:
                    optionThree();
                    break;
                case 4:
                    optionFour();
                    break;
                case 5:
                    exit();
                    break;
                default:
                    System.out.println("\nERROR: introdueix un enter correcte [1,5]");
                    break;
            }
        }
    }

    // ----------------------------------------------- OPTION 1 --------------------------------------------------------

    /**
     * Mètode que mostra el menú de la primera opció.
     */
    public void optionOne(){
        char funtion;
        int exit = 0;
        ArrayList<InterestingPlaces> grafPlaces = manager.getGraf();

        while (exit == 0){
            viewManager.showOptionOne();
            System.out.print("Quina funcionalitat vol executar? ");
            funtion = viewManager.askForChar();
            switch (Character.toUpperCase(funtion)){
                case 'A':
                    optionOneA(grafPlaces);
                    break;
                case 'B':
                    optionOneB(grafPlaces);
                    break;
                case 'C':
                    optionOneC(grafPlaces);
                    break;
                case 'D':
                    exit = 1;
                    break;
                default:
                    System.out.println("\nERROR: introdueix un caràcter correcte [A,D]");
                    break;
            }
        }
    }

    /**
     * Mètode que mostra el menú de la primera opció A.
     * @param grafPlaces  Arraylist amb tots els llocs d'interes
     */
    public void optionOneA(ArrayList<InterestingPlaces> grafPlaces){
        int llocID = 0;
        int okRegne = 0;
        int okLloc = 0;

        System.out.print("Quin lloc vol explorar? ");
        llocID = viewManager.askForInt();

        for(int i = 0; i < grafPlaces.size(); i++){
            if(grafPlaces.get(i).getId() == llocID){
                System.out.println("\n" + grafPlaces.get(i).getId()
                        + " - " + grafPlaces.get(i).getName() + ", "
                        + grafPlaces.get(i).getReign()
                        + " (" + grafPlaces.get(i).getClimate() + ")");
                okLloc = 1;
                System.out.println("\nEls llocs del " + grafPlaces.get(i).getReign() + " als que es pot arribar són:\n");

                for(int j = 0; j < grafPlaces.get(i).getGraf().size(); j++){
                    if(grafPlaces.get(i).getGraf().get(j).getPlace().getReign().equals(grafPlaces.get(i).getReign())){
                        System.out.println(grafPlaces.get(i).getGraf().get(j).getPlace().getId() + " - "
                                + grafPlaces.get(i).getGraf().get(j).getPlace().getName()
                                + ", " + grafPlaces.get(i).getGraf().get(j).getPlace().getReign()
                                + " (" + grafPlaces.get(i).getGraf().get(j).getPlace().getClimate() + ")");
                        okRegne = 1;
                    }
                }
                if(okRegne == 0) System.out.println("No hi ha llocs del " + grafPlaces.get(i).getReign() + " als que es pot arribar!");
            }
        }
        if(okLloc == 0) System.out.println("\nNo existeix aquest lloc!");
    }

    /**
     * Assumint que les aus tendeixen a anar en línia recta, trobeu el conjunt de trajectes que
     * connecten tots els llocs d’interès i minimitzen la distància a recórrer.
     * Haviem de fer mstPrim i al tenir dataseets tant grans(GRAFS DENSOS) ens ha interesat escalar  mes per vertex que per nodes
     * @param grafPlaces  Arraylist amb tots els llocs d'interes
     */
    public void optionOneB(ArrayList<InterestingPlaces> grafPlaces){
        ArrayList<InterestingPlaces> nodesMST = new ArrayList<>();      //Es important tenir una forma de afegir nodes,nosaltres ho farem mitjançant arraylist
        float distancia=0;
        String cadena2 = "- ";
        nodesMST=mstPrim(grafPlaces,grafPlaces.size());
        int trobat=0;

        for (int i = 0; i < nodesMST.size()-1; i++) {   //Menys 1 perque fa un ++ de mes
            trobat = 0;

            for (int k = 0; k < grafPlaces.size() && trobat == 0; k++) {
                for (int l = 0; l < grafPlaces.get(k).getGraf().size(); l++) {
                    if (nodesMST.get(i + 1) != null) {
                        if ((nodesMST.get(i).getId() == grafPlaces.get(k).getGraf().get(l).getRoutes().getPlaceA())
                                && (nodesMST.get(i + 1).getId() == grafPlaces.get(k).getGraf().get(l).getRoutes().getPlaceB())) {
                            distancia += grafPlaces.get(k).getGraf().get(l).getRoutes().getDistance();
                            trobat = 1;
                        }
                        if ((nodesMST.get(i).getId() == grafPlaces.get(k).getGraf().get(l).getRoutes().getPlaceB())
                                && (nodesMST.get(i + 1).getId() == grafPlaces.get(k).getGraf().get(l).getRoutes().getPlaceA())) {
                            distancia += grafPlaces.get(k).getGraf().get(l).getRoutes().getDistance();
                            trobat = 1;
                        }
                    }
                }
            }
            cadena2 = cadena2.concat(nodesMST.get(i).getId() + " - ");
        }

        System.out.println("\nEls trajectes més habituals, que minimitzen la distancia a recorrer son els seguents:\n");
        System.out.println("La ruta es la seguent: " + cadena2 + " i la seva distancia: "+distancia);
    }

    /**
     *  mstPrim consistia en mirar amb un contador quans nodes tinc ,i quants vertex tinc,mirar el contador de dintre amb el contador del altre i anar recorrent els nodes no visitats i agafant els que
     *  connecten amb nodes nous ,pero amb la condicio de que la distancia fos la mes petita, i amb la funcio auxiliar que agafa la aresta de menys pes de les que conecten amb node nous ho aconseguiem.
     *
     * @param g Arraylist amb tots els llocs d'interes
     * @param size mida del arraylist per saber cuans nodes s'han de coneixer
     * @return  Retorna un arraylist de llocs d'interes amb tots els nodes coneguts
     */
    private ArrayList<InterestingPlaces> mstPrim(ArrayList<InterestingPlaces> g, int size) {
        int contadorNodesMST=0;
        ArrayList<InterestingPlaces> nodesMST = new ArrayList<>();      //Es important tenir una forma de afegir nodes,nosaltres ho farem amb arraylists,ja que sabem que el cost que te en "afegir" i trobar informacio es n
        ArrayList<KnownRoutes> vertexMST = new ArrayList<>();           //Es important tenir una forma de afegir arestes
        nodesMST.add(g.get(0).getGraf().get(0).getPlace());             //Node origen qualsevol del graf(grafPlaces),el primer per exemple

        while(contadorNodesMST!=size) { //Mentre mst no tingui tots els nodes que el graf,MENYS 1 perque em faltava un
            InterestingPlaces node = agafaArestaMenysPes(nodesMST, contadorNodesMST,g);
            nodesMST.add(node);
            contadorNodesMST++;
        }
        return nodesMST;
    }

    /**
     * Funcio que mira els vertexs del node que s'ha afegit,i troba el que tingui menys pes i que conecta a un node nou(no conegut)
     * @param nodesMST array amb els nodes afegits fins al moment
     * @param contadorNodesMST posicio del ultim node que s'ha afegit
     * @param g graf amb tota la informació
     * @return retorna un node per afegir
     */
    private InterestingPlaces agafaArestaMenysPes(ArrayList<InterestingPlaces> nodesMST, int contadorNodesMST, ArrayList<InterestingPlaces> g) {
        InterestingPlaces nodeToAdd = null;
        int nodeToSearch = 0;
        int nodeEstaAñadit = 0;

        for (int i = 0; i < g.size(); i++) {
            float bestDistance=Float.MAX_VALUE;
            for (int j = 0; j < g.get(i).getGraf().size(); j++) {
                if (nodesMST.get(contadorNodesMST).getId() == g.get(i).getGraf().get(j).getRoutes().getPlaceA()) {
                    for (int k = 0; k < nodesMST.size(); k++) {
                        //Si amb el que conecta ja esta ñadit no l'añadeix
                        if (g.get(i).getGraf().get(j).getRoutes().getPlaceB() == nodesMST.get(k).getId()) {
                            nodeEstaAñadit = 1;
                        }
                    }
                    if(nodeEstaAñadit !=1) {
                        if (bestDistance > g.get(i).getGraf().get(j).getRoutes().getDistance()) {
                            nodeEstaAñadit = 0;
                            bestDistance = g.get(i).getGraf().get(j).getRoutes().getDistance();
                            nodeToSearch = g.get(i).getGraf().get(j).getRoutes().getPlaceB();
                        }
                    }
                }
                nodeEstaAñadit = 0;

                if(nodesMST.get(contadorNodesMST).getId()==g.get(i).getGraf().get(j).getRoutes().getPlaceB()) {
                    for (int k = 0; k < nodesMST.size(); k++) {
                        //Si amb el que conecta ja esta ñadit no l'añadeix
                        if (g.get(i).getGraf().get(j).getRoutes().getPlaceA() == nodesMST.get(k).getId()) {
                            nodeEstaAñadit = 1;
                        }
                    }
                    if (nodeEstaAñadit != 1) {
                        if (bestDistance > g.get(i).getGraf().get(j).getRoutes().getDistance()) {
                            nodeEstaAñadit = 0;
                            bestDistance = g.get(i).getGraf().get(j).getRoutes().getDistance();
                            nodeToSearch = g.get(i).getGraf().get(j).getRoutes().getPlaceA();
                        }
                    }
                }
            }
        }
        int nodeAdded = 0;
        if(nodeEstaAñadit == 0){
            for (int i = 0; i < g.size() && nodeAdded == 0; i++) {
                for (int j = 0; j < g.get(i).getGraf().size(); j++) {
                    if (g.get(i).getGraf().get(j).getPlace().getId() == nodeToSearch) {
                        nodeToAdd = g.get(i).getGraf().get(j).getPlace();
                        nodeAdded = 1;
                    }
                }
            }
        }
        return nodeToAdd;
    }

    /**
     * Mètode que mostra el menú de la primera opció C.
     * @param grafPlaces Arraylist amb tots els llocs d'interes.
     */
    public void optionOneC(ArrayList<InterestingPlaces> grafPlaces){
        int origenID = 0;
        int destiID = 0;
        int j = 0, k = 0;
        boolean coco = false;
        boolean exit = false;
        LinkedList<Integer> path;

        System.out.print("Quin és el lloc d'origen? ");
        origenID = viewManager.askForInt();
        System.out.print("Quin és el lloc de desti? ");
        destiID = viewManager.askForInt();

        while(!exit){
            System.out.print("L’oreneta carrega un coco? ");
            switch (viewManager.askForString()) {
                case "SI":
                    exit = true;
                    coco = true;
                    break;
                case "NO":
                    exit = true;
                    coco = false;
                    break;
                default:
                    System.out.println("\nERROR: només és 'SI' o 'NO'!\n");
                    break;
            }
        }
        //CALCULAR LA RUTA MÉS EFICIENT AMB DIJKSTRA
        InterestingPlaces desti = null, origen = null;

        while ((origen == null || desti == null) && j < grafPlaces.size()) {
            if (grafPlaces.get(j).getId() == origenID) {
                origen = grafPlaces.get(j);
            } else if (grafPlaces.get(j).getId() == destiID) {
                desti = grafPlaces.get(j);
            }
            j++;
        }
        if (origen == null || desti == null)
        {
            System.out.print("\nERROR:El node ");
            if (origen==null && desti==null)System.out.print("origen i desti");
            else
            {
                if (origen==null) System.out.print("origen");
                else System.out.print("desti");
            }
            System.out.print(" no existeix");

        }
        else
        {
            path = dijkstra(grafPlaces, origen.getId(), desti.getId(), coco);
            System.out.println();
            System.out.print("\tCamí: ");
            if (path != null) {

                for (int i = 0; i < path.size(); i++) {
                    if(i+1 == path.size())
                        System.out.print("Node " + path.get(i));
                    else
                        System.out.print("Node " + path.get(i) + ", ");
                }
            } else {
                System.out.print("ERROR: No hi ha path d'un node al altre!");
            }
        }

        System.out.println();


    }

    /**
     * Mètode que inicialitza els valors de la llista de nodes visitats.
     * @param origin_node Node d'origen.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @return Llista de nodes visitats.
     */
    private ArrayList<Double> initializeScores(int origin_node, ArrayList<InterestingPlaces> graph) {
        ArrayList<Double> scores = new ArrayList<>(graph.size());

        for (int i = 0; i < graph.size(); i++) {
            if (i != origin_node) {
                scores.add(Double.POSITIVE_INFINITY);   // La distancia hacia los otros nodos por ahora sera infinito ya que habra que definirla.
            } else {
                scores.add(0.0);  // En el vertice origen la distancia a si mismo es claramente 0.
            }
        }
        return scores;
    }

    /**
     * Mètode que inicialitza els valors de la llista de nodes visitats.
     * @param origin_node Node d'origen.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @return Llista de nodes visitats.
     */
    private ArrayList<Integer> initializePrevious(int origin_node, ArrayList<InterestingPlaces> graph) {
        ArrayList<Integer> previous = new ArrayList<>(graph.size());

        for (int i = 0; i < graph.size(); i++) {
            if (i != origin_node) {
                previous.add(-1);   // La distancia hacia los otros nodos por ahora sera infinito ya que habra que definirla.
            } else {
                previous.add(origin_node);  // En el vertice origen la distancia a si mismo es claramente 0.
            }
        }
        return previous;
    }

    /**
     * Mètode que inicialitza els valors de la llista de nodes visitats.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @return Llista de nodes visitats.
     */
    private ArrayList<Boolean> initializeVisited(ArrayList<InterestingPlaces> graph) {
        ArrayList<Boolean> visited = new ArrayList<>();

        for (int i = 0; i < graph.size(); i++) {
            visited.add(false);
        }
        return visited;
    }

    /**
     * Mètode que calcula la ruta més curta entre dos nodes.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @param origin_node Node d'origen.
     * @param destination_node Node de destí.
     * @param coco Boolean que indica si l'oreneta porta un coco o no.
     * @return Llista amb els nodes que formen la ruta més curta.
     */
    private LinkedList<Integer> createPath(ArrayList<Integer> nodePrevious, int origin_node, int destination_node) {
        LinkedList<Integer> path = new LinkedList<>();
        int i = destination_node;

        path.addFirst(destination_node);
        while (i != origin_node) {
            path.addFirst(nodePrevious.get(i));
            i = nodePrevious.get(i);
        }

        return path;
    }

    /**
     * Mètode que calcula la ruta més curta entre dos nodes.
     * @param nodeVisited Llista de nodes visitats.
     * @param nodeScore Llista de nodes amb la distància.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @return el node amb la distància més curta.
     */
    private int getNextNode(ArrayList<Boolean> nodeVisited, ArrayList<Double> nodeScore, ArrayList<InterestingPlaces> graph) {
        int next_node = -1;
        Double next_score =  Double.POSITIVE_INFINITY;

        for (int i = 0; i < graph.size(); i++) {
            if (!nodeVisited.get(i) && nodeScore.get(i) < next_score) {
                next_node = i;
                next_score = nodeScore.get(i);
            }
        }

        return next_node;
    }

    /**
     * Array amb les ids de tots els nodes.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @return una array amb les ids de tots els nodes.
     */
    private int[] getIdArray(ArrayList<InterestingPlaces> graph) {
        int[] ids = new int[graph.size()];// Array con las ids de los nodos
        for (int i = 0; i < graph.size(); i++) {
            ids[i] = graph.get(i).getId();
        }
        return ids;
    }

    /**
     * Mètode que retorna la posició d'un node dins l'array d'ids.
     * @param ids Array amb les ids de tots els nodes.
     * @param Id Id del node.
     * @return la posició del node dins l'array d'ids.
     */
    private int getPosition( int[] ids,int Id) {
        int i=0;
        while (ids[i] != Id) {
            i++;
        }
        return i;
    }

    /**
     * Mètode que retorna la posició d'un node dins l'array d'ids.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @param option 0 si es vol calcular el temps, 1 si es vol calcular la distància.
     * @return la matriu de distàncies o temps.
     */
    private double[][] initializeMatrix(ArrayList<InterestingPlaces> graph, int option) {
        double[][] time = new double[graph.size()][graph.size()];
        int[] ids = getIdArray(graph);

        // Inicializamos d a infinito para todas las posiciones
        for (int i = 0; i < graph.size(); i++) {
            Arrays.fill(time[i], Double.POSITIVE_INFINITY);
        }

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                if (i == j) time[i][j] = 0;
            }
        }

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.get(i).getGraf().size(); j++) {
                if (option == 0) {
                    time[i][getPosition(ids, graph.get(i).getGraf().get(j).getPlace().getId())] = graph.get(i).getGraf().get(j).getRoutes().getTime_european();
                } else if (option == 1) {
                    time[i][getPosition(ids, graph.get(i).getGraf().get(j).getPlace().getId())] = graph.get(i).getGraf().get(j).getRoutes().getTime_african();
                }
                else {
                    time[i][getPosition(ids,graph.get(i).getGraf().get(j).getPlace().getId())]=graph.get(i).getGraf().get(j).getRoutes().getDistance();
                }
            }
        }
        return time;
    }

    /**
     * Mètode que inicialitza l'array de distàncies.
     * @param graph Arraylist amb tots els llocs d'interes.
     * @param origin_node Node d'origen.
     * @param destination_node Node de destí.
     * @param coco Boolean que indica si l'oreneta porta un coco o no.
     * @return l'array de distàncies.
     */
    public LinkedList<Integer> dijkstra(ArrayList<InterestingPlaces> graph,int origin_node, int destination_node, boolean coco) {
        // Cada posicion representa su nodo, por ejemplo, la posicion 3 de los siguentes arrays representa el nodo 3 del graph.
        double[][] timeEuropean,timeAfrican,distance;
        int[] ids = getIdArray(graph);// Array con las ids de los nodos
        LinkedList<Integer> europeanPath = new LinkedList<>(),africanPath = new LinkedList<>();
        origin_node=getPosition(ids,origin_node);
        destination_node=getPosition(ids,destination_node);
        ArrayList<Double> nodeScore = initializeScores(origin_node,graph);       // Hay que guardar el score de cada nodo para compararlos entre ellos.
        ArrayList<Integer> nodePrevious = initializePrevious(origin_node,graph);  // Hay que guardar el nodo previo respecto al mejor camino para poder hacer el path.
        ArrayList<Boolean> nodeVisited = initializeVisited(graph);               // Hay que guardar los nodos visitados asi que utilizare un arrayList de booleanos.
        int current_node = origin_node;
        double actual_score,europeanBestScore,africanBestScore,europeanDistance=0,africanDistance = 0;
        timeEuropean=initializeMatrix(graph,0);
        distance=initializeMatrix(graph,2);

        while (europeanPath.isEmpty()) {
            nodeVisited.set(current_node, true);
            for (int next_node = 0; next_node < graph.size(); ++next_node) {// El for revisa todos los nodos
                if (coco) {
                    if (timeEuropean[current_node][next_node] != -1 && !nodeVisited.get(next_node) && (graph.get(next_node).getClimate().equals("Clima Polar") || graph.get(next_node).getClimate().equals("Clima Continental")) && distance[current_node][next_node] < 50) {   // Si el nodo es vecino al actual i si el nodo no ha sido visitado (o es el mismo) entonces
                        actual_score = nodeScore.get(current_node) + timeEuropean[current_node][next_node];     // Se calcula el score del nuevo recorrido entre nodos para compararla
                        if (actual_score < nodeScore.get(next_node)) {  // Si la score resulta ser mejor que la anterior se actualiza la score del nodo y el previous node
                            nodeScore.set(next_node, actual_score);
                            nodePrevious.set(next_node, current_node);
                        }
                    }
                }
                else {
                    if (timeEuropean[current_node][next_node] != -1 && !nodeVisited.get(next_node) && (graph.get(next_node).getClimate().equals("Clima Polar") || graph.get(next_node).getClimate().equals("Clima Continental"))) {   // Si el nodo es vecino al actual i si el nodo no ha sido visitado (o es el mismo) entonces
                        actual_score = nodeScore.get(current_node) + timeEuropean[current_node][next_node];     // Se calcula el score del nuevo recorrido entre nodos para compararla
                        if (actual_score < nodeScore.get(next_node)) {  // Si la score resulta ser mejor que la anterior se actualiza la score del nodo y el previous node
                            nodeScore.set(next_node, actual_score);
                            nodePrevious.set(next_node, current_node);
                        }
                    }
                }
            }
            // Ahora se detecta si el nodo actual es el nodo destino y se devuelve el path
            if (current_node == destination_node) {
                int i = destination_node;

                europeanPath.addFirst(destination_node);
                while (i != origin_node) {
                    europeanPath.addFirst(nodePrevious.get(i));
                    i = nodePrevious.get(i);
                }
            }
            // Ahora actualizamos el nodo actual con el nodo con el score mas bajo y que no haya sido visitado
            current_node = getNextNode(nodeVisited, nodeScore,graph);
            if (current_node == -1 && europeanPath.isEmpty()) {  // Ahora se detecta si no hay path y por lo tanto que no hay nodos con un score que no sea infinito
                europeanPath.add(null);
            }
        }
        europeanBestScore = nodeScore.get(destination_node);
        current_node = origin_node;

        nodeScore = initializeScores(origin_node,graph);       // Hay que guardar el score de cada nodo para compararlos entre ellos.
        nodePrevious = initializePrevious(origin_node,graph);  // Hay que guardar el nodo previo respecto al mejor camino para poder hacer el path.
        nodeVisited = initializeVisited(graph);               // Hay que guardar los nodos visitados asi que utilizare un arrayList de booleanos.

        timeAfrican=initializeMatrix(graph,1);

        while (africanPath.isEmpty()) {
            nodeVisited.set(current_node, true);
            for (int next_node = 0; next_node < graph.size(); ++next_node) {    // El for revisa todos los nodos
                if (coco) {
                    if (timeAfrican[current_node][next_node] != -1 && !nodeVisited.get(next_node)&& (graph.get(next_node).getClimate().equals("Clima Tropical") || graph.get(next_node).getClimate().equals("Clima Continental")) && distance[current_node][next_node]<50) {   // Si el nodo es vecino al actual i si el nodo no ha sido visitado (o es el mismo) entonces
                        actual_score = nodeScore.get(current_node) + timeAfrican[current_node][next_node];     // Se calcula el score del nuevo recorrido entre nodos para compararla
                        if (actual_score < nodeScore.get(next_node)) {  // Si la score resulta ser mejor que la anterior se actualiza la score del nodo y el previous node
                            nodeScore.set(next_node, actual_score);
                            nodePrevious.set(next_node, current_node);
                        }
                    }
                }
                else {
                    if (timeAfrican[current_node][next_node] != -1 && !nodeVisited.get(next_node)&& (graph.get(next_node).getClimate().equals("Clima Tropical") || graph.get(next_node).getClimate().equals("Clima Continental"))) {   // Si el nodo es vecino al actual i si el nodo no ha sido visitado (o es el mismo) entonces
                        actual_score = nodeScore.get(current_node) + timeAfrican[current_node][next_node];     // Se calcula el score del nuevo recorrido entre nodos para compararla
                        if (actual_score < nodeScore.get(next_node)) {  // Si la score resulta ser mejor que la anterior se actualiza la score del nodo y el previous node
                            nodeScore.set(next_node, actual_score);
                            nodePrevious.set(next_node, current_node);
                        }
                    }
                }
            }
            // Ahora se detecta si el nodo actual es el nodo destino y se devuelve el path
            if (current_node == destination_node) {
                int i = destination_node;

                africanPath.addFirst(destination_node);
                while (i != origin_node) {
                    africanPath.addFirst(nodePrevious.get(i));
                    i = nodePrevious.get(i);
                }
            }
            // Ahora actualizamos el nodo actual con el nodo con el score mas bajo y que no haya sido visitado
            current_node = getNextNode(nodeVisited, nodeScore,graph);
            if (current_node == -1 && africanPath.isEmpty()) {  // Ahora se detecta si no hay path y por lo tanto que no hay nodos con un score que no sea infinito
                africanPath.add(null);
            }
        }
        africanBestScore=nodeScore.get(destination_node);
        for (int i = 0; i < africanPath.size()-1; i++) {
            africanDistance += distance[africanPath.get(i)][africanPath.get(i+1)];
        }
        for (int i = 0; i < europeanPath.size()-1; i++) {
            europeanDistance += distance[europeanPath.get(i)][europeanPath.get(i+1)];
        }
        if (africanBestScore < europeanBestScore && africanBestScore!=-1) {
            System.out.print("\nL'opció més eficient és enviar una oreneta africana\n\tTemps: " + (int)africanBestScore + " minuts \n\tDistància: " + (int)africanDistance + " quilòmetres");
            return africanPath;
        } else if (europeanBestScore != -1) {
            System.out.print("\nL'opció més eficient és enviar una oreneta europea\n\tTemps: " + (int)europeanBestScore + " minuts \n\tDistància: " + (int)europeanDistance + " quilòmetres");
            return europeanPath;
        }
        else {
            return null;
        }
    }

    // ----------------------------------------------- OPTION 2 --------------------------------------------------------

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 2
     */
    public void optionTwo(){
        char funtion;
        int exit = 0;

        manager.initializeTree();
        while (exit == 0){
            viewManager.showOptionTwo();
            System.out.print("Quina funcionalitat vol executar? ");
            funtion = viewManager.askForChar();
            switch (funtion){
                case 'A':
                    optionTwoA();
                    break;
                case 'B':
                    optionTwoB();
                    break;
                case 'C':
                    Tree tree = manager.getTree();
                    System.out.println();
                    optionTwoC(tree.getHabitant(), false, "");
                    break;
                case 'D':
                    optionTwoD();
                    break;
                case 'E':
                    optionTwoE();
                    break;
                case 'F':
                    manager.removeTree();
                    exit = 1;
                    break;
                default:
                    System.out.println("\nERROR: introdueix un caràcter correcte [A,F]");
                    break;
            }
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 2A
     */
    public void optionTwoA() {
        Habitant new_habitant = viewManager.askHabitant();
        viewManager.succesfullyAddedHabitant(manager.addHabitant(new_habitant), new_habitant.getName());
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 2B
     */
    public void optionTwoB() {
        String wanted_name = manager.removeHabitant(viewManager.askRemoveId());
        viewManager.succesfullyRemovedHabitant(wanted_name != null, wanted_name);
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 2D
     * @param habitantsTree Arbre dels habitants
     * @param major Boolea que indica si el node es major o no
     * @param anterior String que indica el anterior
     */
    public void optionTwoC(Habitant habitantsTree, boolean major, String anterior){
        if(habitantsTree.getMajorNode() != null){
            optionTwoC(habitantsTree.getMajorNode(), true, anterior + (major ? "        " : " |      "));
        }
        System.out.print(anterior);
        if(major){
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("------");
        viewManager.printTree(habitantsTree.getName(), habitantsTree.getKingdom(), habitantsTree.getId(), habitantsTree.getWeight(), 1);
        if(habitantsTree.getMinorNode() != null){
            optionTwoC(habitantsTree.getMinorNode(), false, anterior + (major ? " |      " : "        "));
        }
    }

    /**
     * Metode que interacciona amb el usuari per preguntar les caracteristicas del objecte per trobar una bruixa tenint en compte el pes segons el tipus d'objecte que
     * t'introdueixin
     */
    private void optionTwoD() {

        System.out.print("\nNom del objecte: ");
        String name=viewManager.askForString();
        System.out.print("Pes del objecte( amb ',' no '.'): ");
        Double pes=viewManager.askForDouble();
        System.out.print("Tipus del Objecte: ");
        String type=viewManager.askForString();
        ArrayList<Habitant> habitant;
        habitant = manager.getWeightWitches(pes,type);

        if (habitant == null) {
            viewManager.printNumWitches(0);
        } else {
            viewManager.printNumWitches(habitant.size());
            for(int i = 0; i < habitant.size(); i++){
                viewManager.printWitch(habitant.get(i));
            }
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 2E
     */
    public void optionTwoE() {
        LinkedList<Habitant> batuda = manager.getWitches(viewManager.askForDouble("\nPes mínim: "), viewManager.askForDouble("Pes màxim: "));

        if (batuda == null) {
            viewManager.printNumWitches(0);
        } else {
            viewManager.printNumWitches(batuda.size());
            for (int i = 0; i < batuda.size(); i++) {
                viewManager.printWitch(batuda.get(i));
            }
        }
    }

    // ----------------------------------------------- OPTION 3 --------------------------------------------------------

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3
     */
    public void optionThree(){
        char funtion;
        int exit = 0;

        r_tree_manager.initializeRTree();
        RTree rTree = r_tree_manager.getR_tree();
        while (exit == 0) {
            viewManager.showOptionThree();
            System.out.print("Quina funcionalitat vol executar? ");
            funtion = viewManager.askForChar();
            switch (funtion) {
                case 'A':
                    optionThreeA();
                    break;
                case 'B':
                    optionThreeB();
                    break;
                case 'C':
                    optionThreeC(rTree);
                    break;
                case 'D':
                    optionThreeD();
                    break;
                case 'E':
                    optionThreeE();
                    break;
                case 'F':
                    exit = 1;
                    break;
                default:
                    System.out.println("\nERROR: introdueix un caràcter correcte [A - F]");
                    break;
            }
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3A
     */
    public void optionThreeA(){
        Bardissa new_bardissa = viewManager.askBardissa();
        r_tree_manager.addBardissa(new_bardissa);

        if(new_bardissa != null){
            System.out.println("\nUna nova bardissa aparegué a la Bretanya.");
        } else{
            System.out.println("\nERROR: Tipus invàlid.");
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3B
     */
    public void optionThreeB(){
        System.out.print("\nLatitud de la bardissa: ");
        double latitud = viewManager.askForDouble();

        System.out.print("Longitud de la bardissa: ");
        double longitud = viewManager.askForDouble();

        if (r_tree_manager.removeBardissa(latitud, longitud))
            System.out.println("\nLa bardissa s’ha eliminat, per ser integrada a una tanca.");
        else
            System.out.println("\nERROR: No s'ha pogut eliminar la bardissa.");
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3C
     * @param rTree RTree
     */
    public void optionThreeC(RTree rTree){
        System.out.println();
        RTreeView rTreeView = new RTreeView(rTree);
        rTreeView.setVisible(true);
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3D
     */
    public void optionThreeD(){
        System.out.print("\nEntra el primer punt de l’àrea (lat,long): ");
        String primer = viewManager.askForString();

        System.out.print("Entra el segon punt de l’àrea (lat,long): ");
        String segon = viewManager.askForString();

        ArrayList<Bardissa> b = r_tree_manager.cercaPerArea(primer, segon);

        if(b.size() == 0){
            System.out.println("\nNo hi ha Bardisses en aquesta AREA.");
        } else{
            System.out.println();
            for(int i = 0; i < b.size(); i++){
                viewManager.printRTree(b.get(i).getLatitud(), b.get(i).getLongitud(), b.get(i).getTipus(), b.get(i).getMida(), b.get(i).getColor());
            }
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 3E
     */
    public void optionThreeE(){
        System.out.print("\nEntra el punt a consultar (lat,long): ");
        String punt = viewManager.askForString();

        System.out.print("Entra la quantitat de bardisses a considerar (K): ");
        int k = viewManager.askForInt();

        r_tree_manager.cercaPerPunt(punt, k);

    }


    // ----------------------------------------------- OPTION 4 --------------------------------------------------------

    TablaHash tabla;

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4
     */
    public void optionFour(){
        char funtion;
        int exit = 0;
        tabla=manager.initializeHashTable();
        while (exit == 0){
            viewManager.showOptionFour();
            System.out.print("Quina funcionalitat vol executar? ");
            funtion = viewManager.askForChar();
            switch (funtion){
                case 'A':
                    optionFourA();
                    break;
                case 'B':
                    optionFourB();
                    break;
                case 'C':
                    optionFourC();
                    break;
                case 'D':
                    optionFourD();
                    break;
                case 'E':
                    optionFourE();
                    break;
                case 'F':
                    optionFourF();
                    break;
                case 'G':
                    exit = 1;
                    break;
                default:
                    System.out.println("\nERROR: introdueix un caràcter correcte [A - G]");
                    break;
            }
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4A
     */
    public void optionFourA(){
        System.out.print("\nNom de l'acusat: ");
        String name = viewManager.askForString();
        System.out.print("Nombre de conills vistos: ");
        int rabbits = viewManager.askForInt();
        System.out.print("Professio: ");
        String profession = viewManager.askForString();
        Boolean heretic=false;
        if (rabbits>1975) heretic=true;
        if (profession.equals("KING") || profession.equals("QUEEN") || profession.equals("CLERGYMAN")) heretic=false;
        Acusats acusado = new Acusats(name, rabbits, profession,heretic);
        tabla.put(acusado);
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4B
     */
    public void optionFourB(){
        System.out.print("\nNom de l'acusat: ");
        String name = viewManager.askForString();
        tabla.remove(name);
        if (tabla.get(name)==null)System.out.println("L’execució pública de "+ name +" ha estat un èxit.");
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4C
     */
    public void optionFourC(){
        System.out.print("\nNom de l'acusat: ");
        String name = viewManager.askForString();
        Acusats acusat = tabla.get(name);
        tabla.remove(name);
        System.out.print("Marcar com a heretge (Y/N)? ");
        char isHeretic = viewManager.askForChar();
        if (acusat==null)System.out.println("El acusat indicat no existeix");
        else
        {
            if (acusat.getProfessio().equals("KING") || acusat.getProfessio().equals("QUEEN") || acusat.getProfessio().equals("CLERGYMAN")) System.out.println("Aquest acusat no pot ser heretge");
            else
            {
                switch (isHeretic) {
                    case 'Y':
                        acusat.setHeretge(true);
                        System.out.print("\nLa Inquisició Espanyola ha conclòs que "+ name +" és un heretge\n");
                        break;
                    case 'N':
                        acusat.setHeretge(false);
                        System.out.print("\nLa Inquisició Espanyola ha conclòs que "+ name +" no és un heretge");
                        break;
                    default:
                        System.out.println("\nERROR: introdueix un caràcter correcte [Y or N]");
                        break;
                }
            }
        }

        tabla.put(acusat);
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4D
     */
    public void optionFourD(){
        System.out.print("\nNom de l'acusat: ");
        String name = viewManager.askForString();
        Acusats acusat = tabla.get(name);
        String isHeretge="No";
        if (acusat.getHeretge())isHeretge="Si";
        System.out.print("\nRegistre per “"+name+"”:");
        System.out.print("\n\t* Nombre de conills vistos: "+acusat.getNumConills());
        System.out.print("\n\t* Professió: "+acusat.getProfessio());
        System.out.print("\n\t* Heretge? "+isHeretge+"\n");
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4E
     */
    public void optionFourE(){
        System.out.print("\nNombre minim de conills: ");
        int min = viewManager.askForInt();
        System.out.print("Nombre màxim de conills: ");
        int max = viewManager.askForInt();
        List<Acusats> acusadosRango = tabla.getAcusatsPorNumConills(min,max);
        String isHeretge="No";
        System.out.print("\nS’han trobat els següents acusats: ");
        for (Acusats a : acusadosRango) {
            if (a.getHeretge())isHeretge="Si";
            else isHeretge="No";
            System.out.print("\n\t"+a.getNom()+":");
            System.out.print("\n\t\t* Nombre de conills vistos: "+a.getNumConills());
            System.out.print("\n\t\t* Professió: "+a.getProfessio());
            System.out.print("\n\t\t* Heretge? "+isHeretge+"\n");
        }
    }

    /**
     * Funció que s'encarrega de mostrar per pantalla la opció 4F
     */
    public void optionFourF(){
        System.out.println("Generant histograma...\n");
        tabla.contarHerejesPorProfesion();
    }



    // ------------------------------------------------- EXIT ----------------------------------------------------------

    /**
     * Funció que s'encarrega de finalitzar el programa
     */
    public void exit(){
        System.out.println("\nAturant The Hashy Grail...");
        System.exit(0);
    }
}