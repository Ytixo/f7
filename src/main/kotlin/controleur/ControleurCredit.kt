// ControleurCredit.kt
package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Alert
import outils.GestionnaireLangue

class ControleurCredit : EventHandler<ActionEvent> {

    override fun handle(event: ActionEvent?) {
        // 1. Création de la boîte de dialogue d'information
        val alerte = Alert(Alert.AlertType.INFORMATION)

        // 2. Préparation du texte avec les différentes sections

        alerte.title = GestionnaireLangue.texte("parametre.controleur.credit.title")
        alerte.headerText = GestionnaireLangue.texte("parametre.controleur.credit.headerText")
        alerte.contentText = GestionnaireLangue.texte("parametre.controleur.credit.texteCredit")

        alerte.showAndWait()
    }
}