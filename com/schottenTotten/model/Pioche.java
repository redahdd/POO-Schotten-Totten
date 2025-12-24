package com.schottenTotten.model;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Pioche {

    private int nombreCartes;
    protected ArrayList<Carte> pioche;

    // Constructeur par défaut (variante de base)
    public Pioche() {
        this(false); // Pas de cartes tactiques par défaut
    }
    
    // Constructeur avec choix des cartes tactiques
    public Pioche(boolean avecCartesTactiques) {
        pioche = new ArrayList<>();
        
        // Ajouter les cartes de base (cartes clan)
        ajouterCartesBase();
        
        // Ajouter les cartes tactiques si demandé
        if (avecCartesTactiques) {
            ajouterCartesTactiques();
        }
        
        nombreCartes = pioche.size();
        Collections.shuffle(pioche);  // Mélange aléatoire des cartes
    }
    
    /**
     * Ajoute les cartes de base (cartes clan) à la pioche
     */
    private void ajouterCartesBase() {
        // Boucle pour chaque couleur de l'enum Couleur défini dans Carte
        for (Carte.Couleur couleur : Carte.Couleur.values()) {
            // Boucle pour chaque valeur
            for (int i = 1; i <= 9; i++) {
                Carte carte = new Carte(couleur, i); // crée une carte avec couleur et valeur
                pioche.add(carte);           // ajoute la carte à la pioche
            }
        }
    }
    
    /**
     * Ajoute les cartes tactiques à la pioche
     */
    private void ajouterCartesTactiques() {
        // Ajouter toutes les cartes tactiques disponibles
        for (CarteTactique.TypeCarteTactique type : CarteTactique.TypeCarteTactique.values()) {
            CarteTactique carte = new CarteTactique(type);
            pioche.add(carte);
        }
    }
    
    // Getter pour la pioche
    public ArrayList<Carte> getPioche() {
        return pioche;
    }

    // Méthode pour piocher une carte
    public Carte piocher() {
        if (!pioche.isEmpty()) {
            return pioche.remove(0); // retire la première carte et la retourne
        }
        return null; // si pioche vide
    }

    public int getNombreCartes() {
        return pioche.size();
    }

    /**
     * Vérifie si une carte spécifique est encore présente dans la pioche.
     * Utile pour la revendication anticipée des bornes.
     */
    public boolean contientCarte(Carte.Couleur couleur, int valeur) {
        for (Carte carte : pioche) {
            if (carte.getCouleur() == couleur && carte.getValeur() == valeur) {
                return true;
            }
        }
        return false;
    }
}