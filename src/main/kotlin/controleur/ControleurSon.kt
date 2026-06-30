package controleur

import outils.Musique
import vue.MainVue

class ControleurSon(vue: MainVue) {
    //1. Utilisation d'un bind pour accorder la valeur du slider à la valeur du sonn
    init {
        vue.menuParametre.sliderSon.valueProperty().bindBidirectional(Musique.volumeProperty)
    }
}