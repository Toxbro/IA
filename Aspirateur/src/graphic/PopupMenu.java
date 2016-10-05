/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Thomas
 */
public class PopupMenu extends JPopupMenu{
    JMenuItem addDust;
    JMenuItem addJewel;
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
