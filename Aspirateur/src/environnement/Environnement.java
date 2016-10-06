package environnement;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 * Classe principale de l'environnement.
 * Contient les informations générales relatives à l'environnement
 * telles que la probabilité d'apparition de poussières, le temps
 * entre chaque boucle de tentative d'apparition, etc.
 * @author Thomas
 */
public class Environnement implements Runnable{

    /**
     * La probabilité d'apparition de poussière en %
     */
    private int percentageDust = 20;
    
    /**
     * La probabilité d'apparition de bijoux en %
     */
    private int percentageJewel = 10;
    
    /**
     * Le temps à attendre entre chaque boucle en seconde
     */
    private int secondsToLoop = 2;
    
    /**
     * Permet de stopper la génération de l'environnement
     */
    private Boolean run = true;
    
    /**
     * Grille de l'environnement
     * Élément principal de l'environnement
     */
    private Grid grid;
    
    /**
     * Classe principale de l'application
     * Utilisée ici comme interface avec les autres modules du programme
     */
    private Main main;
    
    /**
     * Constructeur de la classe
     * @param main Classe principale de l'application utilisée comme interface
     */
    public Environnement(Main main){
        this.main = main;
    }
    
    /**
     * Méthode principale de la classe.
     * Génère la grille puis tant que {@link #run} est vrai génère des poussières et des bijoux.
     */
    @Override
    public void run() {
        grid = new Grid();
        Cell cell;
        while (run) {
            try {
                int randDust = getRandom(0, 100);
                int randJewel = getRandom(0, 100);
                
                //Ajout poussière
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
                
                //Ajout bijou
                if (randJewel <= getPercentageJewel()) {
                    Boolean cellHasObject = true;
                    while (cellHasObject) {
                        cell = getRandomCell();
                        if (!cell.hasObject(Type.JEWEL)) {
                            cell.addObject(Type.JEWEL);
                            cellHasObject = false;
                            main.addJewel(cell.getRow(), cell.getCol());
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
    
    /**
     * Méthode générant un nombre aléatoire compris entre les bornes passées en paramètre
     * @param min Borne minimum pour la génération de l'aléa
     * @param max Borne maximum pour la génération de l'aléa
     * @return Un entier aléatoire compris entre les bornes choisies
     */
    private int getRandom(int min, int max){
        return (int) (Math.random() * (max - min));
    }
    
    /**
     * Méthode permettant de récupérer une cellule aléatoire dans la grille
     * @return Une cellule de la grille étant enable
     */
    public Cell getRandomCell(){
        Boolean cellIncorrect = true;
        Cell cell;
        int row, col;
        while (cellIncorrect) {            
            row = getRandom(0, 3);
            col = getRandom(0, 5);
            cell = getGrid().getCell(row, col);
            if ((cell != null) && (cell.getEnable())) {
                cellIncorrect = false;
                return cell;
            }
        }
        return null;
    }

    /**
     * Getter de la probabilité d'apparition de poussière
     * @return La probabilité d'apparition de poussière
     */
    public int getPercentageDust() {
        return percentageDust;
    }

    /**
     * Setter de la probabilité d'apparition de poussière
     * @param percentageDust La probabilité d'apparition de poussière
     */
    public void setPercentageDust(int percentageDust) {
        this.percentageDust = percentageDust;
    }

    /**
     * Getter de la probabilité d'apparition de bijou
     * @return La probabilité d'apparition de bijou
     */
    public int getPercentageJewel() {
        return percentageJewel;
    }

    /**
     * Setter de la probabilité d'apparition de bijou
     * @param percentageJewel La probabilité d'apparition de bijou
     */
    public void setPercentageJewel(int percentageJewel) {
        this.percentageJewel = percentageJewel;
    }

    /**
     * Getter du temps entre chaque boucle
     * @return Le temps entre chaque boucle en seconde
     */
    public int getSecondsToLoop() {
        return secondsToLoop;
    }

    /**
     * Setter du temps entre chaque boucle
     * @param secondsToLoop Le temps entre chaque boucle en seconde
     */
    public void setSecondsToLoop(int secondsToLoop) {
        this.secondsToLoop = secondsToLoop;
    }

    /**
     * Getter de la {@link #grid} de l'environnement
     * @return La {@link #grid} de l'environnement
     */
    public Grid getGrid() {
        return grid;
    }
    
}
