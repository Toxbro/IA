package environnement;

import java.util.ArrayList;

/**
 * Classe représentant une cellule de la {@link #environnement.Grid}
 * @author Thomas
 */
public class Cell {
    
    /**
     * Si la cellule est valable :
     * X :    0 1 2 3 4
     * Y:  0 | | | |   
     *     1 | | | | | |
     *     2 | | | |
     * Les cellules situées en [0;3], [0;4], [2;3] et [2;4] ne sont pas valable
     */
    private Boolean enable = true;
    
    /**
     * La colonne de la cellule
     */
    private int col;
    
    /**
     * La ligne de la cellule
     */
    private int row;
    
    /**
     * Liste des objets contenus dans la cellule
     */
    private ArrayList<Object> objects;
    
    /**
     * Constructeur de la classe prenant en paramètre la ligne et la colonne de la cellule
     * @param r Ligne de la cellule
     * @param c Colonne de la cellule
     */
    public Cell (int r, int c){
        objects = new ArrayList<>();
        col = c;
        row = r;
    }
    
    /**
     * Surcharge du constructeur prenant en compte si la cellule est valable
     * @param r Ligne de la cellule
     * @param c Colonne de la cellule
     * @param e Si la cellule est valable
     */
    public Cell (int r, int c, Boolean e){
        this(r, c);
        enable = e;
    }

    /**
     * Getter de la validité de la cellule
     * @return Si la cellule est valable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * Setter de la validité de la cellule
     * @param enable Si la cellule est valable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * Getter de la colonne de la cellule
     * @return La colonne de la cellule
     */
    public int getCol() {
        return col;
    }

    /**
     * Setter de la colonne de la cellule
     * @param col Colonne de la cellule
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Getter de la ligne de la cellule
     * @return La ligne de la cellule
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter de la ligne de la cellule
     * @param row La ligne de la cellule
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter de la liste des objets présents dans la cellule
     * @return La liste des objets présents dans la cellule
     */
    public ArrayList<Object> getObjects() {
        return objects;
    }

    /**
     * Setter de la liste des objets présents dans la cellule
     * @param objects La liste des objets présents dans la cellule
     */
    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }
    
    /**
     * Méthode permettant de rajouter un objet d'un certain {@link #Type} à la cellule
     * @param t Le type de l'objet à ajouter
     */
    public void addObject(Type t){
        this.objects.add(new Object(t));
    }
    
    /**
     * Méthode permettant de supprimer un objet d'un certain {@link #Type} de la cellule
     * @param t Le type de l'objet à supprimer
     * @return L'objet supprimé
     */
    public Boolean removeObject(Type t){
        for (Object o : objects){
            if (o.getType() == t)
                return this.objects.remove(o);
        }
        return false;
    }
    
    /**
     * Méthode permettant de supprimer tous les objets de la cellule
     */
    public void removeAllObjects(){
        this.objects.removeAll(objects);
    }
    
    /**
     * Méthode permettant de tester si la cellule possède un objet du type passé en paramètre
     * @param t Le type de l'objet à tester
     * @return Si le type d'objet est présent dans la cellule
     */
    public Boolean hasObject(Type t){
        Boolean present = false;
        for (Object o : objects){
            if (o.getType() == t)
                present = true;
        }
        return present;
    }
    
    /**
     * Méthode utilisée pour identifier la cellule sous forme de chaine de caractères
     * @return La chaine de caractère identifiant la cellule sour la forme [ligne, colonne]
     */
    @Override
    public String toString(){
        return "["+this.row+","+this.col+"]";
    }
}
