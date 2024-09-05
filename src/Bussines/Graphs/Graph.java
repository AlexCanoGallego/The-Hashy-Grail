package Bussines.Graphs;

import java.util.ArrayList;

public class Graph {
    private KnownRoutes routes;
    private InterestingPlaces place;

    /**
     * Constructor de la classe Graph
     * @param routes Rutes
     * @param places Llocs
     */
    public Graph(KnownRoutes routes, InterestingPlaces places){
        this.place = places;
        this.routes = routes;
    }

    /**
     * Constructor de la classe Graph
     */
    public Graph() {
        this.place = null;
        this.routes = null;
    }

    /**
     * Metode que afegueix un lloc
     * @param place Lloc
     */
    public void setPlace(InterestingPlaces place) {
        this.place = place;
    }

    /**
     * Metode que afegueix unes rutes
     * @param routes Rutes
     */
    public void setRoutes(KnownRoutes routes) {
        this.routes = routes;
    }

    /**
     * Metode que retorna uns llocs
     * @return Place
     */
    public InterestingPlaces getPlace() {
        return place;
    }

    /**
     * Metode que retorna unes rutes
     * @return Routes
     */
    public KnownRoutes getRoutes() {
        return routes;
    }


    /**
     * Metode que retorna un ArrayList de Graf
     * @param all_routes Rutes
     * @param interestingPlaces Llocs
     * @return ArrayList de Graf
     */
    public ArrayList<InterestingPlaces> makeGraph(ArrayList<KnownRoutes> all_routes, ArrayList<InterestingPlaces> interestingPlaces) {

        for (int i = 0; i < interestingPlaces.size(); i++) {
            int count = 0;
            for (int j = 0; j < all_routes.size(); j++) {
                if(all_routes.get(j).getPlaceMatch(interestingPlaces.get(i).getId())){
                    for(int k = 0; k < interestingPlaces.size(); k++){
                        if(interestingPlaces.get(i).getId() == all_routes.get(j).getPlaceA()){
                            if(interestingPlaces.get(k).getId() == all_routes.get(j).getPlaceB()){
                                interestingPlaces.get(i).setGraf(count, all_routes.get(j), interestingPlaces.get(k));

                            }
                        } else if(interestingPlaces.get(i).getId() == all_routes.get(j).getPlaceB()){
                            if(interestingPlaces.get(k).getId() == all_routes.get(j).getPlaceA()){
                                interestingPlaces.get(i).setGraf(count, all_routes.get(j), interestingPlaces.get(k));
                            }
                        }
                    }
                    count++;
                }
            }
        }
        return interestingPlaces;
    }
}
