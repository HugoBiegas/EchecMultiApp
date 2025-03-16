# EchecMultiApp - Jeu d'échecs en ligne

## Présentation du projet

EchecMultiApp est une application mobile Android hébergeant un jeu d'échecs multijoueur en ligne. Développée en Java, l'application utilise une base de données distante Firebase pour gérer les comptes utilisateurs, les salons de jeu et les parties en cours. Le système de tour par tour en ligne est également intégré via l'API Firebase.

## Fonctionnalités

### Authentification
- Création de compte avec email, mot de passe et nom d'utilisateur
- Connexion sécurisée via Firebase Authentication
- Récupération de mot de passe par email

### Gestion des parties
- Création de salons de jeu privés
- Rejoindre des parties existantes
- Interface de jeu intuitive

### Jeu d'échecs complet
- Implémentation des règles traditionnelles d'échecs
- Déplacement de toutes les pièces selon leurs règles spécifiques
- Gestion des situations spéciales (échec, échec et mat, promotion, roque)
- Interface visuelle avec plateau et pièces colorées

### Profil utilisateur
- Suivi des statistiques (victoires, défaites, taux de victoire)
- Affichage du profil utilisateur

## Technologies utilisées

### Firebase
- **Authentication** : Gestion des utilisateurs et authentification sécurisée
- **Realtime Database** : Base de données en temps réel pour les salons et parties
- **Cloud Firestore** : Stockage structuré des données utilisateurs (victoires, défaites)

### Android
- SDK minimum : Android 5.0 (Lollipop)
- Interface utilisateur responsive
- Gestion des événements et interactions tactiles

## Installation

1. Cloner le dépôt GitHub :
   ```
   git clone https://github.com/mordeur/EchecMultiApp.git
   ```

2. Ouvrir le projet dans Android Studio

3. Configurer Firebase :
   - Créer un projet dans la console Firebase
   - Ajouter une application Android et suivre les instructions d'installation
   - Télécharger et placer le fichier `google-services.json` dans le dossier app

4. Compiler et installer l'application sur un appareil Android ou un émulateur

## Guide d'utilisation

### Première utilisation
1. Créer un compte ou se connecter
2. Accéder à l'écran des salons

### Jouer une partie
1. Créer un salon ou rejoindre un salon existant
2. Attendre qu'un adversaire rejoigne le salon (si création)
3. Jouer selon les règles des échecs
4. Le résultat (victoire, défaite ou partie nulle) est automatiquement enregistré

### Règles du jeu
L'application implémente les règles classiques des échecs, incluant :
- Mouvements spécifiques à chaque pièce
- Échec et échec et mat
- Promotion des pions
- Roque (petit et grand)

## Structure du projet

Le projet est organisé en packages :
- **Connection** : Gestion de l'authentification et des profils
- **Pion** : Classes pour les différentes pièces du jeu d'échecs
- **Room** : Création et gestion des salons
- **Adapter** : Adaptateurs pour l'affichage du plateau et des pièces

## Liens utiles

- [Repository GitHub](https://github.com/mordeur/EchecMultiApp)
- [Documentation Firebase](https://firebase.google.com/docs)
