/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Classe qui représente le menu contextuel d'une cellule
 * @author Maxime
 */
public class PopupMenu extends JPopupMenu{
    /**
     * Element  du menu permettant d'ajouter de la poussière
     */
    JMenuItem addDust;
    /**
     * Element du menu permettant d'jouter un bijou
     */
    JMenuItem addJewel;
    /**
     * Constructeur de la classe qui permet d'initialiser le menu
     * ainsi que les listeneners
     * @param c = la cellule mère
     */
    public PopupMenu(CellPane c){
        addDust = new JMenuItem("Add dust");
        addJewel = new JMenuItem("Add jewel");
        addDust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adding dust by User to cell ["+c.getCoordY()+","+c.getCoordX()+"]");
                c.getGp().getMain().getMain().addDust(c.getCoordY(), c.getCoordX());
                c.getGp().addD(c.getCoordX(), c.getCoordY());
            }
        });
        addJewel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adding jewel by User to cell ["+c.getCoordY()+","+c.getCoordX()+"]");
                c.getGp().getMain().getMain().addJewel(c.getCoordY(),c.getCoordX());
                c.getGp().addJ(c.getCoordX(), c.getCoordY());
            }
        });
        
        add(addDust);
        add(addJewel);
    }
}
