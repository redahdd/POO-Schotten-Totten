package com.schottenTotten.model;

import com.schottenTotten.controller.Partie;
import com.schottenTotten.controller.JeuFactory;
import com.schottenTotten.ai.IAAleatoire;
import java.util.Scanner;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IAAleatoire ia = new IAAleatoire();

        System.out.println("=== BIENVENUE AU SCHOTTEN-TOTTEN ===");
        
        // 1. Choix variante via JeuFactory
        System.out.print("Choisissez la variante (1: Base, 2: Tactique) : ");
        int v = scanner.nextInt();
        
        Joueur humain = new Joueur(1, "Humain");
        Joueur robot = new Joueur(2, "Robot", Joueur.TypeIA.ALEATOIRE);
        
        Partie partie = (v == 2) ? JeuFactory.creerPartieTactique(humain, robot) : JeuFactory.creerPartieBase(humain, robot);

        System.out.println("Partie créée ! Variante : " + partie.getVariante());
        System.out.println("Chaque joueur a " + humain.getNombreCartes() + " cartes en main.\n");

        // 2. Boucle de jeu principale
        while (!partie.estTerminee()) {
            Joueur courant = partie.getJoueurCourant();
            
            if (courant.estIA()) {
                // IA utilise la classe IAAleatoire
                System.out.println("\n=== Tour de " + courant.getNom() + " (IA) ===");
                int[] coup = ia.choisirCoup(courant, partie.getBornes());
                
                if (coup != null) {
                    String resultat = partie.jouerTour(coup[0], coup[1]);
                    System.out.println("IA joue carte " + coup[0] + " sur borne " + (coup[1]+1) + ": " + resultat);
                } else {
                    System.out.println("IA ne peut pas jouer !");
                    break;
                }
                
            } else {
                // Tour du joueur humain
                System.out.println("\n=== Votre tour ===");
                System.out.println(partie.getEtatPartie());
                
                // Afficher la main
                System.out.println("Votre main :");
                for (int i = 0; i < courant.getMain().size(); i++) {
                    Carte carte = courant.getMain().get(i);
                    if (carte instanceof CarteTactique) {
                        CarteTactique carteTactique = (CarteTactique) carte;
                        System.out.println("  " + i + ": " + carteTactique.toString() + " - " + carteTactique.getDescription());
                    } else {
                        System.out.println("  " + i + ": " + carte.getCouleur() + " " + carte.getValeur());
                    }
                }
                
                // Choisir une carte et une borne
                int indexCarte, indexBorne;
                
                do {
                    System.out.print("Index carte à jouer (0-" + (courant.getMain().size()-1) + ") : ");
                    indexCarte = scanner.nextInt();
                } while (indexCarte < 0 || indexCarte >= courant.getMain().size());
                
                do {
                    System.out.print("Borne à cibler (1-9) : ");
                    indexBorne = scanner.nextInt() - 1;
                } while (indexBorne < 0 || indexBorne > 8);
                
                // Jouer le coup
                String resultat = partie.jouerTour(indexCarte, indexBorne);
                System.out.println("Résultat : " + resultat);
                
                if (resultat.contains("invalide")) {
                    System.out.println("Coup impossible ! Réessayez.");
                    continue; // Recommencer le tour
                }
            }
        }
        
        // Fin de partie
        System.out.println("\n" + partie.getEtatPartie());
        System.out.println("\n=== FIN DE PARTIE ===");
        if (partie.getVainqueur() != null) {
            System.out.println("🎉 VAINQUEUR : " + partie.getVainqueur().getNom() + " !");
        } else {
            System.out.println("Match nul !");
        }
        
        scanner.close();
    }
}