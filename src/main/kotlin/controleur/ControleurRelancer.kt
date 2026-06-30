package controleur

import iut.info1.flip7.Flip7
import iut.info1.flip7.etats.EtatPartie
import javafx.event.ActionEvent
import javafx.event.EventHandler
import vue.MainVue

class ControleurRelancer(var vue: MainVue, val jeu : Flip7): EventHandler<ActionEvent>{

    override fun handle(p0: ActionEvent?) {
        vue.menuPartie.reinitialiser()
        val controleurPartie = ControleurPartie(jeu, vue.menuPartie)
        vue.menuPartie.fixeControleurPioche(ControleurPioche(jeu, vue.menuPartie, controleurPartie))
        vue.changerVue(vue.menuInit)
    }
}