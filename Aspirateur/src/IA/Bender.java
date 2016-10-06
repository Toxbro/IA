package IA;

import java.util.Map;
import environnement.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 * Classe principale du robot.
 * Possède un état mental BDI
 * @author quentin
 */

public class Bender implements Runnable {
    
    /**
     * La cellule actuelle du robot;
     */
    private Cell currentCell;
    
    /**
     * La consommation totale du robot;
     */
    private int conso;
    
    /**
     * La grille des cellules connues par le robot, associées avec le nombre de tour depuis le dernier passage du robot sur cette celulle.
     */
    private HashMap<Cell, Integer> grid = new HashMap<Cell, Integer>();
    
    /**
     * La classe Main, qui a lancé le thread (permet l'accès aux méthodes de mise à jour de l'environnement).
     */
    private Main master;
    
    /**
     * Indique si la phase d'exploration de l'environnement par le robot est terminé ou non.
     */
    private boolean isExploDone;
    
    /**
     * La succession de direction des déplacements afin d'atteindre le but actuel.
     */
    private ArrayList<Direction> path = new ArrayList<Direction>();
    
    /**
     * Le nombre de cellules total dans la grille. Permet de savoir si l'exploration est terminée.
     */
    private int cellTotal;
    
    /**
     *  Le constructeur de l'objet Aspirateur.
     *  En plus d'initialiser les attributs "master", "isExploDone" et "cellTotal", crée la cellule de départ et l'ajoute à la grille.
     * 
     * @param master        Référence vers le maître qui instancie tous les threads. Nécessaire afin de mettre à jour l'environnement en fonction des actions du robot.
     * @param cellTotal     Nombre de cellules total dans la grille. Permet au robot de savoir quand terminer la phase d'exploration.
     */
    public Bender(Main master, int cellTotal) {
        this.master = master;
        this.currentCell = new Cell(0, 0, true);
        this.grid.put(currentCell, 0);
        this.isExploDone = false;
        this.cellTotal = cellTotal;
    }
    
    /**
     *  La méthode run() du thread Aspirateur.
     *  La routine du robot met à jour ses Beliefs, puis appelle la fonction getDesire() afin d'obtenir la prochaine tâche à effectuer, puis effectuer cette tâche.
     */
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
    
    /**
     * L'algorithme d'exploration du robot.
     * Identifie les cellules adjacentes, puis ajoute les cellules inconnues ainsi découvertes dans la grille.
     * Enfin, choisit une cellule inconnue adjacente aléatoirement afin d'effectuer le déplacement suivant.
     * Si toutes les cellules adjacentes sont connues, choisit une cellule aléatoirement.
     */
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
            if(unknown.size() == 0){
                move(getDir(currentCell, adjacent.get((int) (Math.random()*adjacent.size()))));
            }
            else
                move(getDir(currentCell, unknown.get((int) (Math.random() * unknown.size()))));
    }
    
    /**
     * Met à jour les Beliefs du robot.
     * A chaque itération de la routine, ajoute 1 au délai de chaque cellule.
     * De plus, si l'exploration est terminée, réinitialise le délai pour la cellule actuelle du robot.
     */
    private void updateBelief() {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            entry.setValue(entry.getValue()+1);
        }
        if(isExploDone){
            grid.put(currentCell, 0);
        }
    }
    
    /**
     * Définit le Desire du robot en fonction de l'état de son environnement.
     * Si l'exploration n'est pas terminée, il la termine.
     * S'il n'a pas de but défini, définit un but.
     * Si un bijou est présent sur la cellule, prendre le bijou.
     * Si de la poussière est présente, aspirer la poussière.
     * Sinon, se déplacer vers l'objectif.
     * 
     * @return Le désir choisi.
     */
    private Desire getDesire(){
        if(!isExploDone)
            return Desire.EXPLORE;
        else if(path.isEmpty())
            return Desire.GETNEWGOAL;
        else if(getJewelState())
            return Desire.PICK;
        else if(getDustState())
            return Desire.CLEAN;
        else{
            return Desire.MOVETOGOAL;
        }
    }
    
    /**
     * Définit la cellule-objectif actuelle.
     * Cet objectif est définit comme étant la cellule où la probabilité d'apparition de la poussière est la plus élevée.
     * Cette probabilité est spécifiée par le nombre d'itérations effectuées depuis le dernier passage du robot sur cette cellule.
     * 
     * @return La cellule potentiellement la plus sale.
     */
    private Cell getGoal(){
        int max = 0;
        Cell dirtiest = null;
        
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()){
            if(entry.getValue() > max) {
                max = entry.getValue();
                dirtiest = entry.getKey();
            }
        }
        System.out.println("Goal : "+dirtiest+" "+max); 
        return dirtiest;
    }
    
    /**
     * Crée le chemin vers l'objectif.
     * Ce chemin est défini par une succession de direction que le robot devra prendre afin d'arriver à la cellule-objectif.
     * Le chemin est choisi parmis plusieurs autres chemins, de même longueur, comme étant le plus potentiellement sale.
     * Ce niveau de saleté est défini comme étant la somme de la probabilité de saleté de chacune des cellules qui le composent.
     * 
     * @param start La cellule initiale.
     * @param end   La cellule-objectif.
     * @return Le chemin le plus sale vers l'objectif.
     */
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
        
        for(int i=0; i<path.size()-1; i++) {
            result.add(getDir(path.get(i), path.get(i+1)));
        }
        
        return result;
    }
    
    /**
     * Crée la liste des chemins potentiels vers l'objectf.
     * Ces chemins sont crées de manière récursive, en sélectionnant les cellules adjacentes à la cellule "start" nous rapprochant de l'objectif,
     * puis en appelant getPath depuis ces cellules.
     * On obtient ainsi un arbre de chemin menant à l'objectif.
     * 
     * @param start La cellule initiale.
     * @param end   La cellule-objectif.
     * @return La liste des chemins vers l'objectif.
     */
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
    
    /**
     * Renvoit la liste des cellules adjacentes à la cellule "start" les plus proches de l'objectif.
     * Cette méthode récupère la liste des cellules adjacentes à la cellule start, 
     * puis calcule la distance de chacune de ces cellules à l'objectif afin de prendre la ou les plus proches.
     * 
     * @param start La cellule initiale.
     * @param end   La cellule-objectif.
     * @return La liste des cellules adjacentes à "start" les plus proches de "end".
     */
    private ArrayList<Cell> getClosestAdj(Cell start, Cell end){
        ArrayList<Cell> result = new ArrayList<Cell>();
        ArrayList<Cell> adjacent;
        HashMap<Cell, Integer> closeAdj= new HashMap<Cell, Integer>();
        int dist = Integer.MAX_VALUE;
        
        adjacent = getAdj(start);
        
        for(Cell c : adjacent){
            closeAdj.put(c, Math.abs(end.getCol() - c.getCol()) + Math.abs(end.getRow() - c.getRow()));
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
    
    /**
     * Sélectionne la liste des cellules adjacentes à la cellule "c".
     * Cette méthode appelle, pour chacune des 4 directions, la méthode {@link getAdj(Cell c, Direction dir)},
     * puis trie celles qui sont "enabled", c'est-à-dire celles où le robot peut aller.
     * 
     * @param c La cellule dont on souhaite connaître les cellules adjacentes.
     * @return La liste des cellules adjacentes à "c".
     */
    private ArrayList<Cell> getAdj(Cell c){
        ArrayList<Cell> result = new ArrayList<Cell>();
        
        for(Direction d : Direction.values()){
            Cell cell = getCell(c, d);
            if(cell != null && cell.getEnable())
                result.add(cell);
        }
        return result;
    } 
    
    /**
     * Renvoit ou crée la cellule possédant les coordonnées passées en paramètres.
     * Si le robot ne possède pas de cellule possédant ces coordonnées dans sa grille, et que le robot est en phase d'exploration,
     * crée la cellule, et demande au master si elle est "enabled" ou non.
     * Si le robot n'est pas en phase d'exploration, retourne "null".
     * 
     * @param x La colonne de la cellule (commence à gauche).
     * @param y La ligne de la cellule (commence en haut).
     * @return La cellule si elle se trouve dans la grid, une nouvelle cellule si le robot ne la connaît pas et se trouve en mode exploration, null sinon.
     */   
    private Cell getCell(int x, int y) {
        for(Map.Entry<Cell, Integer> entry : grid.entrySet()) {
            if(entry.getKey().getCol() == x && entry.getKey().getRow() == y)
                return entry.getKey();
        }
        if(!isExploDone) {
            Cell c = new Cell(y,x);
            c.setEnable(master.isCellEnabled(c));
            return c;
        }
        return null;
    }
    
    /**
     * Renvoit la cellule se trouvant dans la direction demandée par rapport à la cellule spécifiée.
     * Appelle la méthode {@link getCell(x,y)} en modifiant x et y afin de correspondre à la direction désirée.
     * 
     * @param c     La cellule initiale.
     * @param dir   La direction de la cellule recherchée par rapport à la cellule initiale.
     * @return      La cellule recherchée.
     */
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
    
    /**
     * Renvoit la direction de la cellule "end" par rapport à la cellule "start".
     * Effectue la manipulation inverse à {@link getCell(c, dir)}.
     * 
     * @param start La cellule initiale.
     * @param end   La cellule cible.
     * @return      La direction de la cellule cible par rapport à la cellule initiale.
     */
    private Direction getDir(Cell start, Cell end) {
        System.out.println("");
        if(end.getCol() == start.getCol() && end.getRow() == start.getRow()+1)
            return Direction.DOWN;
        else if(end.getCol() == start.getCol() && end.getRow() == start.getRow()-1)
            return Direction.UP;
        else if(end.getCol() == start.getCol()+1 && end.getRow() == start.getRow())
            return Direction.RIGHT;
        else if(end.getCol() == start.getCol()-1 && end.getRow() == start.getRow())
            return Direction.LEFT;
        else {
            return null;
        }
    }
    
    /**
     * Met à jour la consommation du robot, et avertit le master de l'état actuelle de la consommation.
     */
    private void updateConso(){
        conso++;
        master.updateConso(conso);
    }
    
    /**
     * Déplace le robot dans la direction désirée.
     * Avertit le master de la direction du mouvement, et met à jour l'emplacement du robot.
     * 
     * @param dir La direction du déplacement.
     */
    private void move(Direction dir) {
        updateConso();
        master.botMove(dir);
        currentCell = getCell(currentCell, dir);        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Demande au master l'état de la poussière sur la cellule actuelle du robot.
     * 
     * @return Un boolean indiquant la présence ou l'absence de poussière.
     */
    private boolean getDustState() {
        return master.getDustState();
    }
    
    /**
     * Demande au master l'état des bijous sur la cellule actuelle du robot.
     * 
     * @return Un boolean indiquant la présence ou l'absence de bijou.
     */
    private boolean getJewelState() {
        return master.getJewelState();
    }
    
    /**
     * Aspire la poussière et les bijous sur la case actuelle.
     * Avertit le master que le robot a aspiré la poussière sur la case, met à jour la consommation.
     */
    private void suck() {
        updateConso();
        master.suck();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ramasse les bijous sur la case actuelle.
     * Avertit le master que le robot a récupéré le bijou sur la case, met à jour la consommation.
     */
    private void pick() {
        updateConso();
        master.pick();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}