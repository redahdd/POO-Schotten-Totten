package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Borne {

    private List<Carte> cartesJoueur1;
    private List<Carte> cartesJoueur2;
    private EtatBorne etat;
    private int index;
    // Pour gérer la règle de l'égalité : le premier à avoir fini la borne gagne
    private int idPremierACompleter = 0; 

    public enum EtatBorne {
        LIBRE,
        GAGNÉE_J1,
        GAGNÉE_J2
    }

    public Borne(int index) {
        this.cartesJoueur1 = new ArrayList<>();
        this.cartesJoueur2 = new ArrayList<>();
        this.etat = EtatBorne.LIBRE;
        this.index = index;
    }

    public boolean ajouterCarte(Joueur j, Carte c) {
        if (this.etat != EtatBorne.LIBRE) return false;

        if (j.getId() == 1 && cartesJoueur1.size() < 3) {
            cartesJoueur1.add(c);
            verifierPremierACompleter(1);
            return true;
        }
        if (j.getId() == 2 && cartesJoueur2.size() < 3) {
            cartesJoueur2.add(c);
            verifierPremierACompleter(2);
            return true;
        }
        return false;
    }

    private void verifierPremierACompleter(int idJoueur) {
        if (idPremierACompleter == 0) {
            if ((idJoueur == 1 && cartesJoueur1.size() == 3) || 
                (idJoueur == 2 && cartesJoueur2.size() == 3)) {
                idPremierACompleter = idJoueur;
            }
        }
    }

    public int revendiquer(Pioche pioche) {
        if (this.etat != EtatBorne.LIBRE) return 0;

        if (estComplet()) {
            return evaluerBorneComplete();
        }

        // Logique de revendication anticipée
        int forceJ1 = (cartesJoueur1.size() == 3) ? calculerForce(cartesJoueur1) : calculerForceAvecCartes(cartesJoueur1);
        int forceJ2 = (cartesJoueur2.size() == 3) ? calculerForce(cartesJoueur2) : calculerForceAvecCartes(cartesJoueur2);

        boolean j1PeutGagner = peutEncoreGagner(1, forceJ1, forceJ2, pioche);
        boolean j2PeutGagner = peutEncoreGagner(2, forceJ2, forceJ1, pioche);

        if (j1PeutGagner && !j2PeutGagner) {
            this.etat = EtatBorne.GAGNÉE_J1;
            return 1;
        } else if (j2PeutGagner && !j1PeutGagner) {
            this.etat = EtatBorne.GAGNÉE_J2;
            return 2;
        }

        return 0; 
    }

    private int evaluerBorneComplete() {
        int forceJ1 = calculerForce(cartesJoueur1);
        int forceJ2 = calculerForce(cartesJoueur2);

        if (forceJ1 > forceJ2) {
            this.etat = EtatBorne.GAGNÉE_J1;
            return 1;
        } else if (forceJ2 > forceJ1) {
            this.etat = EtatBorne.GAGNÉE_J2;
            return 2;
        } else {
            // Règle officielle : Premier à avoir complété gagne l'égalité
            this.etat = (idPremierACompleter == 1) ? EtatBorne.GAGNÉE_J1 : EtatBorne.GAGNÉE_J2;
            return idPremierACompleter;
        }
    }

    private boolean peutEncoreGagner(int joueur, int forceActuelle, int forceAdversaire, Pioche pioche) {
        List<Carte> mesCartes = (joueur == 1) ? cartesJoueur1 : cartesJoueur2;
        if (mesCartes.size() == 3) return forceActuelle > forceAdversaire;

        int forceMaxPossible = calculerForceMaximaleTheorique(mesCartes, pioche);
        return forceMaxPossible > forceAdversaire;
    }

    private int calculerForceMaximaleTheorique(List<Carte> cartes, Pioche pioche) {
        if (cartes.isEmpty()) return 524; // 500 (Suite-Coul) + 9+8+7
        if (cartes.size() == 1) return 500 + cartes.get(0).getValeur() + 17; 
        
        // Utilisation de la méthode demandée avec la pioche
        return calculerMeilleureCompletionPossible(cartes, pioche);
    }

    private int calculerMeilleureCompletionPossible(List<Carte> deuxCartes, Pioche pioche) {
        Carte c1 = deuxCartes.get(0);
        Carte c2 = deuxCartes.get(1);
        int v1 = c1.getValeur();
        int v2 = c2.getValeur();

        // 1. Test Brelan
        if (v1 == v2) {
            for (Carte.Couleur coul : Carte.Couleur.values()) {
                if (coul != c1.getCouleur() && coul != c2.getCouleur()) {
                    if (pioche.contientCarte(coul, v1)) return 400 + (v1 * 3);
                }
            }
        }

        // 2. Test Suite-Couleur
        if (c1.getCouleur() == c2.getCouleur()) {
            int min = Math.min(v1, v2);
            int max = Math.max(v1, v2);
            int diff = max - min;
            if (diff <= 2) {
                int[] cibles = (diff == 1) ? new int[]{min - 1, max + 1} : new int[]{min + 1};
                for (int v : cibles) {
                    if (v >= 1 && v <= 9 && pioche.contientCarte(c1.getCouleur(), v)) return 500 + v1 + v2 + v;
                }
            }
        }

        // 3. Par défaut : Meilleure somme simple possible avec les cartes restantes
        for (int v = 9; v >= 1; v--) {
            for (Carte.Couleur coul : Carte.Couleur.values()) {
                if (pioche.contientCarte(coul, v)) return 100 + v1 + v2 + v;
            }
        }
        return 100 + v1 + v2;
    }

    private int calculerForce(List<Carte> main) {
        List<Carte> triee = new ArrayList<>(main);
        Collections.sort(triee);
        
        boolean coul = triee.get(0).getCouleur() == triee.get(1).getCouleur() && triee.get(1).getCouleur() == triee.get(2).getCouleur();
        boolean suite = triee.get(0).getValeur() + 1 == triee.get(1).getValeur() && triee.get(1).getValeur() + 1 == triee.get(2).getValeur();
        boolean brelan = triee.get(0).getValeur() == triee.get(1).getValeur() && triee.get(1).getValeur() == triee.get(2).getValeur();
        int somme = triee.get(0).getValeur() + triee.get(1).getValeur() + triee.get(2).getValeur();

        if (coul && suite) return 500 + somme;
        if (brelan) return 400 + somme;
        if (coul) return 300 + somme;
        if (suite) return 200 + somme;
        return 100 + somme;
    }

    private int calculerForceAvecCartes(List<Carte> cartes) {
        if (cartes.isEmpty()) return 0;
        int somme = cartes.stream().mapToInt(Carte::getValeur).sum();
        if (cartes.size() == 2 && cartes.get(0).getCouleur() == cartes.get(1).getCouleur()) somme += 50;
        return somme;
    }

    public boolean estComplet() { return cartesJoueur1.size() == 3 && cartesJoueur2.size() == 3; }
    public EtatBorne getEtat() { return etat; }
    public int getIndex() { return index; }
    
    // Méthodes manquantes pour Partie.java
    public int getCartesJoueur1Size() {
        return cartesJoueur1.size();
    }
    
    public int getCartesJoueur2Size() {
        return cartesJoueur2.size();
    }
}