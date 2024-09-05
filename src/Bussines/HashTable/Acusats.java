package Bussines.HashTable;

public class Acusats {
    private String nom;
    private int numConills;
    private String professio;
    private Boolean heretge;

    /**
     * Constructor de la classe Acusats
     * @param nom Nom de l'acusat
     * @param numConills Numero de conills de l'acusat
     * @param professio Professio de l'acusat
     * @param heretge Si es heretge o no
     */
    public Acusats(String nom, int numConills, String professio,Boolean heretge) {
        this.nom = nom;
        this.numConills = numConills;
        this.professio = professio;
        this.heretge = heretge;
    }

    /**
     * Metode que retorna el nom de l'acusat
     * @return Nom de l'acusat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Metode que retorna el numero de conills de l'acusat
     * @return Numero de conills de l'acusat
     */
    public int getNumConills() {
        return numConills;
    }

    /**
     * Metode que retorna la professio de l'acusat
     * @return Professio de l'acusat
     */
    public String getProfessio() {
        return professio;
    }

    /**
     * Metode que retorna si l'acusat es heretge o no
     * @return Si l'acusat es heretge o no
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Metode que modifica el numero de conills de l'acusat
     * @param numConills Numero de conills de l'acusat
     */
    public void setNumConills(int numConills) {
        this.numConills = numConills;
    }

    /**
     * Metode que modifica la professio de l'acusat
     * @param professio Professio de l'acusat
     */
    public void setProfessio(String professio) {
        this.professio = professio;
    }

    /**
     * Metode que modifica si l'acusat es heretge o no
     * @param heretge Si l'acusat es heretge o no
     */
    public Boolean getHeretge() {
        return heretge;
    }

    /**
     * Metode que modifica si l'acusat es heretge o no
     * @param heretge Si l'acusat es heretge o no
     */
    public void setHeretge(Boolean heretge) {
        this.heretge = heretge;
    }
}

