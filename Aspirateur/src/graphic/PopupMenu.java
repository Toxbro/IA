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
                System.out.println("Add dust");
                c.getComponent(2).setVisible(true);
                c.getComponent(2).setBackground(Color.red);
                //c.setBackground(Color.RED);
            }
        });
        addJewel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add jewel");
                c.getComponent(0).setVisible(true);
                c.getComponent(0).setBackground(Color.yellow);
                JLabel jewel = new JLabel(new ImageIcon("ressources/jewels.jpg"));
                //JLabel jewel = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
                //c.getComponent(0).add(jewel);
                //c.setLayout(new GridBagLayout());
//                JPanel gbl = new JPanel();
//                gbl.setLayout(new GridBagLayout());
//                GridBagConstraints cJ = new GridBagConstraints();
//                cJ.gridx = 0;
//                cJ.gridy = 0;
//                GridBagConstraints c1 = new GridBagConstraints();
//                c1.gridx = 0;
//                c1.gridy = 1;
//                GridBagConstraints c2 = new GridBagConstraints();
//                c2.gridx = 0;
//                c2.gridy = 2;
//                Border border = null;
//                     border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
//                JPanel jp = new JPanel();
//                jp.setBackground(Color.GREEN);
//                jp.setBorder(border);
//                //JLabel jewel = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
//                //jewel.setBackground(Color.GREEN);
//                gbl.add(jp,cJ);
//                gbl.add(jp,c1);
//                gbl.add(jp,c2);
//                gbl.setVisible(true);
//                //c.setBackground(Color.black);
//                c.add(gbl);
//                c.setEnabled(true);
//                c.setVisible(true);
                //JLabel jewel = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
                //c.add(jewel);
                //c.addJ();
                //c.setBackground(Color.GREEN);
            }
        });
        
        add(addDust);
        add(addJewel);
    }
}
