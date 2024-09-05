package Bussines.Graphs;

public class KnownRoutes {
    private int placeA;
    private int placeB;
    private float time_european;
    private float time_african;
    private float distance;
    private InterestingPlaces place;

    /**
     * Constructor de la classe KnownRoutes
     * @param placeA Lloc A
     * @param placeB Lloc B
     * @param time_european Temps en hores per arribar de A a B en Europa
     * @param time_african Temps en hores per arribar de A a B en Africa
     * @param distance Distancia entre A i B
     */
    public KnownRoutes(int placeA, int placeB, float time_european, float time_african, float distance) {
        this.placeA = placeA;
        this.placeB = placeB;
        this.time_european = time_european;
        this.time_african = time_african;
        this.distance = distance;
        this.place = null;
    }

    /**
     * Metode que afegueix un lloc a la ruta
     * @param place
     */
    public void setPlace(InterestingPlaces place){
        this.place = place;
    }

    /**
     * Metode que retorna el lloc de la ruta
     */
    public InterestingPlaces getPlace() {
        return place;
    }

    /**
     * Metode que retorna el lloc B
     * @return Lloc B
     */
    public int getPlaceB() {
        return placeB;
    }

    /**
     * Metode que retorna el lloc A
     * @return Lloc A
     */
    public int getPlaceA() {
        return placeA;
    }

    /**
     * Metode que retorna el temps en hores per arribar de A a B en Europa
     * @return Temps en hores per arribar de A a B en Europa
     */
    public float getTime_european() {
        return time_european;
    }

    /**
     * Metode que retorna el temps en hores per arribar de A a B en Africa
     * @return Temps en hores per arribar de A a B en Africa
     */
    public float getTime_african() {
        return time_african;
    }

    /**
     * Metode que retorna la distancia entre A i B
     * @return Distancia entre A i B
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Metode que retorna si el lloc passat per parametre coincideix amb algun dels llocs de la ruta
     * @param place Lloc a comparar
     * @return Retorna true si el lloc passat per parametre coincideix amb algun dels llocs de la ruta
     */
    public boolean getPlaceMatch(int place){
        if(this.placeA == place || this.placeB == place){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Metode que retorna si els llocs passats per parametre coincideixen amb els llocs de la ruta
     * @param placeA Lloc A a comparar
     * @param placeB Lloc B a comparar
     * @return Retorna true si els llocs passats per parametre coincideixen amb els llocs de la ruta
     */
    public boolean samePlaces(int placeA, int placeB) {
        if (this.placeB == placeB && this.placeA == placeA) {
            return true;
        }
        return false;
    }
}
