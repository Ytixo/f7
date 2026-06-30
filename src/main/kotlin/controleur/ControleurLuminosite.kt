package controleur

import javafx.scene.effect.ColorAdjust
import vue.MainVue
import vue.VueParametre

class ControleurLuminosite(mainVue: MainVue, vueParametre: VueParametre) {

    //1.Utilisations d'un bind pour accorder la valeur du slider à la valeur de la luminosité
    init {
        val filtre = ColorAdjust()

        mainVue.effect = filtre
        filtre.brightnessProperty().bind(vueParametre.sombreProperty().multiply(-1.0)
        )
    }
}