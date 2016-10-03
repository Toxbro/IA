/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import environnement.Environnement;

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
}
