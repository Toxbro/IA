/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import environnement.*;
import IA.*;

/**
 *
 * @author Thomas
 */
public final class Main {
    
    private  Environnement environnement;
    
    private Cell currentRobotCell;
    
    public Main (){
        setEnvironnement(new Environnement(this));
        Thread t = new Thread(getEnvironnement());
        t.start();
        
        currentRobotCell = environnement.getRandomCell();
        
        //DEBUG
        System.out.println("Robot is currently on : "+currentRobotCell.toString());
        
        //Initialize other threads
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
        Grid grid = environnement.getGrid();
        Cell newCell;
        
        if (dir.equals(Direction.LEFT)) {
            newCell = grid.getCell(currentRobotCell.getRow(), currentRobotCell.getCol()-1);
            if ((newCell != null) && (newCell.getEnable())) {
                currentRobotCell = newCell;
                
                //Send event to GUI
                
                return true;
            }   
        }
        if (dir.equals(Direction.RIGHT)) {
            newCell = grid.getCell(currentRobotCell.getRow(), currentRobotCell.getCol()+1);
            if ((newCell != null) && (newCell.getEnable())) {
                currentRobotCell = newCell;
                
                //Send event to GUI
                
                return true;
            }   
        }
        if (dir.equals(Direction.UP)) {
            newCell = grid.getCell(currentRobotCell.getRow()-1, currentRobotCell.getCol());
            if ((newCell != null) && (newCell.getEnable())) {
                currentRobotCell = newCell;
                
                //Send event to GUI
                
                return true;
            }   
        }
        if (dir.equals(Direction.DOWN)) {
            newCell = grid.getCell(currentRobotCell.getRow()+1, currentRobotCell.getCol());
            if ((newCell != null) && (newCell.getEnable())) {
                currentRobotCell = newCell;
                
                //Send event to GUI
                
                return true;
            }   
        }
        return false;
    }
    
    //retourne l'état de la poussière sur la case actuelle du robot.
    public boolean getDustState() {
        return currentRobotCell.hasObject(Type.DUST);
    }
    
    //retourne l'état des bijous sur la case actuelle du robot.
    public boolean getJewelState() {
        return currentRobotCell.hasObject(Type.JEWEL);
    }
    
    //le robot aspire la poussière et les bijous
    public void suck() {
        currentRobotCell.removeAllObjects();
        //Send event to GUI
    }
    
    //le robot prend un bijou
    public void pick() {
        currentRobotCell.removeObject(Type.JEWEL);
        //Send event to GUI
    }
}
