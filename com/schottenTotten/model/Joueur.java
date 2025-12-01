import java.util.ArrayList;

public class Joueur {

    private String nom;
    protected ArrayList<Carte> main;

    // Constructeur
    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();  // initialise la main vide
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }

    // Méthodes pour gérer la main
    public void ajouterCarte(Carte carte) {
        main.add(carte);
    }

    public void retirerCarte(Carte carte) {
        main.remove(carte);
    }

    public ArrayList<Carte> getMain() {
        return main;
    }
    public int getNombreCartes() {
        return main.size();
    }
}
