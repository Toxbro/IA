package environnement;

/**
 * Classe représentant un objet présent sur une cellule
 * Cette classe a été implementée dans l'optique d'ajouter des contraintes sur ces objets telles que le poids
 * Cette fonctionnalité n'a pas encore été développée.
 * @author Thomas
 */
public class Object {
    
    /**
     * Type de l'objet ({@link #Type)
     */
    private Type type;
    
    /**
     * Constructeur de la classe
     * @param t Type de l'objet
     */
    public Object (Type t){
        this.type = t;
    }

    /**
     * Getter du type de l'objet
     * @return Le type de l'objet
     */
    public Type getType() {
        return type;
    }

    /**
     * Setter du type de l'objet
     * @param type Le type de l'objet
     */
    public void setType(Type type) {
        this.type = type;
    }
}
