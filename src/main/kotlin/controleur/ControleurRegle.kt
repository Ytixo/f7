package controleur

import iut.info1.flip7.cartes.Carte2ndeChance
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.stage.Screen
import javafx.scene.control.TextArea
import outils.GestionnaireLangue


class ControleurRegle : EventHandler<ActionEvent> {

    override fun handle(event: ActionEvent?) {
        // 1. Création de la boîte de dialogue d'information
        val alerte = Alert(Alert.AlertType.INFORMATION)
        alerte.title = GestionnaireLangue.texte("parametre.controleur.regle.title")
        alerte.headerText = GestionnaireLangue.texte("parametre.controleur.regle.headerText")

        // 2. Préparation du texte avec les règles du jeu
        val texteRegle = GestionnaireLangue.texte("parametre.controleur.regle.texteRegle").trimIndent()

        alerte.isResizable = true

        /**
         * Permet de faire une alert qui est scrollable
         */
        val zoneTexte = TextArea(texteRegle)
        zoneTexte.isEditable = false
        zoneTexte.isWrapText = true

        /**
         * Fait en sorte que la fenêtre de l'alert s'adapte à la taille de l'écran
         */
        val screen = Screen.getPrimary().visualBounds
        val ratio = 950.0 / 1000.0

        val largeurAlert: Double
        val hauteurAlert: Double

        if (screen.width / screen.height > ratio) {
            hauteurAlert = screen.height * 0.9
            largeurAlert = hauteurAlert * ratio
        } else {
            largeurAlert = screen.width * 0.9
            hauteurAlert = largeurAlert / ratio
        }

        alerte.dialogPane.prefWidth = largeurAlert
        alerte.dialogPane.prefHeight = hauteurAlert
        alerte.dialogPane.content = zoneTexte

        alerte.showAndWait()
    }
}