@startuml
skinparam style strictuml

abstract class Carte {
+ suivant : Carte?
}

class Bonus {
+ signe : String
+ valeur : Int
}

class Numero {
+ valeur : Int
}

class Action {
+ titre : String
}

Carte <|-- Bonus
Carte <|-- Action
Carte <|-- Numero

class Flip7 {
- scoreFinal : Int
- Manche : Int
- indexJoueurActuel : Int
- joueurEnJeu : Int
- nbJoueurs : Int
- 1erJoueur : Int
}

class Joueur {
- nom : String {read only}
- totalPoint : Int
- statut : Boolean
- secondeChance : Boolean ReadOnly
}

class Pile {
- nbCarte : Int
}

Flip7 "1" -- "1" Pile : "-pioche"
Flip7 "1" -- "1" Pile : "-defausse"
Flip7 "1" -- "2..18" Joueur : "-joueurs"

Pile "1" --> "0..1" Carte : "-premier"

Joueur "0..1" -- "0..7" Numero : "-listCardNumber"
Joueur "0..1" -- "0..*" Bonus : "-listCardBonus"
Joueur "0..1" -- "0..*" Action : "-listCardAction"

note "joueurs : ArrayOf<Joueur>" as NoteListJoueur

note "listCardNumber : ArrayOf<Numero>" as NoteListCardNumber
note "listCardBonus : ArrayOf<Bonus>" as NoteListCardBonus
note "listCardAction : ArrayOf<Action>" as NoteListCardAction

Joueur .. NoteListCardNumber
Joueur .. NoteListCardBonus
Joueur .. NoteListCardAction

Flip7 .. NoteListJoueur


@enduml