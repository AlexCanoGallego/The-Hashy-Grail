package Bussines.HashTable;
import java.util.*;

public class TablaHash {
    private LinkedList<Acusats>[] tabla;
    private int tamano;

    /**
     * Constructor de la classe TablaHash
     * @param tamano Tamany de la tabla
     */
    public TablaHash(int tamano)
    {
        this.tamano = tamano;
        tabla = new LinkedList[tamano];
        for (int i = 0; i < tamano; i++)
        {
            tabla[i] = new LinkedList<Acusats>();
        }
    }

    /**
     * Metode que posa a la tabla un acusat
     * @param p Acusat a posar
     */
    public void put(Acusats p)
    {
        int indice = obtenerIndice(p.getNom());
        LinkedList<Acusats> lista = tabla[indice];
        lista.add(p);
    }

    /**
     * Metode que retorna un acusat de la tabla
     * @param name Nom de l'acusat
     * @return Acusat
     */
    public Acusats get(String name)
    {
        int indice = obtenerIndice(name);
        LinkedList<Acusats> lista = tabla[indice];
        for (Acusats p : lista)
        {
            if (Objects.equals(p.getNom(), name))
            {
                return p;
            }
        }
        return null;
    }

    /**
     * Metode que elimina un acusat de la tabla
     * @param name Nom de l'acusat
     */
    public void remove(String name)
    {
        int indice = obtenerIndice(name);
        LinkedList<Acusats> lista = tabla[indice];
        for (Acusats p : lista)
        {
            if (Objects.equals(p.getNom(), name))
            {
                lista.remove(p);
                break;
            }
        }
    }

    /**
     * Metode que conte un acusat a la tabla
     * @param name Nom de l'acusat
     * @return True si el conte, false si no
     */
    public boolean containsKey(String name)
    {
        int indice = obtenerIndice(name);
        LinkedList<Acusats> lista = tabla[indice];
        for (Acusats p : lista)
        {
            if (Objects.equals(p.getNom(), name))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Metode que retorna el index d'un acusat
     * @param name Nom de l'acusat
     * @return Index de l'acusat
     */
    private int obtenerIndice(String name)
    {
        return sumaASCII(name) % tamano;
    }

    /**
     * Metode que suma els valors ASCII d'una cadena
     * @param cadena Cadena a sumar
     * @return Suma dels valors ASCII
     */
    public static int sumaASCII(String cadena) {
        cadena = cadena.toLowerCase().replace(" ", "");
        int suma = 0;
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            int valorASCII = (int) c;
            suma += valorASCII * (i + 1);
        }
        return suma;
    }

    /**
     * Metode que retorna una List de acusats que tenen un numero de conills entre min i max
     * @param min Minim de conills
     * @param max Maxim de conills
     * @return List de acusats
     */
    public List<Acusats> getAcusatsPorNumConills(int min, int max) {
        List<Acusats> acusados = new ArrayList<>();

        for (LinkedList<Acusats> lista : tabla) {
            for (Acusats acusado : lista) {
                if (acusado.getNumConills() >= min && acusado.getNumConills() <= max) {
                    acusados.add(acusado);
                }
            }
        }

        return acusados;
    }

    /**
     * Metode que conta els hereus per profesio
     */
    public void contarHerejesPorProfesion() {
        //HashMap<String, Integer> contadorHerejesPorProfesion = new HashMap<String, Integer>();
        String[] profesions = {"MINSTREL", "KNIGHT", "KING", "QUEEN", "PEASANT", "SHRUBBER", "CLERGYMAN", "ENCHANTER"};
        int counterM=0,counterKn=0,counterK=0,counterQ=0,counterP=0,counterS=0,counterC=0,counterE=0,counterTotal=0;
        // Recorremos la tabla para contar el número de herejes por profesión
        for (LinkedList<Acusats> lista : tabla) {
            for (Acusats acusado : lista) {
                if (acusado.getHeretge()) {
                    String profesion = acusado.getProfessio();
                    switch (profesion)
                    {
                        case "MINSTREL":
                            counterM++;
                            break;
                        case "KNIGHT":
                            counterKn++;
                            break;
                        case "KING":
                            counterK++;
                            break;
                        case "QUEEN":
                            counterQ++;
                            break;
                        case "PEASANT":
                            counterP++;
                            break;
                        case "SHRUBBER":
                            counterS++;
                            break;
                        case "CLERGYMAN":
                            counterC++;
                            break;
                        case "ENCHANTER":
                            counterE++;
                            break;
                    }
                    counterTotal++;
                }
            }
        }
        int[] counters ={counterM,counterKn,counterK,counterQ,counterP,counterS,counterC,counterE};

        // Imprimimos los resultados
        for (int i = 0; i < profesions.length; i++) {
            System.out.print(profesions[i]+":");
            int numTimes = (int) (counters[i]*100f/counterTotal);
            for (int j = 0; j < numTimes; j++) {
                System.out.print("#");
            }
            System.out.println("\t\t/ hi han "+counters[i]+" heretges");
        }
    }

}

