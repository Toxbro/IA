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
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 *
 * @author quentin
 */

public class Aspi implements Runnable {
    
    private Cell currentCell;
    private int conso;
    private HashMap<Cell, Integer> grid = new HashMap<Cell, Integer>();
    private Main master;
    private boolean isExploDone;
    private ArrayList<Direction> path = new ArrayList<Direction>();
    private boolean isCellClean = false;
    private int cellTotal;
    
    public Aspi(Main master, int cellTotal) {
        this.master = master;
        this.currentCell = new Cell(0, 0);
        this.isExploDone = false;
        this.path = null;
        this.cellTotal = cellTotal;
        this.grid.put(new Cell(0, 0), 0);
    }
    
    @Override
    public void run() {
        while(true) {
           updateBelief();
           switch(getDesire()){
                case EXPLORE:{
                    explore();
                    break;
                }
                case GETNEWGOAL:{
                    path = getPath(currentCell, getGoal());
                    break;
                }
                case PICK: {
                    pick();
                    break;
                }
                case CLEAN:{
                    suck();
                    break;
                }
                case MOVETOGOAL:{
                    if(getJewelState() || getDustState())
                        break;
                    move(path.get(0));
                    path.remove(0);
                    break;
                }
            }
        }
    }
    
    private void explore() {
        ArrayList<Cell> adjacent = getAdj(currentCell);
        ArrayList<Cell> unknown = new ArrayList<Cell>();
        for(Cell c : adjacent) {
            if(!grid.containsKey(c)) {
                grid.put(c, 0);
                unknown.add(c);
                cellTotal--;
            }
        }
        if(cellTotal == 0)
            isExploDone = true;
        else
            move(getDir(unknown.get((int) (Math.random() * unknown.size()))));
    }
    
    private void updateBelief() {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            entry.setValue(entry.getValue()+1);
        }
    }
    
    private Desire getDesire(){
        if(!isExploDone)
            return Desire.EXPLORE;
        else if(path.isEmpty())
            return Desire.GETNEWGOAL;
        else if(getJewelState())
            return Desire.PICK;
        else if(getDustState())
            return Desire.CLEAN;
        else
            return Desire.MOVETOGOAL;
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
    
    private ArrayList<Direction> getPath(Cell start, Cell end) {
        ArrayList<ArrayList<Cell>> choice = getPathList(start, end);
        ArrayList<Cell> path = new ArrayList<Cell>();
        ArrayList<Direction> result = new ArrayList<Direction>();
        int dustAmount = 0, maxDust = 0;
        
        for(ArrayList<Cell> p : choice) {
            for(Cell c : p) {
                for(Map.Entry<Cell, Integer> entry : grid.entrySet()){
                    if(entry.getKey() == c) {
                        dustAmount += entry.getValue();
                    }
                }
            }
            if(dustAmount > maxDust) {
                maxDust = dustAmount;
                path = p;
            }
        }
        
        for(Cell c : path) {
            result.add(getDir(c));
        }
        
        return result;
    }
    
    private ArrayList<ArrayList<Cell>> getPathList(Cell start, Cell end) {
        ArrayList<ArrayList<Cell>> result = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<Cell>> tempResult;
        ArrayList<Cell> adjacent = new ArrayList<Cell>();
        int dist = Integer.MAX_VALUE;
        
        adjacent = getClosestAdj(start, end);

        for(Cell c : adjacent){
            
            if(c != end) {
                tempResult = getPathList(c, end);
                
                for(ArrayList<Cell> r : tempResult) {
                    r.add(0, start);
                    result.add(r);
                }
            }
            else {
                result.add(new ArrayList<Cell>() {{
                    add(start); 
                    add(end);
                }});
            }
        }

        return result;
    }
    
    private ArrayList<Cell> getClosestAdj(Cell start, Cell end){
        ArrayList<Cell> result = new ArrayList<Cell>();
        ArrayList<Cell> adjacent;
        HashMap<Cell, Integer> closeAdj= new HashMap<Cell, Integer>();
        int dist = Integer.MAX_VALUE;
        
        adjacent = getAdj(start);
        
        for(Cell c : adjacent){
            closeAdj.put(c, Math.abs(c.getCol() - currentCell.getCol()) + Math.abs(c.getRow() - currentCell.getRow()));
        }
        
        for(Map.Entry<Cell, Integer> entry : closeAdj.entrySet()) {
            if(entry.getValue() < dist) {
                dist = entry.getValue();
                result = new ArrayList<Cell>();
                result.add(entry.getKey());
            }
            else if(entry.getValue() == dist) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }
    
    private ArrayList<Cell> getAdj(Cell c){
        ArrayList<Cell> result = new ArrayList<Cell>();
        
        for(Direction d : Direction.values()){
            result.add(getCell(c, d));
        }
        return result;
    } 
    
    private void move(Direction dir) {
        conso++;
        currentCell = getCell(currentCell, dir);
        master.botMove(dir);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aspi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean getDustState() {
        return master.getDustState();
    }
    
    private boolean getJewelState() {
        return master.getJewelState();
    }
    
    private void suck() {
        conso++;
        master.suck();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aspi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void pick() {
        conso++;
        master.pick();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aspi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Cell getCell(int x, int y) {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            if(entry.getKey().getCol() == x && entry.getKey().getRow() == y)
                return entry.getKey();
        }
        if(!isExploDone)
            return new Cell(y,x);
        else
            return null;
    }
    
    private Cell getCell(Cell c, Direction dir) {
        switch(dir){
            case RIGHT:
                return getCell(c.getCol()+1,c.getRow());
            case LEFT:
                return getCell(c.getCol()-1,c.getRow());
            case UP:
                return getCell(c.getCol(),c.getRow()-1);
            case DOWN:
                return getCell(c.getCol(),c.getRow()+1);
            default:
                return null;
        }
    }
    
    private Direction getDir(Cell c) {
        if(c.getCol() == currentCell.getCol() && c.getRow() == currentCell.getRow()+1)
            return Direction.DOWN;
        else if(c.getCol() == currentCell.getCol() && c.getRow() == currentCell.getRow()-1)
            return Direction.UP;
        else if(c.getCol() == currentCell.getCol()+1 && c.getRow() == currentCell.getRow())
            return Direction.RIGHT;
        else if(c.getCol() == currentCell.getCol()-1 && c.getRow() == currentCell.getRow())
            return Direction.LEFT;
        else
            return null;
    }
}


