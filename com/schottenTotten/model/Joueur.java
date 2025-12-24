package com.schottenTotten.model;
import java.util.ArrayList;

public class Joueur {
    public int id;
    private String nom;
    private TypeJoueur type;
    private TypeIA typeIA;
    private String role;
    
        public enum TypeJoueur {
        HUMAIN,
        IA
    }

    public enum TypeIA {
        ALEATOIRE,
        STRATEGIQUE,
        NONE  // Pour les joueurs humains
    }
    
    protected ArrayList<Carte> main;

    // Constructeur pour joueur humain
    public Joueur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.type = TypeJoueur.HUMAIN;
        this.typeIA = TypeIA.NONE;
        this.role = "Joueur";
        this.main = new ArrayList<>();
    }
    
    // Constructeur pour joueur IA
    public Joueur(int id, String nom, TypeIA typeIA) {
        this.id = id;
        this.nom = nom;
        this.type = TypeJoueur.IA;
        this.typeIA = typeIA;
        this.role = "IA";
        this.main = new ArrayList<>();
    }
    
    // Constructeur complet
    public Joueur(int id, String nom, TypeJoueur type, TypeIA typeIA, String role) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.typeIA = typeIA;
        this.role = role;
        this.main = new ArrayList<>();
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }
    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Nouveaux getters pour les attributs ajoutés
    public TypeJoueur getType() {
        return type;
    }
    
    public TypeIA getTypeIA() {
        return typeIA;
    }
    
    public String getRole() {
        return role;
    }
    
    // Setters pour permettre la modification
    public void setType(TypeJoueur type) {
        this.type = type;
    }
    
    public void setTypeIA(TypeIA typeIA) {
        this.typeIA = typeIA;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Méthodes utilitaires
    public boolean estIA() {
        return type == TypeJoueur.IA;
    }
    
    public boolean estHumain() {
        return type == TypeJoueur.HUMAIN;
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
