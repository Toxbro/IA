/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author Thomas
 */
public class PopupMenu extends JPopupMenu{
    JMenuItem addDust;
    JMenuItem addJewel;
    public PopupMenu(Component c){
        addDust = new JMenuItem("Add dust");
        addJewel = new JMenuItem("Add jewel");
        addDust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add dust");
                c.setBackground(Color.RED);
                System.out.println();
            }
        });
        addJewel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add jewel");
                c.setBackground(Color.GREEN);
            }
        });
        
        add(addDust);
        add(addJewel);
    }
}
