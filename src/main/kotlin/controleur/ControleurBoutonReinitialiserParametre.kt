// ControleurBoutonReinitialiserParametre.kt
package controleur

import vue.MainVue
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import vue.VueParametre
import outils.GestionnaireLangue

class ControleurBoutonReinitialiserParametre(
    private val vue: MainVue,
    private val vueParametre: VueParametre
) : EventHandler<ActionEvent> {

    override fun handle(p0: ActionEvent?) {
        val a = Alert(Alert.AlertType.CONFIRMATION)

        a.title = GestionnaireLangue.texte("parametre.controleur.reinitialiser.validation")
        a.headerText = GestionnaireLangue.texte("parametre.controleur.reinitialiser.headerText")
        a.contentText = GestionnaireLangue.texte("parametre.controleur.reinitialiser.contentText")

        val result = a.showAndWait()

        if (result.isPresent && result.get() == ButtonType.OK) {
            vueParametre.reinitialiser()
            vue.menuInit.reinitialiser()
            vue.retourVue()
        }

    }
}