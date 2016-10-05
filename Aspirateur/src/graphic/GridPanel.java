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
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Thomas
 */
public class GridPanel extends JPanel{
//Grille
//  X : 0 1 2  3 4
//Y: 0 |0|1|2|   
//   1 |3|4|5|6|7|
//   2 |8|9|10|
//    
//    (x,y)=numCase
//    (1,0) = 1
//    (0,1) = 3
//    (2,2) = 10
    public int[] etat = new int[11];
    public boolean robot;
    private GridBagConstraints gbc = new GridBagConstraints();
    public GridPanel() {}
    
    public void initialize(){
        //initialisation de l'état des cellules
            for (int i=0; i<11; i++){
                etat[i]=0;
            }
            setLayout(new GridBagLayout());

            //GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 5; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane cellPane = creaCell(numCompo(col, row));
                     if(row == 0 || row == 2){
                         if(col < 3){
                            add(cellPane, gbc);
                         }
                     }
                     else{
                        add(cellPane, gbc);
                     }
                }
            }
        }
    
    public void addJ(int x, int y){
        JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
        JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell<4){
            if(etatCell == 0){
                cp.add(jewelI,0);
                cp.getComponent(0).setVisible(true);
                etat[numCell] = 4;
            }else if(etatCell == 1){
                cp.add(jewelI,0);
                cp.add(dustI,2);
                cp.getComponent(0).setVisible(true);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 5;
            }else if(etatCell == 2){  
                cp.add(jewelI,0);
                cp.add(robotI,1);
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 6;
            }else{
                cp.add(jewelI,0);
                cp.add(robotI,1);
                cp.add(dustI,2);
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 7;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);

    }
    
    public void delJ(int x, int y){
        JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
        JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell>=4){
            if(etatCell == 4){
                cp.getComponent(0).setVisible(false);
                etat[numCell] = 0;
            }else if(etatCell == 5){
                cp.getComponent(0).setVisible(false);
                cp.add(dustI,2);
//                cp.getComponent(0).setVisible(false);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 1;
            }else if(etatCell == 6){  
                cp.getComponent(0).setVisible(false);
                cp.add(robotI,1);
                cp.getComponent(1).setVisible(true);
//                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 2;
            }else{
                cp.getComponent(0).setVisible(false);
                cp.add(robotI,1);
                cp.add(dustI,2);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 3;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    
    public void addD(int x, int y){
        JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
        JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell!=1 || etatCell!=3 || etatCell!=5 || etatCell!=7){
            if(etatCell == 0){
                cp.add(dustI,2);
//                cp.getComponent(2).setVisible(true);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 1;
            }else if(etatCell == 2){
                cp.add(robotI,1);
                cp.add(dustI,2);
//                cp.getComponent(1).setVisible(true);
//                cp.getComponent(2).setVisible(true);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 3;
            }else if(etatCell == 4){ 
                cp.add(jewelI,0);
                cp.add(dustI,2);
//                cp.getComponent(0).setVisible(true);
//                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 5;
            }else{
                cp.add(jewelI,0);
                cp.add(robotI,1);
                cp.add(dustI,2);
//                cp.getComponent(0).setVisible(true);
//                cp.getComponent(1).setVisible(true);
//                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 7;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    
    public void delD(int x, int y){
        JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
        JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell!=0 || etatCell!=2 || etatCell!=4 || etatCell!=6){
            if(etatCell == 1){
                cp.getComponent(2).setVisible(false);
                etat[numCell] = 0;
            }else if(etatCell == 3){
//                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(false);
                cp.add(robotI,1);
//                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 2;
            }else if(etatCell == 5){  
//                cp.getComponent(0).setVisible(true);
                cp.getComponent(2).setVisible(false);
                cp.add(jewelI,0);
//                cp.getComponent(0).setBackground(Color.yellow);
                etat[numCell] = 4;
            }else{
//                cp.getComponent(0).setVisible(true);
//                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(false);
                cp.add(jewelI,0);
                cp.add(dustI,1);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 6;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    
    public void addR(int x, int y){
        if(!robot){
            JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
            JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            int numCell = numCompo(x,y);
            int etatCell = etat[numCell];
            Component listC[] = this.getComponents();
            CellPane cp = (CellPane)listC[numCell];
            if(etatCell == 0){
                cp.add(robotI,1);
//                cp.getComponent(1).setVisible(true);
                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 2;
            }else if(etatCell == 1){
                cp.add(robotI,1);
                cp.add(dustI,2);
//                cp.getComponent(1).setVisible(true);
//                cp.getComponent(2).setVisible(true);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 3;
            }else if(etatCell == 4){ 
                cp.add(jewelI,0);
                cp.add(robotI,1);
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 6;
            }else if(etatCell == 5){
                cp.add(jewelI,0);
                cp.add(robotI,1);
                cp.add(dustI,2);
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(1).setBackground(Color.blue);
//                cp.getComponent(2).setBackground(Color.red);
                
//                cp.add(jewelI,cp.getComponent(0));
//                cp.add(robotI,cp.getComponent(1));
//                cp.add(dustI,cp.getComponent(2));
                etat[numCell] = 7;
            }
            gbc.gridx=x;
            gbc.gridy=y;
            cp.setBorder(fBord(numCell));
            this.setComponentZOrder(cp, numCell);
            robot = true;
        }
    }
    
    public void delR(int x, int y){
        if(robot){
            JLabel jewelI = new JLabel(new ImageIcon(((new ImageIcon("ressources/jewels.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            JLabel robotI = new JLabel(new ImageIcon(((new ImageIcon("ressources/aspi.png")).getImage()).getScaledInstance(10, 35, java.awt.Image.SCALE_SMOOTH)));
            JLabel dustI = new JLabel(new ImageIcon(((new ImageIcon("ressources/dust.png")).getImage()).getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            int numCell = numCompo(x,y);
            int etatCell = etat[numCell];
            Component listC[] = this.getComponents();
            CellPane cp = (CellPane)listC[numCell];
            if(etatCell == 2){
                cp.getComponent(1).setVisible(false);
                etat[numCell] = 0;
            }else if(etatCell == 3){
                cp.getComponent(1).setVisible(false);
                cp.add(robotI,2);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 1;
            }else if(etatCell == 6){  
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(false);
                cp.add(jewelI,0);
//                cp.getComponent(0).setBackground(Color.yellow);
                etat[numCell] = 4;
            }else if(etatCell == 7){
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(false);
                cp.add(jewelI,0);
                cp.add(dustI,2);
                cp.getComponent(2).setVisible(true);
//                cp.getComponent(0).setBackground(Color.yellow);
//                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 5;
            }
            gbc.gridx=x;
            gbc.gridy=y;
            cp.setBorder(fBord(numCell));
            this.setComponentZOrder(cp, numCell);
            robot = false;
        }
    }
    
    public void mvtR(int exX, int exY, int x, int y){
        if(robot){
            delR(exX,exY);
            addR(x,y);
        }
    }
    /**
     * Fonction qui retourne le numéro de la CellPane en fonction de X et Y
     * @param x = la colonne
     * @param y = la ligne
     * @return  = le numéro du CellPane
     */
    public int numCompo(int x, int y){
       
        if(y==0){
            if(x==0){
                return 0;
            }else if(x==1){
                return 1;
            }else{
                return 2;
            }
        }else if(y==1){
            if(x==0){
                return 3;
            }else if(x==1){
                return 4;
            }else if(x==2){
                return 5;
            }else if(x==3){
                return 6;
            }else{
                return 7;
            }
        }else{
            if(x==0){
                return 8;
            }else if(x==1){
                return 9;
            }else{
                return 10;
            }
        }
    }
    /**
     * Fonction pour créer une cellule
     * @param numCell
     * @return 
     */
    public CellPane creaCell (int numCell){
        CellPane cellPane = new CellPane();
        Border border = new MatteBorder(0, 0, 0, 0, Color.GRAY);
        border = fBord(numCell);
        cellPane.setBorder(border);
        return cellPane;
    }
    
    public Border fBord(int numCell){
        if(numCell==0){
            return new MatteBorder(1, 1, 0, 1, Color.GRAY);
        }else if(numCell == 1){
            return new MatteBorder(1, 0, 0, 1, Color.GRAY);
        }else if(numCell == 2){
            return new MatteBorder(1, 0, 0, 1, Color.GRAY);
        }else if(numCell == 3){
            return new MatteBorder(1, 1, 1, 1, Color.GRAY);
        }else if(numCell == 4){
            return new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 5){
            return new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 6){
            return new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 7){
            return new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 8){
            return new MatteBorder(0, 1, 1, 1, Color.GRAY);
        }else if(numCell == 9){
            return new MatteBorder(0, 0, 1, 1, Color.GRAY);
        }else{
            return new MatteBorder(0, 0, 1, 1, Color.GRAY);
        }
    }
}
