/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import environnement.*;
import IA.*;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public final class Main {
    
    private  Environnement environnement;
    
    private graphic.Main graph;
    
    private Aspi robot;
    
    private Cell currentRobotCell;
    
    private Cell initialRobotCell;
    
    public Main (){
        
        setGraph(new graphic.Main(this));
        Thread tGraphic = new Thread(getGraph());
        tGraphic.start();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setEnvironnement(new Environnement(this));
        Thread tEnvironnement = new Thread(getEnvironnement());
        tEnvironnement.start();
        
        //Temporisation for cell creation
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentRobotCell = environnement.getGrid().getCell(0, 0);
        initialRobotCell = currentRobotCell;
        graph.view.addR(currentRobotCell.getCol(), currentRobotCell.getRow());
        
        //DEBUG
        System.out.println("Robot is currently on : "+currentRobotCell.toString());
        
        setRobot(new Aspi(this, 10));
        Thread tRobot = new Thread(getRobot());
        tRobot.start();
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
    
    public void botMove(Direction dir) {
        Grid grid = environnement.getGrid();
        Cell newCell = null;
        
        System.out.println(dir);
        
        switch(dir){
            case LEFT:
                newCell = grid.getCell(currentRobotCell.getRow(), currentRobotCell.getCol()-1);
                if ((newCell != null) && (newCell.getEnable())) {
                    graph.view.mvtR(currentRobotCell.getCol(), currentRobotCell.getRow(), newCell.getCol(), newCell.getRow());  
                    currentRobotCell = newCell;
                }
                break;
            case RIGHT:
                newCell = grid.getCell(currentRobotCell.getRow(), currentRobotCell.getCol()+1);
                if ((newCell != null) && (newCell.getEnable())) {
                    graph.view.mvtR(currentRobotCell.getCol(), currentRobotCell.getRow(), newCell.getCol(), newCell.getRow()); 
                    currentRobotCell = newCell;
                }
                break;
            case UP:
                newCell = grid.getCell(currentRobotCell.getRow()-1, currentRobotCell.getCol());
                if ((newCell != null) && (newCell.getEnable())) {
                    graph.view.mvtR(currentRobotCell.getCol(), currentRobotCell.getRow(), newCell.getCol(), newCell.getRow()); 
                    currentRobotCell = newCell;
                }
                break;
            case DOWN:
                newCell = grid.getCell(currentRobotCell.getRow()+1, currentRobotCell.getCol());
                if ((newCell != null) && (newCell.getEnable())) {
                    graph.view.mvtR(currentRobotCell.getCol(), currentRobotCell.getRow(), newCell.getCol(), newCell.getRow()); 
                    currentRobotCell = newCell;
                }
                break;
        }
        
        System.out.println("Robot déplacé en "+currentRobotCell.getRow()+','+currentRobotCell.getCol());
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
        graph.view.delD(currentRobotCell.getCol(), currentRobotCell.getRow());
        graph.view.delJ(currentRobotCell.getCol(), currentRobotCell.getRow());
    }
    
    //le robot prend un bijou
    public void pick() {
        currentRobotCell.removeObject(Type.JEWEL);
        graph.view.delJ(currentRobotCell.getCol(), currentRobotCell.getRow());
    }
    
    /**
     * Method called by GUI or by Environnement : determined by {@link StackTraceElement}
     * Add dust to the environnement or the GUI
     */ 
    public void addDust(int r, int c){
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements[stackTraceElements.length-2].getClassName().equals("environnement.Environnement")) {
            System.out.println("Called by environnement");
            graph.view.addD(c, r);
            
        }
        else{
            environnement.getGrid().getCell(r, c).addObject(Type.DUST);
            System.out.println("Called by user");
        }
    }
    
    /**
     * Method called by GUI or by Environnement : determined by {@link StackTraceElement}
     * Add jewel to the environnement or the GUI
     */ 
    public void addJewel(int r, int c){
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        //GUI -> complete what to do
        if (stackTraceElements[stackTraceElements.length-2].getClassName().equals("environnement.Environnement")) {
            System.out.println("Called by environnement");
            graph.view.addJ(c, r);
        }
        //GUI -> complete if
        else{
            environnement.getGrid().getCell(r, c).addObject(Type.JEWEL);
            System.out.println("Called by user");
        }
    }

    /**
     * @return the graph
     */
    public graphic.Main getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(graphic.Main graph) {
        this.graph = graph;
    }
    
    public void setFrequency(int freq){
        //System.out.println("main.Main.setFrequency()"+freq);
        environnement.setSecondsToLoop(freq);
    }
    public void setDustProb(int prob){
        //System.out.println("main.Main.setDustProb()"+prob);
        environnement.setPercentageDust(prob);
    }
    public void setJewelProb(int prob){
        //System.out.println("main.Main.setJewelProb()"+prob);
        environnement.setPercentageJewel(prob);
    }
    
    public boolean isCellEnabled(Cell c) {
        Grid grid = environnement.getGrid();
        Cell cell;
        
        cell = grid.getCell(c.getRow()+initialRobotCell.getRow(), c.getCol()+initialRobotCell.getCol());
        if(cell != null)
            return cell.getEnable();
        return false;
    }

    /**
     * @return the robot
     */
    public Aspi getRobot() {
        return robot;
    }

    /**
     * @param robot the robot to set
     */
    public void setRobot(Aspi robot) {
        this.robot = robot;
    }
}
