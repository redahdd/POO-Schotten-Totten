public class Carte {
    private Couleur couleur;
    private int valeur;

    public Carte(String couleur, int valeur) {
        this.couleur = couleur;
        this.valeur = valeur;
    }
    public enum Couleur {
        ROUGE,
        BLEU,
        VERT,
        JAUNE,
        ORANGE,
        VIOLET
    }



    public getCouleur() {
        return couleur;
    }
    public getValeur() {
        return valeur;
    }
}