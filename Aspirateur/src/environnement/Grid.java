/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environnement;

import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
public class Grid {
    
    private ArrayList<Cell> cells;
    
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
    
    public Cell getCell(int r, int c){
        for (Cell cell : getCells()){
            if ((cell.getRow() == r)&&(cell.getCol() == c)) {
                return cell;
            }
        }
        return null;
    }

    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}
