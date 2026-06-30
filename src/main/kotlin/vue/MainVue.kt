package vue

import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.animation.FadeTransition
import javafx.scene.Node
import javafx.scene.layout.StackPane
import javafx.util.Duration

class MainVue : BorderPane() {
    var menuInit: VueInit


    // Initialisation des menu de base
    var menuParametre: VueParametre = VueParametre()
    var menuMain: VueMenu
    var menuPartie: VuePartie = VuePartie()
    var ancienneVue: Node?

    init {
        this.ancienneVue = null
        this.menuInit = VueInit()
        this.menuMain = VueMenu()
        changerVue(menuMain)



    }

    // Source https://www.youtube.com/watch?v=rMQrXSYHl8w
    // Gemini en aide pour passer en Kotlin (car je trouvais pas de tuto adapté) et aide pour le binding de la
    fun changerVue(nouvelleVue: Node) {
        val vueActuelle = this.center
        if (nouvelleVue != vueActuelle) {
            animation(nouvelleVue)
        }

    }

    fun animation(nouvelleVue: Node) {
        ancienneVue = this.center
        if (ancienneVue == null) {
            this.center = nouvelleVue
            return
        }

        val voileNoir = Rectangle().apply {
            fill = Color.BLACK
            opacity = 0.0

            widthProperty().bind(this@MainVue.widthProperty())
            heightProperty().bind(this@MainVue.heightProperty())
        }

        val conteneurTransition = StackPane(ancienneVue, voileNoir)
        this.center = conteneurTransition

        val fonduNoir = FadeTransition(Duration.millis(500.0), voileNoir).apply {
            fromValue = 0.0
            toValue = 1.0
        }

        fonduNoir.setOnFinished {

            conteneurTransition.children[0] = nouvelleVue


            val apparitionVue = FadeTransition(Duration.millis(500.0), voileNoir).apply {
                fromValue = 1.0
                toValue = 0.0

                setOnFinished {
                    this@MainVue.center = nouvelleVue
                }
            }

            apparitionVue.play()
        }

        fonduNoir.play()
    }


    fun retourVue() {
        val nouvelleVue = ancienneVue
        ancienneVue = this.center

        if (nouvelleVue != null && nouvelleVue != ancienneVue) {
            animation(nouvelleVue)
        }
    }
    fun actualiserToutesLesLangues() {

        menuMain.actualiserTextes()
        menuParametre.actualiserTextes()
        menuInit.actualiserTextes()
        menuPartie.actualiserTextes()
    }
}
