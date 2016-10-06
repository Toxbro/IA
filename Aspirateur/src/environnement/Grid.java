package environnement;

import java.util.ArrayList;

/**
 * Classe représentant la grille de l'environnement
 * @author Thomas
 */
public class Grid {
    
    /**
     * Liste de cellules incluses dans la grille
     */
    private ArrayList<Cell> cells;
    
    /**
     * Constructeur de la grille
     * X :    0 1 2 3 4
     * Y : 0 | | | |   
     *     1 | | | | | |
     *     2 | | | |
     * Crée toutes les cellules nécessaires et mets les cellules situées en
     * [0;3], [0;4], [2;3] et [2;4] en non valable
     */
    public Grid(){
        cells = new ArrayList<>();
        for (int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 5 ; j++){
                if(((i == 0)&&((j == 3) || (j == 4)))||
                   ((i == 2)&&((j == 3) || (j == 4)))) {
                    cells.add(new Cell(i, j, false));
                }
                else{
                    cells.add(new Cell(i, j));
                }
            }
        }
    }
    
    /**
     * Getter d'une cellule précise dans la grille
     * @param r Ligne de la cellule souhaitée
     * @param c Colonne de la cellule souhaitée
     * @return La cellule souhaitée si existante null sinon
     */
    public Cell getCell(int r, int c){
        for (Cell cell : getCells()){
            if ((cell.getRow() == r)&&(cell.getCol() == c)) {
                return cell;
            }
        }
        return null;
    }

    /**
     * Getter de toutes les cellules présentes dans la grille
     * @return Les cellules de la grille
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Setter de toutes les cellules présentes dans la grille
     * @param cells Les cellules de la grille
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}
