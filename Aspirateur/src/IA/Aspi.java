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
    private Cell goal;
    
    public Aspi(Main master) {
        this.master = master;
        this.x = 0;
        this.y = 0;
        this.isExploDone = false;
        this.goal = null;
    }
    
    @Override
    public void run() {
        while(true) {
           updateBelief();
           switch(getDesire()){
               
           }
        }
    }
    
    private void updateBelief() {
        if(isExploDone) {
            updateGrid();
        }
        else {
            grid.put(new Cell(y, x), 0);
        }
    }
    
    private int getDesire(){
         if(isExploDone && goal == null) {
            goal = setNewGoal();
        }
        if(isExploDone && goal != null) {
            updateGrid();
        }
        else {
            grid.put(new Cell(y, x), 0);
        }
    }
    
    private void move(Direction dir) {
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
    
    private void updateGrid() {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            entry.setValue(entry.getValue()+1);
        }
    }
}


