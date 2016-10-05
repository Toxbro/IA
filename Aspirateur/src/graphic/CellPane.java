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
    
        public CellPane() {
            setLayout(new GridLayout(1,3));
            JPanel jpJ = new JPanel();
            JPanel jpR = new JPanel();
            JPanel jpD = new JPanel();
            jpJ.setVisible(false);
            jpR.setVisible(false);
            jpD.setVisible(false);
            add(jpJ);
            add(jpR);
            add(jpD);
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
            return new Dimension(50, 50);
        }
        
        private void doPop(MouseEvent e){
            PopupMenu menu = new PopupMenu(this);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
        
}
