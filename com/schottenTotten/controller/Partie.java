package com.schottenTotten.controller;

import com.schottenTotten.model.*;
import java.util.ArrayList;

public class Partie {

    private String edition;
    private String variante;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private boolean partieTerminee;
    private Joueur vainqueur;

    protected Pioche pioche;
    private ArrayList<Borne> bornes;

    // Constructeur
    public Partie(String edition, String variante, Joueur joueur1, Joueur joueur2) {
        this.edition = edition;
        this.variante = variante;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur1; // Le joueur 1 commence
        this.partieTerminee = false;
        this.vainqueur = null;
        this.pioche = new Pioche();     
        initialiserBornes();
        distribuerCartes(); // Distribuer les cartes initiales
    }

    // Getters
    public String getEdition() { return edition; }
    public String getVariante() { return variante; }

    // Initialisation des bornes
    public void initialiserBornes() {
        bornes = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            bornes.add(new Borne(i));
        }
    }

    // Distribution des cartes initiales
    public void distribuerCartes() {
        // Dans la variante de base, chaque joueur reçoit 6 cartes
        for (int i = 0; i < 6; i++) {
            if (pioche.getNombreCartes() > 0) {
                joueur1.ajouterCarte(pioche.piocher());
            }
            if (pioche.getNombreCartes() > 0) {
                joueur2.ajouterCarte(pioche.piocher());
            }
        }
    }

    // Boucle de jeu principale
    public void lancerPartie() {
        while (!estTerminee()) {
            jouerTour();
        }
        
        // Annoncer le vainqueur
        if (vainqueur != null) {
            System.out.println("Partie terminée ! Le vainqueur est : " + vainqueur.getNom());
        } else {
            System.out.println("Partie terminée en égalité !");
        }
    }

    // Jouer un tour complet pour le joueur courant
    public String jouerTour(int indexCarte, int indexBorne) {
        if (partieTerminee) {
            return "La partie est déjà terminée";
        }

        // 1. Essayer de jouer le coup choisi
        if (!jouerCoup(indexCarte, indexBorne)) {
            return "Coup invalide ! Recommencez.";
        }

        // 2. Revendiquer des bornes automatiquement
        revendiquerBornes();

        // 3. Vérifier si la partie est terminée
        if (verifierVictoire()) {
            partieTerminee = true;
            return "Partie terminée ! " + vainqueur.getNom() + " a gagné !";
        }

        // 4. Piocher une nouvelle carte
        if (pioche.getNombreCartes() > 0) {
            joueurCourant.ajouterCarte(pioche.piocher());
        }

        // 5. Changer de joueur
        changerTour();

        return "Tour joué avec succès";
    }

    // Méthode pour jouer un coup spécifique
    private boolean jouerCoup(int indexCarte, int indexBorne) {
        if (joueurCourant.getNombreCartes() == 0 || indexCarte >= joueurCourant.getMain().size()) {
            return false;
        }
        
        if (indexBorne < 0 || indexBorne >= bornes.size()) {
            return false;
        }
        
        Carte carteAJouer = joueurCourant.getMain().get(indexCarte);
        Borne borne = bornes.get(indexBorne);
        
        // Vérifier si la borne est libre et accepte encore des cartes
        if (borne.getEtat() != Borne.EtatBorne.LIBRE) {
            return false;
        }
        
        boolean carteAjoutee = borne.ajouterCarte(joueurCourant, carteAJouer);
        if (carteAjoutee) {
            joueurCourant.retirerCarte(carteAJouer);
            return true;
        }
        
        return false;
    }

    // Version simplifiée de jouerTour pour l'IA
    public String jouerTour() {
        if (partieTerminee) {
            return "La partie est déjà terminée";
        }

        // Pour l'IA : jouer automatiquement
        if (joueurCourant.estIA()) {
            boolean coupJoue = jouerCoupIA();
            if (!coupJoue) {
                return "Impossible de jouer pour " + joueurCourant.getNom();
            }
        } else {
            return "Tour humain - utilisez jouerTour(indexCarte, indexBorne)";
        }

        // Revendiquer des bornes automatiquement
        revendiquerBornes();

        // Vérifier si la partie est terminée
        if (verifierVictoire()) {
            partieTerminee = true;
            return "Partie terminée ! " + vainqueur.getNom() + " a gagné !";
        }

        // Le joueur pioche une nouvelle carte
        if (pioche.getNombreCartes() > 0) {
            joueurCourant.ajouterCarte(pioche.piocher());
        }

        // Changer de joueur
        changerTour();

        return "Tour joué avec succès";
    }
    
    // Logique simple pour l'IA
    private boolean jouerCoupIA() {
        if (joueurCourant.getNombreCartes() == 0) {
            return false;
        }

        // IA joue la première carte sur la première borne disponible
        for (int indexBorne = 0; indexBorne < bornes.size(); indexBorne++) {
            Borne borne = bornes.get(indexBorne);
            if (borne.getEtat() == Borne.EtatBorne.LIBRE) {
                for (int indexCarte = 0; indexCarte < joueurCourant.getMain().size(); indexCarte++) {
                    if (jouerCoup(indexCarte, indexBorne)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // Méthode pour jouer une carte (à adapter selon votre interface)
    private boolean jouerCarte(Joueur joueur) {
        // Cette méthode doit être adaptée selon votre interface utilisateur
        // Pour l'instant, implémentation basique
        if (joueur.getNombreCartes() == 0) {
            return false;
        }

        // Trouver une borne disponible et jouer la première carte
        Carte carteAJouer = joueur.getMain().get(0);
        for (Borne borne : bornes) {
            if (borne.getEtat() == Borne.EtatBorne.LIBRE) {
                boolean carteAjoutee = false;
                if (joueur.getId() == 1 && borne.getCartesJoueur1Size() < 3) {
                    carteAjoutee = borne.ajouterCarte(joueur, carteAJouer);
                } else if (joueur.getId() == 2 && borne.getCartesJoueur2Size() < 3) {
                    carteAjoutee = borne.ajouterCarte(joueur, carteAJouer);
                }
                
                if (carteAjoutee) {
                    joueur.retirerCarte(carteAJouer);
                    return true;
                }
            }
        }
        return false;
    }

    // Revendiquer toutes les bornes possibles
    private void revendiquerBornes() {
        for (Borne borne : bornes) {
            if (borne.getEtat() == Borne.EtatBorne.LIBRE) {
                borne.revendiquer(pioche);
            }
        }
    }

    // Changer de joueur
    private void changerTour() {
        joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
    }

    // Vérifier si la partie est terminée
    public boolean estTerminee() {
        return partieTerminee || verifierVictoire();
    }

    // Vérifier les conditions de victoire
    private boolean verifierVictoire() {
        // Compter les bornes gagnées par chaque joueur
        int bornesJ1 = compterBornesGagnees(1);
        int bornesJ2 = compterBornesGagnees(2);

        // Victoire par 5 bornes au total
        if (bornesJ1 >= 5) {
            vainqueur = joueur1;
            return true;
        }
        if (bornesJ2 >= 5) {
            vainqueur = joueur2;
            return true;
        }

        // Victoire par 3 bornes adjacentes
        if (verifier3BornesAdjacentes(1)) {
            vainqueur = joueur1;
            return true;
        }
        if (verifier3BornesAdjacentes(2)) {
            vainqueur = joueur2;
            return true;
        }

        // Vérifier si la pioche est vide et aucun joueur ne peut plus jouer
        if (pioche.getNombreCartes() == 0 && 
            joueur1.getNombreCartes() == 0 && 
            joueur2.getNombreCartes() == 0) {
            // Déterminer le vainqueur par le nombre de bornes
            if (bornesJ1 > bornesJ2) {
                vainqueur = joueur1;
            } else if (bornesJ2 > bornesJ1) {
                vainqueur = joueur2;
            }
            // Si égalité, vainqueur reste null
            return true;
        }

        return false;
    }

    // Compter les bornes gagnées par un joueur
    private int compterBornesGagnees(int joueur) {
        int count = 0;
        for (Borne borne : bornes) {
            if ((joueur == 1 && borne.getEtat() == Borne.EtatBorne.GAGNÉE_J1) ||
                (joueur == 2 && borne.getEtat() == Borne.EtatBorne.GAGNÉE_J2)) {
                count++;
            }
        }
        return count;
    }

    // Vérifier si un joueur a 3 bornes adjacentes
    private boolean verifier3BornesAdjacentes(int joueur) {
        Borne.EtatBorne etatCherche = (joueur == 1) ? 
            Borne.EtatBorne.GAGNÉE_J1 : Borne.EtatBorne.GAGNÉE_J2;

        // Vérifier toutes les séquences de 3 bornes adjacentes (1-2-3, 2-3-4, ..., 7-8-9)
        for (int i = 0; i <= 6; i++) {
            if (bornes.get(i).getEtat() == etatCherche &&
                bornes.get(i + 1).getEtat() == etatCherche &&
                bornes.get(i + 2).getEtat() == etatCherche) {
                return true;
            }
        }
        return false;
    }

    // Getters supplémentaires
    public Joueur getJoueurCourant() { return joueurCourant; }
    public Joueur getVainqueur() { return vainqueur; }
    public ArrayList<Borne> getBornes() { return bornes; }
    public Joueur getJoueur1() { return joueur1; }
    public Joueur getJoueur2() { return joueur2; }
    public boolean isPartieTerminee() { return partieTerminee; }

    // Méthode pour obtenir l'état de la partie
    public String getEtatPartie() {
        StringBuilder etat = new StringBuilder();
        etat.append("=== ÉTAT DE LA PARTIE ===\n");
        etat.append("Joueur courant: ").append(joueurCourant.getNom()).append("\n");
        etat.append("Cartes restantes dans la pioche: ").append(pioche.getNombreCartes()).append("\n");
        
        etat.append("\nÉtat des bornes:\n");
        for (int i = 0; i < bornes.size(); i++) {
            Borne borne = bornes.get(i);
            etat.append("Borne ").append(i + 1).append(": ");
            etat.append("J1(").append(borne.getCartesJoueur1Size()).append(") ");
            etat.append("J2(").append(borne.getCartesJoueur2Size()).append(") ");
            etat.append("État: ").append(borne.getEtat()).append("\n");
        }
        
        return etat.toString();
    }
}
