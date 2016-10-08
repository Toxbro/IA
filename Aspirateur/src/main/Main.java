package main;

import environnement.*;
import IA.*;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principale du programme
 * Lance les autres modules (Environnement, Robot et Graphique) et joue le rôle d'interface
 * @author Thomas
 */
public final class Main {
    
    /**
     * Environnement avec lequel le robot doit intéragir
     */
    private  Environnement environnement;
    
    /**
     * Interface graphique de l'application
     */
    private graphic.Main graph;
    
    /**
     * Robot interagissant avec l'environnement
     */
    private Bender robot;
    
    /**
     * La cellule courante du robot
     */
    private Cell currentRobotCell;
    
    /**
     * La cellule initiale du robot
     * Le robot est placé aléatoirement sur une case de la grille.
     * S'en suit une phase d'exploration durant laquelle le robot détermine les cellules.
     * Cependant le robot s'exprime en fonction de sa cellule initiale qui correspond à la cellule [0, 0] à ses yeux.
     * La classe principale étant la seule à connaitre sa position réelle doit calculer les cellules réelles avec
     * lesquelles le robot veut intéragir.
     */
    private Cell initialRobotCell;
    
    /**
     * Constructeur de la classe
     * Lance tous les modules dans des threads distincts
     */
    public Main (){
        
        setGraph(new graphic.Main(this));
        Thread tGraphic = new Thread(getGraph());
        tGraphic.start();
        
        //Temporisation pour la génération de l'interface graphique
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setEnvironnement(new Environnement(this));
        Thread tEnvironnement = new Thread(getEnvironnement());
        tEnvironnement.start();
        
        //Temporisation pour la création des cellules
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentRobotCell = environnement.getRandomCell();
        initialRobotCell = currentRobotCell;
        graph.view.addR(currentRobotCell.getCol(), currentRobotCell.getRow());
                
        setRobot(new Bender(this, 10));
        Thread tRobot = new Thread(getRobot());
        tRobot.start();
    }
    
    /**
     * Fonction appelée au lancement du programme
     * Construit un Main ce qui a pour effet de lancer le programme
     * @param args Paramètres d'appels
     */
    public static void main(String [] args){
        Main main = new Main();
    }

    /**
     * Getter de l'environnement
     * @return L'environnement
     */
    public Environnement getEnvironnement() {
        return environnement;
    }

    /**
     * Setter de l'environnement
     * @param aEnvironnement L'environnement
     */
    public void setEnvironnement(Environnement aEnvironnement) {
        environnement = aEnvironnement;
    }
    
    /**
     * Méthode appelée lorsque le robot bouge
     * Quelque soit la direction désirée, la méthode vérifie que la cellule demandée
     * existe et est valable
     * @param dir La direction dans laquelle le robot bouge
     */
    public void botMove(Direction dir) {
        Grid grid = environnement.getGrid();
        Cell newCell = null;        
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
    }
    
    /**
     * Getter de l'état de poussière de la cellule courante du robot
     * @return Si la cellule possède de la poussière
     */
    public boolean getDustState() {
        return currentRobotCell.hasObject(Type.DUST);
    }
    
    /**
     * Getter de l'état de bijou de la cellule courante du robot
     * @return Si la cellule possède un bijou
     */
    public boolean getJewelState() {
        return currentRobotCell.hasObject(Type.JEWEL);
    }
    
    /**
     * Le robot aspire tous les objets présents sur la cellule courante
     */
    public void suck() {
        currentRobotCell.removeAllObjects();
        graph.view.delD(currentRobotCell.getCol(), currentRobotCell.getRow());
        graph.view.delJ(currentRobotCell.getCol(), currentRobotCell.getRow());
    }
    
    /**
     * Le robot prend un bijou présent sur la cellule
     */
    public void pick() {
        currentRobotCell.removeObject(Type.JEWEL);
        graph.view.delJ(currentRobotCell.getCol(), currentRobotCell.getRow());
    }
    
    /**
     * Méthode appelée par l'interface graphique ou l'environnement, cet appel est déterminé par {@link StackTraceElement}
     * Ajoute de la poussière soit à l'environnement soit à l'interface graphique dépendamment de l'appelant
     * @param r La ligne de la cellule où ajouter de la poussière
     * @param c La colonne de la cellule où ajouter de la poussière
     */ 
    public void addDust(int r, int c){
        //Utilisation de StackTraceElement pour déterminer l'appelant
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements[stackTraceElements.length-2].getClassName().equals("environnement.Environnement")) {
            graph.view.addD(c, r);
        }
        else{
            environnement.getGrid().getCell(r, c).addObject(Type.DUST);
        }
    }
    
    /**
     * Méthode appelée par l'interface graphique ou l'environnement, cet appel est déterminé par {@link StackTraceElement}
     * Ajoute un bijou soit à l'environnement soit à l'interface graphique dépendamment de l'appelant
     * @param r La ligne de la cellule où ajouter un bijou
     * @param c La colonne de la cellule où ajouter un bijou
     */  
    public void addJewel(int r, int c){
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements[stackTraceElements.length-2].getClassName().equals("environnement.Environnement")) {
            graph.view.addJ(c, r);
        }
        else{
            environnement.getGrid().getCell(r, c).addObject(Type.JEWEL);
        }
    }

    /**
     * Getter de l'interface graphique
     * @return L'interface graphique
     */
    public graphic.Main getGraph() {
        return graph;
    }

    /**
     * Setter de l'interface graphique
     * @param graph L'interface graphique
     */
    public void setGraph(graphic.Main graph) {
        this.graph = graph;
    }
    
    /**
     * Setter du temps inter boucle pour les générations de poussière et de bijoux
     * @param freq Le nouveau temps inter boucle
     */
    public void setFrequency(int freq){
        environnement.setSecondsToLoop(freq);
    }
    
    /**
     * Setter de la probabilité d'apparition de poussière
     * @param prob Nouvelle probabilité d'apparition de poussière
     */
    public void setDustProb(int prob){
        environnement.setPercentageDust(prob);
    }
    
    /**
     * Setter de la probabilité d'apparition de bijoux
     * @param prob Nouvelle probabilité d'apparition de bijoux
     */
    public void setJewelProb(int prob){
        environnement.setPercentageJewel(prob);
    }
    
    /**
     * Méthode appelée par le robot pour savoir si une cellule est valable
     * Cette méthode traduit les coordonnées exprimées par le robot en coordonnées réelles pour l'environnement
     * @param c La cellule souhaitée
     * @return Si la cellule est valable
     */
    public boolean isCellEnabled(Cell c) {
        Grid grid = environnement.getGrid();
        Cell cell;
        
        cell = grid.getCell(c.getRow()+initialRobotCell.getRow(), c.getCol()+initialRobotCell.getCol());
        if(cell != null)
            return cell.getEnable();
        return false;
    }

    /**
     * Getter pour le robot
     * @return Le robot
     */
    public Bender getRobot() {
        return robot;
    }

    /**
     * Setter pour le robot
     * @param robot Le robot
     */
    public void setRobot(Bender robot) {
        this.robot = robot;
    }
    
    /**
     * Méthode appelée par le robot pour mettre à jour le niveau d'énergie
     * consommé dans l'interface graphique
     * @param i Nouvelle valeur de la consommation en énergie
     */
    public void updateConso(int i){
        graph.setEnergy(Integer.toString(i));
    }
}
