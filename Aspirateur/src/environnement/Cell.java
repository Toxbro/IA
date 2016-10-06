/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environnement;

import java.util.ArrayList;

/**
 * Class representing a cell of the grid
 * @author Thomas
 */
public class Cell {
    
    private Boolean enable = true;
    private int col, row;
    private ArrayList<Object> objects;
    
    public Cell (int r, int c){
        objects = new ArrayList<>();
        col = c;
        row = r;
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        System.out.println(stackTraceElements[stackTraceElements.length-2].getClassName()+" say : Cell "+this.toString()+" created and is "+getEnable());
    }
    
    public Cell (int r, int c, Boolean e){
        this(r, c);
        enable = e;
    }

    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the objects
     */
    public ArrayList<Object> getObjects() {
        return objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }
    
    public void addObject(Type t){
        this.objects.add(new Object(t));
    }
    
    public Boolean removeObject(Type t){
        for (Object o : objects){
            if (o.getType() == t)
                return this.objects.remove(o);
        }
        return false;
    }
    
    public void removeAllObjects(){
        this.objects.removeAll(objects);
    }
    
    public Boolean hasObject(Type t){
        Boolean present = false;
        for (Object o : objects){
            if (o.getType() == t)
                present = true;
        }
        return present;
    }
    
    @Override
    public String toString(){
        return "["+this.row+","+this.col+"]";
    }
}
