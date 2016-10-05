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
                    move(path.get(0));
                    path.remove(0);
                    break;
                }
            }
        }
    }
    
    private void explore() {
        
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
            if(c.getCol() == currentCell.getCol() && c.getRow() == currentCell.getRow()+1)
                result.add(Direction.DOWN);
            else if(c.getCol() == currentCell.getCol() && c.getRow() == currentCell.getRow()-1)
                result.add(Direction.UP);
            else if(c.getCol() == currentCell.getCol()+1 && c.getRow() == currentCell.getRow())
                result.add(Direction.RIGHT);
            else if(c.getCol() == currentCell.getCol()-1 && c.getRow() == currentCell.getRow())
                result.add(Direction.LEFT);
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
        
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()){
            Cell cell = entry.getKey();
            if((cell.getCol() == currentCell.getCol() && cell.getRow() == currentCell.getRow()+1) ||
                (cell.getCol() == currentCell.getCol() && cell.getRow() == currentCell.getRow()-1) ||
                (cell.getCol() == currentCell.getCol()+1 && cell.getRow() == currentCell.getRow()) ||
                (cell.getCol() == currentCell.getCol()-1 && cell.getRow() == currentCell.getRow())) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    } 
    
    private void move(Direction dir) {
        switch(dir){
            case DOWN: currentCell = getCell(currentCell.getCol(),currentCell.getRow()+1);
            case UP: currentCell = getCell(currentCell.getCol(),currentCell.getRow()-1);
            case LEFT: currentCell = getCell(currentCell.getCol()-1,currentCell.getRow());
            case RIGHT: currentCell = getCell(currentCell.getCol()+1,currentCell.getRow());
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
    
    private Cell getCell(int x, int y) {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            if(entry.getKey().getCol() == x && entry.getKey().getRow() == y)
                return entry.getKey();
        }
        return null;
    }
}


