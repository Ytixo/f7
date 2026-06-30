package controleur

import vue.MainVue
import javafx.event.EventHandler
import javafx.event.ActionEvent

class ControleurVersMenuMain(var vue: MainVue): EventHandler<ActionEvent>{

    /**
     * Retour au menu Principal
     */
    override fun handle(p0: ActionEvent?) {
        vue.menuInit.reinitialiser()
        vue.changerVue(vue.menuMain)
    }
    }
