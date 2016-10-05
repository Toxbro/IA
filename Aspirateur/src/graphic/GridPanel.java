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

    public int[] etat = new int[11];
    
    public GridPanel() {}
    
    public void initialize(){
        //initialisation de l'état des cellules
            for (int i=0; i<11; i++){
                etat[i]=0;
            }
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
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
    
    public void addJ(GridPanel gp, int x, int y){
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        CellPane cp = creaCell(numCell);
        if(etatCell<4){
            if(etatCell == 0){
                cp.getComponent(0).setBackground(Color.yellow);
                JLabel jewel = new JLabel(new ImageIcon("ressources/jewels.jpg"));
                etat[numCell] = 4;
            }else if(etatCell == 1){
                cp.getComponent(0).setBackground(Color.yellow);
                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 5;
            }else if(etatCell == 2){                
                cp.getComponent(0).setBackground(Color.yellow);
                cp.getComponent(1).setBackground(Color.blue);
                etat[numCell] = 6;
            }else{
                cp.getComponent(0).setBackground(Color.yellow);
                cp.getComponent(1).setBackground(Color.blue);
                cp.getComponent(2).setBackground(Color.red);
                etat[numCell] = 7;
            }
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=y;
        gbc.gridy=x;
        gp.add(cp, gbc);
        gp.setVisible(true);
        //this.setVisible(true);
    }
    
    public void delJ(GridBagLayout gbl, int x, int y){
        
    }
    
    public void addD(GridBagLayout gbl, int x, int y){
        
    }
    
    public void delD(GridBagLayout gbl, int x, int y){
        
    }
    
    public void addR(GridBagLayout gbl, int x, int y){
        
    }
    
    public void delR(GridBagLayout gbl, int x, int y){
        
    }
    
    public void mvtR(GridBagLayout gbl, int exX, int exY, int x, int y){
        
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
        cellPane.setLayout(new GridLayout(1,3));
        JPanel jpJ = new JPanel();
        JPanel jpR = new JPanel();
        JPanel jpD = new JPanel();
        jpJ.setVisible(false);
        jpR.setVisible(false);
        jpD.setVisible(false);
        cellPane.add(jpJ);
        cellPane.add(jpR);
        cellPane.add(jpD);
        Border border = new MatteBorder(0, 0, 0, 0, Color.GRAY);
        if(numCell==0){
            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
        }else if(numCell == 1){
            border = new MatteBorder(1, 0, 0, 1, Color.GRAY);
        }else if(numCell == 2){
            border = new MatteBorder(1, 0, 0, 1, Color.GRAY);
        }else if(numCell == 3){
            border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
        }else if(numCell == 4){
            border = new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 5){
            border = new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 6){
            border = new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 7){
            border = new MatteBorder(1, 0, 1, 1, Color.GRAY);
        }else if(numCell == 8){
            border = new MatteBorder(0, 1, 1, 1, Color.GRAY);
        }else if(numCell == 9){
            border = new MatteBorder(0, 0, 1, 1, Color.GRAY);
        }else{
            border = new MatteBorder(0, 0, 1, 1, Color.GRAY);
        }
        cellPane.setBorder(border);
        return cellPane;
    }
}
