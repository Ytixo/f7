// VueMenu.kt
package vue

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.ParallelTransition
import javafx.animation.TranslateTransition
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.util.Duration
import outils.GestionnaireLangue

class VueMenu : BorderPane() {

    // Création des éléments
    val boutonJouer: Button = Button(GestionnaireLangue.texte("menu.jouer"))

    private val boutonQuitter: Button = Button(GestionnaireLangue.texte("menu.quitter"))

    private val bouttonParametre : Button

    private var logoF7: StackPane

    private val imageParametre: ImageView = ImageView("file:src/main/resources/images/logo_parametre.png")

    private val boxTop: StackPane

    private val boxJouer: HBox

    private val boxQuitter: HBox

    init {
        // Création des éléments
        logoF7 = F7NeonLogo2()

        //Configuration des éléments
        imageParametre.isPreserveRatio = true
        imageParametre.fitWidth = 50.0

        bouttonParametre = Button()
        bouttonParametre.graphic = imageParametre
        bouttonParametre.alignment = Pos.TOP_RIGHT
        bouttonParametre.style = "-fx-background-color: transparent;"

        boutonQuitter.styleClass.add("button-custom")

        boutonJouer.setPrefSize(200.0,50.0)
        boutonJouer.styleClass.add("button-custom")

        //Configuration du haut de la fenêtre
        boxTop = StackPane()
        boxTop.children.addAll(logoF7,bouttonParametre)
        StackPane.setAlignment(logoF7, Pos.CENTER)
        StackPane.setAlignment(bouttonParametre, Pos.TOP_RIGHT)
        boxTop.maxWidth = Double.MAX_VALUE
        this.top = boxTop

        boxJouer = HBox()
        boxJouer.alignment = Pos.CENTER
        boxJouer.children.add(boutonJouer)
        boxJouer.opacity = 0.0
        boxJouer.translateY = 30.0

        this.center = boxJouer

        //Configuration du bas de la fenêtre
        boxQuitter = HBox(boutonQuitter)
        boxQuitter.alignment = Pos.CENTER
        boxQuitter.padding = Insets(10.0,10.0,10.0,10.0)
        boxQuitter.opacity = 0.0
        boxQuitter.translateY = 30.0

        this.bottom = boxQuitter

        val apparitionMenu = ParallelTransition(
            creerAnimationApparition(boxJouer, 3.0),
            creerAnimationApparition(boxQuitter, 3.3)
        )

        apparitionMenu.play()
    }

    /*Gemini fonction d'apparition en differe des element */
    private fun creerAnimationApparition(noeud: javafx.scene.Node, delaySeconds: Double): ParallelTransition {
        val fondu = FadeTransition(Duration.seconds(1.0), noeud).apply { toValue = 1.0 }
        val glissement = TranslateTransition(Duration.seconds(1.0), noeud).apply {
            toY = 0.0; interpolator = Interpolator.EASE_OUT
        }

        return ParallelTransition(fondu, glissement).apply {
            delay = Duration.seconds(delaySeconds)
        }
    }

    fun attribuerParametre(controlleur : EventHandler<ActionEvent>){
        bouttonParametre.onAction = controlleur
    }

    fun attribuerJouer(controlleur : EventHandler<ActionEvent>){
        boutonJouer.onAction = controlleur
    }

    fun attribuerQuitter(controlleur : EventHandler<ActionEvent>){
        boutonQuitter.onAction = controlleur
    }

    fun changerLogo( logo: StackPane){
        logoF7 = logo
        /* gemini actualisation de la box */
        boxTop.children[0] = logoF7
    }

    fun actualiserTextes() {
        boutonJouer.text = GestionnaireLangue.texte("menu.jouer")
        boutonQuitter.text = GestionnaireLangue.texte("menu.quitter")
    }
}