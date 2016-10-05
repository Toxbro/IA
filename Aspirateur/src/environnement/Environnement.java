/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environnement;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 * Main class
 * @author Thomas
 */
public class Environnement implements Runnable{

    private int percentageDust = 20;
    
    private int percentageJewel = 10;
    
    private int secondsToLoop = 2;
    
    private Boolean run = true;
    
    private Grid grid;
    
    private Main main;
    
    public Environnement(Main main){
        this.main = main;
    }
    
    @Override
    public void run() {
        grid = new Grid();
        Cell cell;
        while (run) {
            try {
                int randDust = getRandom(0, 100);
                int randJewel = getRandom(0, 100);
                
                //Add dust
                if (randDust <= getPercentageDust()) {
                    Boolean cellHasObject = true;
                    while (cellHasObject) {
                        cell = getRandomCell();
                        if (!cell.hasObject(Type.DUST)) {
                            cell.addObject(Type.DUST);
                            cellHasObject = false;
                            main.addDust(cell.getRow(), cell.getCol());
                            System.out.println("Adding dust to cell "+cell.toString());
                        }
                    }
                    
                }
                
                //Add jewel
                if (randJewel <= getPercentageJewel()) {
                    Boolean cellHasObject = true;
                    while (cellHasObject) {
                        cell = getRandomCell();
                        if (!cell.hasObject(Type.JEWEL)) {
                            cell.addObject(Type.JEWEL);
                            cellHasObject = false;
                            System.out.println("Adding jewel to cell "+cell.toString());
                        }
                    }
                }
                
                Thread.sleep(getSecondsToLoop() * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private int getRandom(int min, int max){
        return (int) (Math.random() * (max - min));
    }
    
    public Cell getRandomCell(){
        Boolean cellIncorrect = true;
        Cell cell;
        int row, col;
        while (cellIncorrect) {            
            row = getRandom(0, 2);
            col = getRandom(0, 4);

            cell = getGrid().getCell(row, col);
            if ((cell != null) && (cell.getEnable())) {
                cellIncorrect = false;
                return cell;
            }
            else{
                System.out.println("Get a disabled cell");
            }
        }
        return null;
    }

    /**
     * @return the percentageDust
     */
    public int getPercentageDust() {
        return percentageDust;
    }

    /**
     * @param percentageDust the percentageDust to set
     */
    public void setPercentageDust(int percentageDust) {
        this.percentageDust = percentageDust;
    }

    /**
     * @return the percentageJewel
     */
    public int getPercentageJewel() {
        return percentageJewel;
    }

    /**
     * @param percentageJewel the percentageJewel to set
     */
    public void setPercentageJewel(int percentageJewel) {
        this.percentageJewel = percentageJewel;
    }

    /**
     * @return the secondsToLoop
     */
    public int getSecondsToLoop() {
        return secondsToLoop;
    }

    /**
     * @param secondsToLoop the secondsToLoop to set
     */
    public void setSecondsToLoop(int secondsToLoop) {
        this.secondsToLoop = secondsToLoop;
    }

    /**
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }
    
}
