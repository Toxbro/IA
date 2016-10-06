/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Thomas
 */
public class CellPane extends JPanel{
    private Color defaultBackground;
    private GridPanel gp;
    private int coordX;
    private int coordY;
        public CellPane(GridPanel gp, int x, int y) {
            this.gp = gp;
            this.coordX=x;
            this.coordY=y;
            setLayout(new GridLayout(1,3));
            JPanel jpJ = new JPanel();
            JPanel jpR = new JPanel();
            JPanel jpD = new JPanel();
            JLabel jewel = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            JLabel robot = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
            JLabel dust = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            jpJ.add(jewel);
            jpR.add(robot);
            jpD.add(dust);
            jpJ.setVisible(false);
            jpR.setVisible(false);
            jpD.setVisible(false);
            add(jpJ,0);
            add(jpR,1);
            add(jpD,2);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    defaultBackground = getBackground();
                    setBackground(Color.BLUE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(defaultBackground);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        doPop(e);
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(120, 120);
        }
        
        private void doPop(MouseEvent e){
            PopupMenu menu = new PopupMenu(this);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }

    /**
     * @return the gp
     */
    public GridPanel getGp() {
        return gp;
    }

    /**
     * @param gp the gp to set
     */
    public void setGp(GridPanel gp) {
        this.gp = gp;
    }

    /**
     * @return the coordX
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * @param coordX the coordX to set
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     * @return the coordY
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * @param coordY the coordY to set
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
        
}
