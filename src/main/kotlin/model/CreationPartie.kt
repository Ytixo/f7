package model

import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.OutilsCarte
import Joueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop

class CreationPartie {
    /**
     * Génère la pioche de base pour le jeu Flip7
     */
    fun creerPioche(): MutableList<Carte> {
        val pi = mutableListOf<Carte>()

        pi.add(CarteNum(0))
        for (i in 1..12) {
            repeat(i) {
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2, 4, 6, 8, 10)) {
            pi.add(CarteBonusPlus(i))
        }
        pi.add(CarteBonusMultiplie())
        repeat(3) {
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }

        return pi
    }

    /**
     * Instancie les joueurs et le jeu complet
     */
    fun creerJeu(nomsJoueurs: List<String>, scoreFin: Int): Flip7 {
        val joueurs = mutableListOf<IJoueur>()
        for (nom in nomsJoueurs) {
            joueurs.add(Joueur(nom))
        }

        val pioche = creerPioche()

        return Flip7(joueurs.size, joueurs, pioche, false, scoreFin, OutilsCarte())
    }
}
