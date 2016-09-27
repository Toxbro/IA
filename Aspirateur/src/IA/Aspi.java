/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author quentin
 */
public class Aspi implements Runnable {
    
    private int x,y;
    private int conso;
    private ArrayList<Direction> pattern = new ArrayList<Direction>();
    private Master master;
    
    public Aspi(Master master, int x, int y) {
        this.master = master;
//        this.x = x;
//        this.y = y;
        this.pattern.addAll(Arrays.asList( Direction.RIGHT, Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT,
                        Direction.UP, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.LEFT,
                        Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP));
    }
    
    @Override
    public void run() {
        while(true) {
            Booolean action = false;
            if(!action) {
                if(getJewelState()) {
                    pick();
                    action = true;
                }
                if(getDustState()) {
                    suck();
                    action = true;
                }
                if(!getJewelState() && !getJewelState()) {
                    move();
                    action = true;
                }
            }
        }
    }
    
    private void move() {
        Direction dir = pattern.get(0);
        pattern.remove(0);
        pattern.add(dir);
        switch(dir){
            case DOWN: y--;
            case UP: y++;
            case LEFT: x--;
            case RIGHT: x++;
        }
        master.botMove(dir);
    }
    
    private Boolean getDustState() {
        return master.getDustState(x,y);
    }
    
    private Boolean getJewelState() {
        return master.getJewelState(x,y);
    }
    
    private void suck() {
        master.suck(x,y);
    }
    
    private void pick() {
        master.pick(x,y);
    }
}
