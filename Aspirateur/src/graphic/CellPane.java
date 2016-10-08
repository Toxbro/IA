/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Classe représant une cellule de la grille
 * @author Maxime
 */
public class CellPane extends JPanel{
    /**
     * varible qui contient l'arrière plan par défaut
     */
    private Color defaultBackground;
    /**
     * grille de l'interface graphique
     * permet à la cellule d'interagir avec sa grille mère et 
     * les elements au-dessus qui sont connectés
    */
    private GridPanel gp;
    /**
     * coordonnée X de la cellule (la colone)
     */
    private int coordX;
    /**
     * coordonnée Y de la cellule (la ligne)
     */
    private int coordY;
        /**
         * constructeur de la classe prenant en paramètre sa grille mère, et ses
         * coordonnées x et y
         * @param gp = grille mère
         * @param x = colonne de la cellule
         * @param y = ligne de la cellule
         */
        public CellPane(GridPanel gp, int x, int y) {
            this.gp = gp;
            this.coordX=x;
            this.coordY=y;
            setLayout(new GridLayout(1,3));
            //création de 3 panels qui nous permettrons par la suite d'affiche 
            //respectivement le bijou, le robot et la poussière
            JPanel jpJ = new JPanel();
            JPanel jpR = new JPanel();
            JPanel jpD = new JPanel();
            //création des images
            JLabel jewel = new JLabel(new ImageIcon(((new ImageIcon("../ressources/jewels.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
            JLabel robot = new JLabel(new ImageIcon(((new ImageIcon("../ressources/aspi.png")).getImage()).getScaledInstance(40, 110, java.awt.Image.SCALE_SMOOTH)));
            JLabel dust = new JLabel(new ImageIcon(((new ImageIcon("../ressources/dust.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
            jpJ.setOpaque(false);
            jpR.setOpaque(false);
            jpD.setOpaque(false);
            //ajout des images dans les conteneurs
            jpJ.add(jewel);
            jpR.add(robot);
            jpD.add(dust);
            //les images sont présentes mais ne sont visibles
            jpJ.setVisible(false);
            jpR.setVisible(false);
            jpD.setVisible(false);
            //ajout des conteneurs dans la cellule
            add(jpJ,0);
            add(jpR,1);
            add(jpD,2);
            //ajout de listener sur la souris
            //pour l'interaction humain-machine
            addMouseListener(new MouseAdapter() {
                /**
                 * Méthode qui permet de modifier le background
                 * lorsque la souris de l'utilisateur entre dans 
                 * la cellule
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    defaultBackground = getBackground();
                    setBackground(Color.BLUE);
                }
                /**
                 * on remet le fond de la cellule par défaut
                 * lorsque l'utilisateur sort de la cellule
                 * @param e 
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(defaultBackground);
                }
                /**
                 * on affiche un menu contextuel lorsque l'utilisateur
                 * clique droit sur la cellule
                 * @param e 
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        doPop(e);
                    }
                }
            });
        }
        /**
         * taille de la cellule
         * @return la dimension
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(120, 120);
        }
        /**
         * Méthode qui affiche le menu contextuel de la cellule
         * @param e 
         */
        private void doPop(MouseEvent e){
            //on passe ne paramètre la cellule
            PopupMenu menu = new PopupMenu(this);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }

    /**
     * Getter de la grille mère
     * @return the gp
     */
    public GridPanel getGp() {
        return gp;
    }

    /**
     * Setter de la grille mère
     * @param gp the gp to set
     */
    public void setGp(GridPanel gp) {
        this.gp = gp;
    }

    /**
     * Getter qui retourne la coordonnée X de la cellule
     * @return the coordX (la colonne)
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * Setter qui modifie la coordonnée X de la cellule
     * @param coordX the coordX to set (la colonne)
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     * Getter qui retourne la coordonnée Y de la cellule
     * @return the coordY (la ligne)
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * Setter qui permet de modifier la coordonnée Y de la cellule
     * @param coordY the coordY to set (la ligne)
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
        
}
