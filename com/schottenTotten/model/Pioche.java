import java.util.ArrayList;

public class Pioche {

    private int nombreCartes;
    protected ArrayList<Carte> pioche;

    public Pioche() {
        pioche = new ArrayList<>();

        // Tableau des couleurs
        String[] couleurs = {"red", "green", "blue", "purple", "orange", "yellow"};

        // Boucle pour chaque couleur
        for (String couleur : couleurs) {
            // Boucle pour chaque valeur
            for (int i = 1; i <= 9; i++) {
                Carte carte = new Carte(couleur, i); // crée une carte avec couleur et valeur
                pioche.add(carte);                   // ajoute la carte à la pioche
            }
        }

        nombreCartes = pioche.size(); // met à jour le nombre de cartes
    }

    // Getter pour la pioche
    public ArrayList<Carte> getPioche() {
        return pioche;
    }

    // Exemple de méthode pour piocher une carte
    public Carte piocher() {
        if (!pioche.isEmpty()) {
            return pioche.remove(0); // retire la première carte et la retourne
        }
        return null; // si pioche vide
    }

    public int getNombreCartes() {
        return pioche.size();
    }
}
