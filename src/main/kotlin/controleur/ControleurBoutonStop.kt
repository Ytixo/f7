package controleur

import iut.info1.flip7.Flip7
import javafx.animation.PauseTransition
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.util.Duration
import vue.VuePartie

class ControleurBoutonStop(val modele: Flip7, val vue: VuePartie, val controleurPartie: ControleurPartie) : EventHandler<ActionEvent> {

    /**
     * Demande la confirmation du joueur si oui ou non, il est sûr de s'arrêter
     */
    override fun handle(p0: ActionEvent?) {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.headerText = "Tu veux t'arrêter ?"

        val result = alert.showAndWait()
        if (result.isPresent && result.get() == ButtonType.OK) {
            val nomJoueur = modele.joueurs[modele.joueurCourant].donneNom()
            modele.joueurCourantDitStop()
            vue.afficherBandeauPerso("$nomJoueur a dit STOP !")
            val pause = PauseTransition(Duration.millis(1900.0))
            pause.setOnFinished {
                controleurPartie.rafraichir()
                vue.majMain(modele.main)
            }
            pause.play()
        }
    }
}