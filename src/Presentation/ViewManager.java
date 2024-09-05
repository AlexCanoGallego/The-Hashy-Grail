package Presentation;

import Bussines.RTrees.Bardissa;
import Bussines.Trees.Habitant;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Aquesta classe es la encarregada de gestionar la interacció amb l'usuari.
 * Es la encarregada de mostrar els menus i les opcions que te l'usuari.
 * També es la encarregada de demanar les dades a l'usuari i retornar-les.
 * Aquesta classe es la encarregada de mostrar els missatges d'error.
 */
public class ViewManager {
    private final Scanner scanner;

    /**
     * Constructora de la classe ViewManager.
     */
    public ViewManager(){
        this.scanner = new Scanner(System.in);
    }

    /**
     * Metode que mostra el menu principal.
     */
    public void showMenu(){
        System.out.println("\n1. Sobre Orenetes i cocos (Grafs)");
        System.out.println("2. Caça de bruixes (Arbres binaris de cerca)");
        System.out.println("3. Tanques de bardissa (Arbres R)");
        System.out.println("4. D’heretges i blasfems (Taules)\n");
        System.out.println("5. Exit\n");
    }

    /**
     * Metode que mostra el menu de la opcio 1.
     */
    public void showOptionOne(){
        System.out.println("\n\tA. Exploració del regne");
        System.out.println("\tB. Detecció de trajectes habituals");
        System.out.println("\tC. Missatgeria premium\n");
        System.out.println("\tD. Tornar enrere\n");
    }

    /**
     * Metode que mostra el menu de la opcio 2.
     */
    public void showOptionTwo(){
        System.out.println("\n\tA. Afegir Habitant");
        System.out.println("\tB. Eliminar habitant");
        System.out.println("\tC. Representació visual");
        System.out.println("\tD. Identificació de bruixes");
        System.out.println("\tE. Batuda");
        System.out.println("\n\tF. Tornar enrere\n");
    }

    /**
     * Metode que mostra el menu de la opcio 3.
     */
    public void showOptionThree(){
        System.out.println("\n\tA. Afegir bardissa");
        System.out.println("\tB. Eliminar bardissa");
        System.out.println("\tC. Visualització");
        System.out.println("\tD. Cerca per àrea");
        System.out.println("\tE. Optimització estètica");
        System.out.println("\n\tF. Tornar enrere\n");
    }

    /**
     * Metode que mostra el menu de la opcio 4.
     */
    public void showOptionFour(){
        System.out.println("\n\tA. Afegir acusat");
        System.out.println("\tB. Eliminar acusat");
        System.out.println("\tC. Edicte de gràcia");
        System.out.println("\tD. Judici Final (un acusat)");
        System.out.println("\tE. Judici Final (rang)");
        System.out.println("\tF. Histograma per professions");
        System.out.println("\n\tG. Tornar enrere\n");
    }

    /**
     * Metode que demana a l'usuari les dades dels habitants.
     * @return retorna un habitant amb les dades que ha introduit l'usuari.
     */
    public Habitant askHabitant() {
        Habitant new_habitant = new Habitant();

        System.out.print("Identificador de l'habitant: ");
        new_habitant.setId(askForInt());
        System.out.print("Nom de l'habitant: ");
        new_habitant.setName(askForString());
        System.out.print("Pes de l'habitant: ");
        new_habitant.setWeight(askForDouble());
        System.out.print("Regne de l'habitant: ");
        new_habitant.setKingdom(askForString());

        return new_habitant;
    }

    /**
     * Metode que informa l'estat de la operacio d'afegir un habitant.
     * @param ok es un boolea que indica si s'ha afegit correctament o no.
     * @param name_habitant es el nom de l'habitant que s'ha afegit.
     */
    public void succesfullyAddedHabitant(boolean ok, String name_habitant) {
        System.out.println();
        if (ok) {
            System.out.println(name_habitant + " ens acompanyarà a partir d'ara.");
        } else {
            System.out.println("ERROR: Ja hi ha algú amb el mateix identificador!");
        }
    }

    /**
     * Metode que mostra el Tree visualment.
     * @param name nom del habitant.
     * @param regne regne del habitant.
     * @param id identificador del habitant.
     * @param pes pes del habitant.
     * @param root indica si es la arrel o no.
     */
    public void printTree(String name, String regne, int id, double pes, int root){
        if(root == 0)
            System.out.println("* " + name + " (" + id + ", " + regne + "): " + pes);
        else
            System.out.println("|--- " + name + " (" + id + ", " + regne + "): " + pes);
    }

    /**
     * Metode que pregunta a l'usuari l'identificador del habitant que vol eliminar.
     * @return retorna l'identificador del habitant que vol eliminar.
     */
    public int askRemoveId() {
        System.out.print("Identificador del habitant: ");
        return askForInt();
    }

    /**
     * Metode que informa l'estat de la operacio d'eliminar un habitant.
     * @param ok es un boolea que indica si s'ha eliminat correctament o no.
     * @param removed_name es el nom de l'habitant que s'ha eliminat.
     */
    public void succesfullyRemovedHabitant(boolean ok, String removed_name) {
        System.out.println();
        if (ok) {
            System.out.println(removed_name + " ha estat transformat en un grill.");
        } else {
            System.out.println("ERROR: No s'ha trobat ningú amb aquest identificador.\n");
        }
    }

    /**
     * Metode que printa si no s'ha trobat cap bruixa o sino cuantes s'han trobat
     * @param num es el tamany de la llista d'habitants que s'han afegit a la llista,o sigui que s'han trobat segons les especificaions.
     */
    public void printNumWitches(int num) {
        System.out.println();
        if (num == 0) {
            System.out.println ("No s'ha capturat cap bruixa!");
        } else {
            System.out.println("S'han capturat " + num + " bruixes!");
        }
    }

    /**
     * Metode que printa les bruixes que s'han trobat.
     * @param witch es la bruixa que s'ha trobat.
     */
    public void printWitch(Habitant witch) {
        System.out.println("\t* " + witch.getName() + " (" + witch.getId() + ", " + witch.getKingdom() + "): " + witch.getWeight() + "kg");
    }

    /**
     * Metode que pregunta a l'usuari la bardissa que vol afegir.
     * @return retorna la bardissa que vol afegir.
     */
    public Bardissa askBardissa() {
        Bardissa new_bardissa = new Bardissa();

        System.out.print("Tipus de la bardissa: ");
        switch (askForString()){
            case "CIRCLE":
                new_bardissa.setTipus(0);
                break;
            case "SQUARE":
                new_bardissa.setTipus(1);
                break;
            default:
                return null;
        }

        System.out.print("Mida de la bardissa: ");
        new_bardissa.setMida(askForDouble());
        System.out.print("Latitud de la bardissa: ");
        new_bardissa.setLatitud(askForDouble());
        System.out.print("Longitud de la bardissa: ");
        new_bardissa.setLongitud(askForDouble());
        System.out.print("Color de la bardissa: ");
        new_bardissa.setColor(hexToRgb(askForString()));

        return new_bardissa;
    }

    /**
     * Metode que informa l'estat de la operacio d'afegir una bardissa.
     * @param hex_color es el color de la bardissa que s'ha afegit.
     * @return retorna el color de la bardissa.
     */
    private static Color hexToRgb(String hex_color) {
        return new Color(
                Integer.valueOf( hex_color.substring(1, 2), 16 ),
                Integer.valueOf( hex_color.substring(3, 4), 16 ),
                Integer.valueOf( hex_color.substring(5, 6), 16 )
        );
    }

    /**
     * De Color a String en formato Hexadecimal #43A047
     * @param color es el color de la bardissa que s'ha afegit.
     * @return retorna el color de la bardissa.
     */
    public static String colorToHex(Color color) {
        String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        return hex;
    }

    /**
     * Metode que mostrar el RTRee visualment.
     * @param latitud latitud
     * @param longitud longitud
     * @param tipus tipus
     * @param mida mida
     * @param color color
     */
    public void printRTree(double latitud, double longitud, int tipus, double mida, Color color){

        if(tipus == 0)
            System.out.println("* " + latitud + ", " + longitud + ": CIRCLE (r=" + mida + "m) " + colorToHex(color));
        else
            System.out.println("* " + latitud + ", " + longitud + ": SQUARE (d=" + mida + "m) " + colorToHex(color));
    }

    //----------- ASK ------------------------------------

    /**
     * Metode que pregunta a l'usuari un String.
     * @return retorna el String que ha introduit l'usuari.
     */
    public int askForInt() {
        while(true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Això no és un enter!\n");
                System.out.print("Introdueix un enter: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que pregunta a l'usuari un double.
     * @param msg es el missatge que es mostra per pantalla.
     * @return retorna el double que ha introduit l'usuari.
     */
    public double askForDouble(String msg) {
        while(true) {
            try {
                System.out.print(msg);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Això no és un real!\n");
                System.out.print("Introdueix un real: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que pregunta a l'usuari un double.
     * @return retorna el double que ha introduit l'usuari.
     */
    public double askForDouble() {
        while(true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Això no és un double!\n");
                System.out.print("Introdueix un double: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que pregunta a l'usuari un char.
     * @return retorna el char que ha introduit l'usuari.
     */
    public char askForChar() {
        char option;
        while(true) {
            try {
                option =  (char) scanner.nextLine().charAt(0);
                return option;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Això no és un caràcter!");
                System.out.print("Introdueix un caràcter: ");
            }
        }
    }

    /**
     * Metode que pregunta a l'usuari un String.
     * @return retorna el String que ha introduit l'usuari.
     */
    public String askForString(){
        return  scanner.nextLine();
    }
}

