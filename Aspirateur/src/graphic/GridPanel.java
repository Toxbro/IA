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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 * Classe représentant la grille de l'interface graphique
 * @author Maxime
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
    /**
     * Tableau qui contient l'état des 11 cellules de la grille
     * état d'une cellule correspon à son contenu, elle peut contenir 
     * de la poussière, des bijoux et/ou le robot
     * l'état d'une cellule va de 0 à 7
     * B = Bijou, R=Robot, P=Poussière, / = vide
     * 0 = /,/,/
     * 1 = /,/,P
     * 2 = /,R,/
     * 3 = /,R,P
     * 4 = B,/,/
     * 5 = B,/,P
     * 6 = B,R,/
     * 7 = B,R,P
     */
    public int[] etat = new int[11];
    /**
     * booléan qui informe si il y'a un robot sur la grille
     * True = il y a un robot
     * False = il n'y a pas de robot
     */
    public boolean robot;
    /**
     * contient les contraintes du gridBagLayout
     */
    private GridBagConstraints gbc = new GridBagConstraints();
    /**
     * permet d'établir un lien entre le main du package graphic et cette classe
     */
    private graphic.Main main;
    /**
     * Méthode principale de la classe
     * qui initialise la grille.
     * @param main 
     */
    public void initialize(graphic.Main main){
        this.setMain(main);
            //initialisation de l'état des cellules
            for (int i=0; i<11; i++){
                //toutes initialisés à 0 car au démarrage il n'y a rien
                etat[i]=0;
            }
            
            setLayout(new GridBagLayout());

            //construction et ajout des cellules à la grille
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 5; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;
                     if(row == 0 || row == 2){
                         if(col < 3){
                             CellPane cellPane = creaCell(numCompo(col, row), col, row);
                            add(cellPane, gbc);
                         }
                     }
                     else{
                        CellPane cellPane = creaCell(numCompo(col, row), col, row);
                        add(cellPane, gbc);
                     }
                }
            }
        }
    /**
     * méthode qui permet d'ajouter un bijou en position x et y
     * @param x = la colonne où placer le bijou
     * @param y = la ligne où placer le bijou
     */
    public void addJ(int x, int y){
        //on récupère le numéro de la cellule
        int numCell = numCompo(x,y);
        //on récupère l'état de la cellule
        int etatCell = etat[numCell];
        //on récupère toutes les cellules de la grille
        Component listC[] = this.getComponents();
        //on récupère ensuite la cellule qui nous interresse
        CellPane cp = (CellPane)listC[numCell];
        //suivant l'état de la cellule on effectue le bon traite
        if(etatCell<4){
            if(etatCell == 0){
                cp.getComponent(0).setVisible(true);
                etat[numCell] = 4;
            }else if(etatCell == 1){
                cp.getComponent(0).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 5;
            }else if(etatCell == 2){  
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                etat[numCell] = 6;
            }else{
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 7;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        //on réinjecte la cellule dans la grille
        this.setComponentZOrder(cp, numCell);

    }
    /**
     * Méthode qui permet d'enlever un bijou en position x,y
     * @param x = la colonne
     * @param y = la ligne
     */
    public void delJ(int x, int y){
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
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 1;
            }else if(etatCell == 6){  
                cp.getComponent(0).setVisible(false);
                cp.getComponent(1).setVisible(true);
                etat[numCell] = 2;
            }else{
                cp.getComponent(0).setVisible(false);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 3;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    /**
     * Méthode qui ajoute de la poussière en position passé en paramètre
     * @param x = la colonne
     * @param y = la ligne
     */
    public void addD(int x, int y){
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell!=1 || etatCell!=3 || etatCell!=5 || etatCell!=7){
            if(etatCell == 0){
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 1;
            }else if(etatCell == 2){
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 3;
            }else if(etatCell == 4){ 
                cp.getComponent(0).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 5;
            }else{
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 7;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    /**
     * Méthode permettant d'enlever une poussière suivant les coordonnées
     * passées en paramètre
     * @param x = la colonne
     * @param y = la ligne
     */
    public void delD(int x, int y){
        int numCell = numCompo(x,y);
        int etatCell = etat[numCell];
        Component listC[] = this.getComponents();
        CellPane cp = (CellPane)listC[numCell];
        if(etatCell!=0 || etatCell!=2 || etatCell!=4 || etatCell!=6){
            if(etatCell == 1){
                cp.getComponent(2).setVisible(false);
                etat[numCell] = 0;
            }else if(etatCell == 3){
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(false);
                etat[numCell] = 2;
            }else if(etatCell == 5){  
                cp.getComponent(0).setVisible(true);
                cp.getComponent(2).setVisible(false);
                etat[numCell] = 4;
            }else{
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(false);
                etat[numCell] = 6;
            }
        }
        gbc.gridx=x;
        gbc.gridy=y;
        cp.setBorder(fBord(numCell));
        this.setComponentZOrder(cp, numCell);
    }
    /**
     * Méthode permettant d'ajouter le robot suivant les coordonnées
     * passées en paramètre
     * @param x = la colonne
     * @param y = la ligne
     */
    public void addR(int x, int y){
        //on vérifie que le robot n'est pas déjà sur la grille
        if(!robot){
            int numCell = numCompo(x,y);
            int etatCell = etat[numCell];
            Component listC[] = this.getComponents();
            CellPane cp = (CellPane)listC[numCell];
            if(etatCell == 0){
                cp.getComponent(1).setVisible(true);
                etat[numCell] = 2;
            }else if(etatCell == 1){
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 3;
            }else if(etatCell == 4){ 
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                etat[numCell] = 6;
            }else if(etatCell == 5){
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(true);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 7;
            }
            gbc.gridx=x;
            gbc.gridy=y;
            cp.setBorder(fBord(numCell));
            this.setComponentZOrder(cp, numCell);
            //on passe la variable afin de ne pas pouvoir ajouter un autre robot
            //sur la grille
            robot = true;
        }
    }
    
    /**
     * Méthode permettant d'enlever le robot suivant les coordonnées
     * passées en paramètre
     * @param x = la colonne
     * @param y = la ligne
     */
    public void delR(int x, int y){
        if(robot){
            int numCell = numCompo(x,y);
            int etatCell = etat[numCell];
            Component listC[] = this.getComponents();
            CellPane cp = (CellPane)listC[numCell];
            if(etatCell == 2){
                cp.getComponent(1).setVisible(false);
                etat[numCell] = 0;
            }else if(etatCell == 3){
                cp.getComponent(1).setVisible(false);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 1;
            }else if(etatCell == 6){  
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(false);
                etat[numCell] = 4;
            }else if(etatCell == 7){
                cp.getComponent(0).setVisible(true);
                cp.getComponent(1).setVisible(false);
                cp.getComponent(2).setVisible(true);
                etat[numCell] = 5;
            }
            gbc.gridx=x;
            gbc.gridy=y;
            cp.setBorder(fBord(numCell));
            this.setComponentZOrder(cp, numCell);
            robot = false;
        }
    }
    /**
     * Méthode permettant de déplacer le robot sur la grille
     * @param exX ancienne coordonnée x du robot (colonne)
     * @param exY ancienne coordonnée y du robot (ligne)
     * @param x nouvelle coordonnée x du robot (colonne)
     * @param y nouvelle coordonnée y du robot (ligne)
     */
    public void mvtR(int exX, int exY, int x, int y){
        //on vérifie que le robot existe
        if(robot){
            //on le supprime de son ancien emplacement
            delR(exX,exY);
            //on l'ajoute à son nouvel emplacement
            addR(x,y);
        }
    }
    /**
     * Méthode qui retourne le numéro de la cellule en fonction de X et Y
     * @param x = la colonne
     * @param y = la ligne
     * @return  = le numéro du cellule
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
     * Méthode pour créer une cellule
     * @param numCell
     * @return la cellule créée
     */
    public CellPane creaCell (int numCell, int x, int y){
        System.out.println("création cellule : "+numCell+" X:"+x+" Y:"+y);
        CellPane cellPane = new CellPane(this, x, y);
        //border représente la bordure de la cellule
        Border border = fBord(numCell);
        //on ajoute la bordure à la cellule
        cellPane.setBorder(border);
        //on retourne la cellule
        return cellPane;
    }
    
    /**
     * Méthode qui permet suivant la cellule crééer la bordure de cette dernière
     * @param numCell = le numéro de la cellule
     * @return retourne la bonne bordure de la cellule
     */
    public Border fBord(int numCell){
        switch (numCell) {
            case 0:
                return new MatteBorder(1, 1, 0, 1, Color.GRAY);
            case 1:
                return new MatteBorder(1, 0, 0, 1, Color.GRAY);
            case 2:
                return new MatteBorder(1, 0, 0, 1, Color.GRAY);
            case 3:
                return new MatteBorder(1, 1, 1, 1, Color.GRAY);
            case 4:
                return new MatteBorder(1, 0, 1, 1, Color.GRAY);
            case 5:
                return new MatteBorder(1, 0, 1, 1, Color.GRAY);
            case 6:
                return new MatteBorder(1, 0, 1, 1, Color.GRAY);
            case 7:
                return new MatteBorder(1, 0, 1, 1, Color.GRAY);
            case 8:
                return new MatteBorder(0, 1, 1, 1, Color.GRAY);
            case 9:
                return new MatteBorder(0, 0, 1, 1, Color.GRAY);
            case 10:
                return new MatteBorder(0, 0, 1, 1, Color.GRAY);
            default:
                return new MatteBorder(0, 0, 0, 0, Color.GRAY);
        }
    }

    /**
     * Getter du main du package graphic
     * @return the main
     */
    public graphic.Main getMain() {
        return main;
    }

    /**
     * Setter du main du package graphic
     * @param main the main to set
     */
    public void setMain(graphic.Main main) {
        this.main = main;
    }
}
