# F7

Projet réalisé dans le cadre d'une SAE de groupe à 4.
Il s’agit d’une implémentation du jeu de cartes **Flip 7**, avec une partie graphique et une suite de tests.

Pour exécuter le jeu :

```bash
./gradlew run
```

## Équipe

Projet réalisé par un groupe de 4

## Description du projet

Flip 7 est un jeu de cartes où les joueurs doivent tenter d’obtenir le meilleur score possible sans dépasser certaines limites.

Ce projet contient :

- Une **interface de partie** en modele MVC
- Une **suite de tests automatisés**

### Vue

L’interface graphique permet une expérience utilisateur fluide avec plusieurs écrans :

- 🏠 **Menu principal**
- ⚙️ **Écran de paramètre de partie**
- 🎮 **Écran de jeu**
- 🧾 **Écran de fin de partie**
- ℹ️ **Menu crédits / paramètre**

L’interface est pensée pour être simple, lisible et agréable à utiliser.

### Contrôleur

Le contrôleur fait le lien entre la vue et le modèle :

- Gestion des actions utilisateur (boutons, clics)
- Mise à jour de l’interface en fonction du jeu
- Lancement et arrêt des parties
- Navigation entre les différents menus

## 🧪 Tests

Le projet inclut des tests permettant de vérifier :

- Le bon fonctionnement des règles du jeu
- Le calcul des scores
- Les comportements des joueurs
- La validité des actions possibles

Pour exécuter les tests :

```bash
./gradlew test
```
