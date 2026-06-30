@startuml
skinparam style strictuml

class "Carte" <<abstract>> {

-suivant : Carte?
}

class Bonus {

-signe : String
-valeur : Int

+ <<create>> Bonus(signe : String, valeur : Int)
  }

class Numero {

-valeur : Int

+ <<create>> Numero(valeur : Int)
  }

class Action {

-titre : String

+ <<create>> Action(titre : String)
  }

Carte <|-- Bonus
Carte <|-- Action
Carte <|-- Numero
class Flip7 {

-scoreFinal : Int
-Manche : Int
-indexJoueurActuel : Int
-joueurEnJeu : Int
-nbJoueurs : Int
-1erJoueur : Int

+ <<create>> Flip7(scoreFinal : Int)
  +init()
  +jeu()
  +pioche() : Carte
  +remplirPioche()
  +shuffle(cartes : Array<Carte>)
  +ajouterJoueur(index : Int)
  +eliminerJoueur(index : Int)
  +getJoueurs() : Array<Joueur>
+ donneJoueurSecondeChance() : Array<Joueur>
+ donneEnJeu() : Array<Joueur>
  +nouveauJoueur(pseudo : string)
  }

class Joueur {

-nom : String {read only}
-totalPoint : Int
-statut : Boolean
-secondeChance : Boolean ReadOnly

+ <<create>> Joueur(nom : String)
  +possedeLaCarte(numero:int): Boolean
  +finDeManche()
  +arretJoueur()
  +pioche3Cartes(jeu : Flip7)
  +ajoutSecondeChance() : Boolean
  +deciderContinuer() : Boolean
  +donneCarte(list_joueur : List, carteAdonner : Carte) : Joueur
  +recevoirCarte(jeu : Flip7)
  +enleverCarte(jeu : Flip7)
  +getStatut() : Boolean
  }

class Pile {

-nbCarte : Int

+estVide() : Boolean
+empiler(c : Carte)
+depiler() : Carte
}

Flip7 "1" -- "1" Pile : - pioche
Flip7 "1" -- "1" Pile : - defausse
Flip7 "1" -- "2..18" Joueur : - joueurs
Flip7 "1" -- "2..18" Joueur : - joueursManche
Pile "1" --> "0..1" Carte : - premier
Joueur "0..1" -- "0..7" Numero : - listCardNumber
Joueur "0..1" -- "0.." Bonus : - listCardBonus
Joueur "0..1" -- "0.." Action : - listCardAction
note "joueurs : ArrayOf" as NoteListJoueur
note "joueursManche : ArrayOf" as NoteListJoueursManche
note "listCardNumber : ArrayOf" as NoteListCardNumber
note "listCardBonus : ArrayOf" as NoteListCardBonus
note "listCardAction : ArrayOf" as NoteListCardAction
Joueur .. NoteListCardNumber
Joueur .. NoteListCardBonus
Joueur .. NoteListCardAction
Flip7 .. NoteListJoueur
Flip7 .. NoteListJoueursManche

class NoSuchElement

Flip7 ..> NoSuchElement
@enduml
