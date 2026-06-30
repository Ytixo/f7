package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import vue.MainVue

class ControleurVersMenuInit(var vue: MainVue): EventHandler<ActionEvent>{

    override fun handle(p0: ActionEvent?) {
        vue.changerVue(vue.menuInit)
    }
}