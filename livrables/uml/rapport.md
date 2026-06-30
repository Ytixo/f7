# Rapport de Spécification - Jeu Flip7

Ce rapport présente les choix d'analyse et de modélisation retenus par notre groupe pour l'implémentation du jeu Flip 7, en justifiant les choix pour le diagramme UML (les classes, les attributs et les méthodes choisies).



## 1. But du Jeu

Le principe du Flip 7 est d'atteindre **200 points** (il est possible de changer cette valeur). 

À chaque manche, vous accumulez les points gagnés. Pendant chaque manche, vous allez devoir choisir entre continuer ou s’arrêter. 
* Si vous avez un **double** dans vos cartes, c’est perdu. 
* Si vous arrivez à obtenir **7 cartes** de numéros différents, la manche s'arrête et vous gagnez **15 points** supplémentaires.

### Choix de modélisation
* **ScoreFinal** = score défini avant la partie correspondant au nombre de points pour gagner. Le score est défini avec une méthode `init()` qui permet également d’ajouter des joueurs et de donner le nombre de joueurs.
* **Calcul du score** (calculé dans la fin de manche) :
  $$\text{Score} = \text{Score}+(\text{Somme des Numéros} \times \text{Multiplicateurs}) + \text{Sommes des Bonus}$$

---

## 2. Gestion des Cartes et des Effets

### Règle du jeu
Le jeu Flip 7 est composé de **79 cartes Numéros**, de **6 cartes Bonus** et **9 cartes Actions**. Contrairement aux cartes Numéros qui s'accumulent devant le joueur, les cartes Actions déclenchent des effets immédiats ou modifient des états de joueur dès leur pioche.

#### Choix de modélisation
* **Les cartes :** Une classe abstraite `Carte` est déclinée en trois sous-classes : `Numero`, `Bonus` et `Action`.
* **Identification de l'action :** La classe `Action` possède un attribut `titre : String`. C'est la valeur de ce titre (*"Trois Cartes"*, *"Stop"*, *"Seconde Chance"*) qui donne l'action que le joueur peut réaliser. Comme il n'existe que trois types d'actions, trois conditions suffisent pour les distinguer.

#### Cartes Numéro
Il y a douze 12, onze 11 jusqu’à 1 et en plus une carte zéro.

#### Cartes Bonus
Il y a une carte pour chacune des valeurs (+2, +4, +6, +8, +10, x2).
* Les cartes **addition** sont à ajouter à votre score en dernier.
* La carte **multiplicateur** multiplie la somme des cartes nombre.

#### Cartes Action
Il y a 3 cartes de chaque type, pour chaques cartes Action, une liste de joueur qui peuvent recevoir cette carte est transmise.
#### Choix de modélisation
* **Liste des joueurs en vie :** La méthode `DonneEnJeu()` permet de renvoyer toutes les personnes en vie pouvant recevoir une carte.


* **Bloque :** Le joueur ciblé voit son score de manche bloqué et quitte immédiatement la manche. Si le joueur est seul dans la manche, il est obligé de se cibler lui-même.
* **Trois cartes :** lors du tirage des 3 cartes, si le joueur réussit un *flip seven* en cours de tirage, le tirage s'arrête et la manche est terminée. Si une carte Action est tirée, elle ne prend effet qu’à la fin du tirage. Si le joueur est éliminé en cours, on arrête le tirage.

   * *Vérification :* La méthode `pioche3Carte()` va à chaque tirage vérifier les éventuelles possibilités d’arrêt.
   * *Multiplicités strictes :* Dans le diagramme, la relation entre `Joueur` et `Numero` est strictement bridée à `0..7` pour matérialiser la limite absolue imposée par les règles du jeu.
  
* **Seconde Chance :** Une personne ne peut avoir qu'une seule carte Seconde chance à la fois. S'il tire une nouvelle carte Seconde chance alors qu'il en a déjà une dans son jeu, il doit alors donner cette carte à quelqu'un d'autre qui est encore en jeu (cette personne ne doit pas déjà en posséder une). Si tout le monde en possède une, alors la carte doit être défaussée.

    *    Avant de donner la carte seconde chance, le jeu donne une liste des joueurs qui n'ont pas de seconde chance ; s'il n'y a personne dans cette liste, il la met dans la défausse. La liste des joueurs pouvant recevoir une carte seconde chance est créée grâce à la méthode `DonneJoueurSecondeChance()`. Ensuite l'action est attribuée avec la méthode `AjouteSecondeChance()`.

---

## 3. Structure de Données pour la Pioche et la Défausse

### Règle du jeu
Le jeu nécessite une **pioche** dans laquelle les joueurs tirent des cartes une par une, et une **défausse** où sont envoyées les cartes des joueurs qui ont perdu, les cartes utilisées ou les cartes nettoyées en fin de manche. Lorsque la pioche est vide, la défausse doit être mélangée pour reconstruire une nouvelle pioche.

#### Choix de modélisation
* **Implémentation d'une Pile :** Nous avons fait le choix d'implémenter la classe `Pile` fonctionnant comme une liste chaînée.
* **Pointeurs chaînés :** La classe `Carte` possède un attribut `suivant : Carte?` nullable. La classe `Pile` possède une association `premier` vers la `Carte` au sommet. Les méthodes `empiler(Carte)` et `depiler() : Carte` permettent de récupérer les cartes ou de remplir la pile.
* **Initialisation Pile :** La `Pile` est initialisée par la méthode `init()`.
* **Logique de mélange (Remplir_pioche) :** Pour mélanger la défausse et remplir la pioche, la méthode de la classe `Flip7` va dépiler l'intégralité de la défausse dans un array, puis le mélanger aléatoirement avec un `shuffle()`, puis ré-empiler les cartes une à une dans la pioche.

---

## 4. Le Tour de Jeu

### Règle du jeu
Le premier joueur à jouer change à chaque manche.
À tour de rôle, chaque joueur doit explicitement décider s'il s'arrête (sécurisant ses points et passant son statut à inactif pour le reste de la manche) ou s'il continue à piocher au risque de faire un doublon et de perdre ses cartes. Il faut aussi savoir le type de carte et donc s'il faut la donner à un autre joueur.

#### Choix de modélisation
* **Premier joueur :** l'attribut `premierjoueur : Int` passe au joueur suivant à chaque manche afin de déterminer le prochain premier joueur.
* **Méthode de décision :** La classe `Joueur` intègre la méthode `deciderContinuer() : Boolean`. Cette méthode permet de savoir si le joueur veut continuer et donc piocher.
* **Statuts :** L'attribut `statut : Boolean` chez le `Joueur` indique s'il est encore en jeu et l'attribut `joueurEnJeu : Int` dans `Flip7` compte les joueurs restants pour déclencher automatiquement `finDeManche()`.
* **Déroulement d'une manche :** Le jeu demande au joueur actuel (`indexJoueurActuel : Int`) s'il veut s'arrêter ou continuer, puis le jeu appelle la méthode `pioche():Carte` pour récupérer une carte et regarder de quelle carte il s'agit.
  * S'il s'agit d'un **nombre** ou d'un **bonus**, il va la donner au joueur avec `recevoirCarte()`.
  * S'il s'agit d'une **carte action**, le jeu donne une liste de joueurs pouvant récupérer la carte(`donneCarte(list_joueur : List, carteAdonner : Carte) : Joueur`), en vérifiant à chaque fois si le joueur est en jeu (et pour la seconde chance s'il n'en a pas déjà une).
  * Puis il va utiliser la méthode correspondante à l'action sur le joueur choisi : soit mettre la seconde chance avec `ajoutSecondeChance() : Boolean`, soit piocher trois cartes avec `pioche3Cartes()` (attention, le joueur piochera bien 3 cartes mais pourra ne pas les recevoir s'il n'est plus en jeu) et enfin stopper un joueur avec `arretJoueur()`. Après ce tour de joueur, on passera au suivant et ainsi de suite jusqu'à la fin de la manche.
* **Double :** Quand la méthode `RecevoirCarte()` est appelée, elle va appelée la méthode `possedeLaCarte()`qui va vérifier si un joueur a un double dans ses cartes. Si c’est vrai, alors la méthode va défausser la carte en trop et la carte vie, et passer son attribut `SecondeChance` à `False`. 
* **Élimination :** À chaque fin de tirage, le jeu vérifie si le joueur doit être éliminé ou pas avec `Eliminejoueur()`.
* **Statut mort :** Quand une personne est éliminée, son statut est à `False` et on lui retire toutes ses cartes. La méthode s’appelle `EnleverCarte(jeu:Flip7)`.
* **Distribution :** La méthode `RecevoirCarte(jeu:Flip7)` permet de transférer les cartes de la pile à la main du joueur qui a pioché.
* **Récupération des cartes :** La méthode `EnleverCarte(jeu:Flip7)` permet de transférer les cartes de la main du joueur à la défausse.

### Fin de manche
À la fin de la manche, toutes les cartes bonus sont défaussées et les points sont calculés avec la méthode suivante : on additionne les cartes numéro, puis on applique le $\times 2$ si on l'a, puis on ajoute les cartes modificateur au total, et pour finir, si on a fait un *flip seven*, on ajoute 15 à la somme totale.

#### Choix de modélisation
On a créé une méthode `FinDeManche()` qui vérifie si une personne a des cartes. Si oui, on calcule la somme suivant les règles, on remet les cartes dans la défausse puis on passe au joueur suivant.

---
*(Utilisation IA : Gemini ; mise en forme du rapport en .md)*
