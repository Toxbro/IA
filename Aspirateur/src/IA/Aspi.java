/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import java.util.Map;
import environnement.Cell;
import java.util.HashMap;
import main.Main;

/**
 *
 * @author quentin
 */

public class Aspi implements Runnable {
    
    private int x,y;
    private int conso;
    private HashMap<Cell, Integer> grid = new HashMap<Cell, Integer>();
    private Main master;
    private boolean isExploDone;
    
    public Aspi(Main master) {
        this.master = master;
        this.x = 0;
        this.y = 0;
        this.isExploDone = false;
    }
    
    @Override
    public void run() {
        while(true) {
            boolean action = false;
            if(!action) {
                if(getJewelState()) {
                    pick();
                    action = true;
                }
                if(getDustState()) {
                    suck();
                    action = true;
                }
                if(!getJewelState() && !getJewelState()) {
                    move();
                    action = true;
                }
            }
        }
    }
    
    private void updateBelief() {
        
    }
    
    private void move() {
        Direction dir = pattern.get(0);
        pattern.remove(0);
        pattern.add(dir);
        switch(dir){
            case DOWN: y--;
            case UP: y++;
            case LEFT: x--;
            case RIGHT: x++;
        }
        master.botMove(dir);
    }
    
    private Boolean getDustState() {
        return master.getDustState();
    }
    
    private Boolean getJewelState() {
        return master.getJewelState();
    }
    
    private void suck() {
        master.suck();
    }
    
    private void pick() {
        master.pick();
    }
}


