/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import java.util.Map;
import environnement.Cell;
import java.util.ArrayList;
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
    private ArrayList<Direction> path = new ArrayList<Direction>();
    private boolean isCellClean;
    
    public Aspi(Main master) {
        this.master = master;
        this.x = 0;
        this.y = 0;
        this.isExploDone = false;
        this.path = null;
    }
    
    @Override
    public void run() {
        while(true) {
           updateBelief();
           switch(getDesire()){
                case GETNEWGOAL:{
                    getNewGoal();
                }
                case MOVETOGOAL:{
                    
                }
                case CLEAN:{
                    
                }
                case EXPLORE:{
                    
                }
            }
        }
    }
    
    private void updateBelief() {
        updateGrid();        
        if(!isExploDone)
            grid.put(new Cell(y, x), 0);
    }
    
    private Desire getDesire(){
        if(isExploDone && path == null) {
            return Desire.GETNEWGOAL;
        }
        else if(isExploDone && path != null && !isCellClean) {
            return Desire.CLEAN;
        }
        else if(isExploDone && path != null && isCellClean) {
            return Desire.MOVETOGOAL;
        }
        else {
            return Desire.EXPLORE;
        }
    }
    
    private Cell getGoal(){
        int max = 0;
        Cell dirtiest = null;
        
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()){
            if(entry.getValue() > max) {
                max = entry.getValue();
                dirtiest = entry.getKey();
            }
        }
        return dirtiest;
    }
    
    private ArrayList<Cell> getPath(Cell start, Cell end) {
        ArrayList<Cell> result = new ArrayList<Cell>();
        int dx = 0, dy = 0;
        
        
        dx = end.getCol() - start.getCol();
        dy = end.getRow() - start.getRow();
        
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()){
            Cell cell = entry.getKey();
            if((cell.getCol() == x && cell.getRow() == y+1) ||
                (cell.getCol() == x && cell.getRow() == y-1) ||
                (cell.getCol() == x+1 && cell.getRow() == y) ||
                (cell.getCol() == x-1 && cell.getRow() == y)) {
                adjacent.add(entry.getKey());
            }
        }
        
        return result;
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
    
    private boolean getDustState() {
        return master.getDustState();
    }
    
    private boolean getJewelState() {
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


