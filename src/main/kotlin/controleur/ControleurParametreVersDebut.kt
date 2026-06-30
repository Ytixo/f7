package controleur

import vue.MainVue
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import outils.GestionnaireLangue

class ControleurParametreVersDebut(var vue: MainVue) : EventHandler<ActionEvent> {

    override fun handle(p0: ActionEvent?) {

        val a = Alert(Alert.AlertType.CONFIRMATION)
        a.title = GestionnaireLangue.texte("parametre.controleur.quitter.validation")
        a.headerText = GestionnaireLangue.texte("parametre.controleur.quitter.headerText")
        a.contentText = GestionnaireLangue.texte("parametre.controleur.quitter.contentText")

        val result = a.showAndWait()

        if (result.isPresent && result.get() == ButtonType.OK) {
            vue.menuInit
        }
    }
}