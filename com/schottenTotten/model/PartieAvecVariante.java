package com.schottenTotten.model;

import com.schottenTotten.controller.Partie;
import java.util.ArrayList;

/**
 * Classe Partie étendue qui utilise le système de variantes
 * Permet de configurer automatiquement le jeu selon la variante choisie
 */
public class PartieAvecVariante extends Partie {
    
    private Variante variante;
    
    /**
     * Constructeur qui configure la partie selon la variante
     * @param variante La variante à utiliser
     * @param joueur1 Premier joueur
     * @param joueur2 Second joueur
     */
    public PartieAvecVariante(Variante variante, Joueur joueur1, Joueur joueur2) {
        // Appeler le constructeur parent SANS distribuer les cartes automatiquement
        super("Standard", variante.getNom(), joueur1, joueur2);
        this.variante = variante;
        
        // Remplacer la pioche par une pioche adaptée à la variante
        this.pioche = new Pioche(variante.utiliseCartesTactiques());
        
        // Maintenant distribuer les cartes selon la variante
        distribuerCartesApresInitialisation();
    }
    
    /**
     * Ajuste la distribution des cartes selon la variante (après l'initialisation)
     */
    private void ajusterDistributionSelonVariante() {
        if (pioche == null) return; // Protection contre null
        
        int nombreCartesActuel = getJoueur1().getNombreCartes();
        int nombreCartesVoulu = variante.getNombreCartesMain();
        
        // Si on a besoin de plus de cartes (variante tactique = 7 vs base = 6)
        if (nombreCartesVoulu > nombreCartesActuel) {
            int cartesSupplementaires = nombreCartesVoulu - nombreCartesActuel;
            for (int i = 0; i < cartesSupplementaires; i++) {
                if (pioche.getNombreCartes() > 0) {
                    getJoueur1().ajouterCarte(pioche.piocher());
                }
                if (pioche.getNombreCartes() > 0) {
                    getJoueur2().ajouterCarte(pioche.piocher());
                }
            }
        }
        // Si on a besoin de moins de cartes (cas rare)
        else if (nombreCartesVoulu < nombreCartesActuel) {
            int cartesEnTrop = nombreCartesActuel - nombreCartesVoulu;
            for (int i = 0; i < cartesEnTrop; i++) {
                if (!getJoueur1().getMain().isEmpty()) {
                    getJoueur1().getMain().remove(0);
                }
                if (!getJoueur2().getMain().isEmpty()) {
                    getJoueur2().getMain().remove(0);
                }
            }
        }
    }
    
    /**
     * Redistribue les cartes selon le nombre spécifié par la variante
     */
    private void redistribuerCartesSelonVariante() {
        // Vider les mains actuelles
        getJoueur1().getMain().clear();
        getJoueur2().getMain().clear();
        
        // Distribuer selon la variante
        int nombreCartes = variante.getNombreCartesMain();
        for (int i = 0; i < nombreCartes; i++) {
            if (pioche.getNombreCartes() > 0) {
                getJoueur1().ajouterCarte(pioche.piocher());
            }
            if (pioche.getNombreCartes() > 0) {
                getJoueur2().ajouterCarte(pioche.piocher());
            }
        }
    }
    
    /**
     * Override de la méthode distribuerCartes pour éviter les problèmes d'initialisation
     */
    @Override
    public void distribuerCartes() {
        // Ne rien faire ici, on distribuera les cartes après l'initialisation complète
    }
    
    /**
     * Méthode publique pour distribuer les cartes après l'initialisation complète
     */
    public void distribuerCartesApresInitialisation() {
        if (pioche == null) return;
        
        // Vider les mains au cas où
        getJoueur1().getMain().clear();
        getJoueur2().getMain().clear();
        
        // Distribuer le bon nombre de cartes selon la variante
        int nombreCartes = variante.getNombreCartesMain();
        for (int i = 0; i < nombreCartes; i++) {
            if (pioche.getNombreCartes() > 0) {
                getJoueur1().ajouterCarte(pioche.piocher());
            }
            if (pioche.getNombreCartes() > 0) {
                getJoueur2().ajouterCarte(pioche.piocher());
            }
        }
    }
    
    /**
     * Getter pour la variante utilisée
     * @return L'instance de la variante
     */
    public Variante getVarianteConfig() {
        return variante;
    }
    
    /**
     * Vérifie si cette partie utilise les cartes tactiques
     * @return true si les cartes tactiques sont utilisées
     */
    public boolean utiliseCartesTactiques() {
        return variante.utiliseCartesTactiques();
    }
    
    /**
     * Override de getEtatPartie pour inclure les informations de variante
     */
    @Override
    public String getEtatPartie() {
        StringBuilder etat = new StringBuilder();
        etat.append("=== ÉTAT DE LA PARTIE (").append(variante.getNom()).append(") ===\n");
        etat.append("Cartes en main par joueur: ").append(variante.getNombreCartesMain()).append("\n");
        etat.append("Cartes tactiques: ").append(variante.utiliseCartesTactiques() ? "Oui" : "Non").append("\n");
        etat.append("Joueur courant: ").append(getJoueurCourant().getNom()).append("\n");
        etat.append("Cartes restantes dans la pioche: ").append(pioche.getNombreCartes()).append("\n");
        
        etat.append("\nÉtat des bornes:\n");
        for (int i = 0; i < getBornes().size(); i++) {
            Borne borne = getBornes().get(i);
            etat.append("Borne ").append(i + 1).append(": ");
            etat.append("J1(").append(borne.getCartesJoueur1Size()).append(") ");
            etat.append("J2(").append(borne.getCartesJoueur2Size()).append(") ");
            etat.append("État: ").append(borne.getEtat()).append("\n");
        }
        
        return etat.toString();
    }
}