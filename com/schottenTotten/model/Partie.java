import java.util.ArrayList;

public class Partie {

    private String edition;
    private String variante;
    private Joueur joueur1;
    private Joueur joueur2;

    protected Pioche pioche;
    private ArrayList<Borne> bornes;

    // Constructeur
    public Partie(String edition, String variante, Joueur joueur1, Joueur joueur2) {
        this.edition = edition;
        this.variante = variante;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.pioche = new Pioche();      
        initialiserBornes();             
    }

    // Getters
    public String getEdition() { return edition; }
    public String getVariante() { return variante; }

    // Initialisation des bornes
    public void initialiserBornes() {
        bornes = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            bornes.add(new Borne("Borne n°" + i));
        }
    }

    // Distribution des cartes
    public void distribuerCartes() {
        // Le contenu viendra après, mais on n'écrase plus la pioche ici
        // On pioche simplement depuis la pioche existante
    }

    // Jouer un tour (signature corrigée)
    public String jouerTour(Joueur j) {
        // Contenu à implémenter plus tard
        return null;
    }
}
