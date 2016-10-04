/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import environnement.Environnement;
import IA.*;

/**
 *
 * @author Thomas
 */
public final class Main {
    
    private  Environnement environnement;
    
    public Main (){
        setEnvironnement(new Environnement(this));
        Thread t = new Thread(getEnvironnement());
        t.start();
    }
    
    public static void main(String [] args){
        Main main = new Main();
    }

    /**
     * @return the environnement
     */
    public Environnement getEnvironnement() {
        return environnement;
    }

    /**
     * @param aEnvironnement the environnement to set
     */
    public void setEnvironnement(Environnement aEnvironnement) {
        environnement = aEnvironnement;
    }
    
    public boolean botMove(Direction dir) {
        //Le robot appelle cette méthode pour signifier au master qu'il s'est déplacé.
        //return true ou false, dépendamment de si le mouvement était possible oupas.
        return false;
    }
    
    public boolean getDustState() {
        //retourne l'état de la poussière sur la case actuelle du robot.
        return false;
    }
    
    public boolean getJewelState() {
        //retourne l'état des bijous sur la case actuelle du robot.
        return false;
    }
    
    public void suck() {
        //le robot aspire la poussière et les bijous
    }
    
    public void pick() {
        //le robot prend un bijou
    }
}
