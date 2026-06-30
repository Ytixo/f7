package vue

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.scene.control.Label
import javafx.scene.effect.DropShadow
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.util.Duration


/**
 * Permet d'afficher et de réalisé une animation pour le Logo du jeu F7
 * Animation du type extinction de néon
 */
class F7NeonLogoEteintre : StackPane() {

   init {


       // Les couleur du néon
        val tubeOffColor = Color.web("#2a2a2a") // Gris foncé
        val tubeOnColor = Color.web("#ffffff")  // Blanc pur
        val neonGlowColor = Color.web("#00e6ff") // Cyan électrique

       // On place Le Label du Logo
        val myLab = Label("F7")
        myLab.styleClass.add("titre-neon-F7")
        myLab.textFill = tubeOffColor
        this.children.add(myLab)






       // Le shadow permet de donner l'effet néon
       val dropShadow = DropShadow().apply {
           color = neonGlowColor
           radius = 0.0
           spread = 0.3
           offsetX = 0.0
           offsetY = 0.0
       }
       myLab.effect = dropShadow


       //Définition de la TimeLine

        val timeline = Timeline(
            KeyFrame(
                Duration.ZERO,
                KeyValue(dropShadow.radiusProperty(), 0.0, Interpolator.DISCRETE),
                KeyValue(myLab.textFillProperty(), tubeOffColor, Interpolator.DISCRETE)
            ),
            KeyFrame(
                Duration.seconds(0.1),
                KeyValue(dropShadow.radiusProperty(), 80.0, Interpolator.DISCRETE),
                KeyValue(myLab.textFillProperty(), tubeOnColor, Interpolator.DISCRETE)
            ),
            KeyFrame(
                Duration.seconds(0.15),
                KeyValue(dropShadow.radiusProperty(), 0.0, Interpolator.DISCRETE),
                KeyValue(myLab.textFillProperty(), tubeOffColor, Interpolator.DISCRETE)
            ),
            KeyFrame(
                Duration.seconds(0.3),
                KeyValue(dropShadow.radiusProperty(), 40.0, Interpolator.DISCRETE),
                KeyValue(myLab.textFillProperty(), neonGlowColor, Interpolator.DISCRETE)
            ),
            KeyFrame(
                Duration.seconds(0.4),
                KeyValue(dropShadow.radiusProperty(), 0.0, Interpolator.DISCRETE),
                KeyValue(myLab.textFillProperty(), tubeOffColor, Interpolator.DISCRETE)
            ),


            KeyFrame(
                Duration.seconds(0.8),
                KeyValue(dropShadow.radiusProperty(), 0.0),
                KeyValue(myLab.textFillProperty(), tubeOffColor)
            ),
            KeyFrame(
                Duration.seconds(2.5),
                KeyValue(
                    dropShadow.radiusProperty(),
                    60.0,
                    Interpolator.EASE_OUT
                ),
                KeyValue(myLab.textFillProperty(), tubeOnColor, Interpolator.EASE_OUT)
            )
        )

       // Reverse de la Timeline Pour faire l'extinction
       timeline.rate = -1.0
       timeline.playFrom(Duration.seconds(2.5))
       timeline.delay = Duration.seconds(2.0)






    }
}