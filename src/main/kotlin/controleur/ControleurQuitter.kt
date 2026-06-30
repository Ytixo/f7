package controleur

import javafx.animation.PauseTransition
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.util.Duration
import vue.F7NeonLogoEteintre
import outils.Musique
import vue.MainVue

class ControleurQuitter(var vue: MainVue): EventHandler<ActionEvent>{

    override fun handle(p0: ActionEvent?) {


        vue.menuMain.changerLogo(F7NeonLogoEteintre())

        /* géminie faire que l'ecran se ferme pas avant la fin de l'animation*/
        val pause = PauseTransition(Duration.seconds(3.0))

        pause.setOnFinished {
            Musique.stopMusic()
            Platform.exit()
        }
        pause.play()

    }
}