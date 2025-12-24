package com.schottenTotten.ai;

import com.schottenTotten.model.*;
import com.schottenTotten.controller.Partie;
import java.util.ArrayList;
import java.util.Random;

/**
 * Implémentation d'une IA simple qui joue au hasard
 */
public class IAAleatoire {
    
    private Random random;
    
    public IAAleatoire() {
        this.random = new Random();
    }
    
    /**
     * Choisit un coup aléatoire pour l'IA
     * @param joueur Le joueur IA
     * @param bornes La liste des bornes du jeu
     * @return Un tableau [indexCarte, indexBorne] ou null si aucun coup possible
     */
    public int[] choisirCoup(Joueur joueur, ArrayList<Borne> bornes) {
        if (joueur.getNombreCartes() == 0) {
            return null;
        }
        
        // Créer une liste des coups possibles
        ArrayList<int[]> coupsPossibles = new ArrayList<>();
        
        for (int indexBorne = 0; indexBorne < bornes.size(); indexBorne++) {
            Borne borne = bornes.get(indexBorne);
            if (borne.getEtat() == Borne.EtatBorne.LIBRE && peutJouerSurBorne(borne, joueur)) {
                for (int indexCarte = 0; indexCarte < joueur.getMain().size(); indexCarte++) {
                    coupsPossibles.add(new int[]{indexCarte, indexBorne});
                }
            }
        }
        
        // Si aucun coup possible
        if (coupsPossibles.isEmpty()) {
            return null;
        }
        
        // Choisir un coup aléatoire
        return coupsPossibles.get(random.nextInt(coupsPossibles.size()));
    }
    
    /**
     * Vérifie si un joueur peut encore jouer sur une borne
     */
    private boolean peutJouerSurBorne(Borne borne, Joueur joueur) {
        if (joueur.getId() == 1) {
            return borne.getCartesJoueur1Size() < 3;
        } else {
            return borne.getCartesJoueur2Size() < 3;
        }
    }
}