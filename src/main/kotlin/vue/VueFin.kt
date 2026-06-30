package vue

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class VueFin : BorderPane() {

    val score : Label
    val stat : Label
    val boutonRelancer: Button
    val boutonQuitter: Button
    val bouttonMenu : Button
    val rootbot : HBox
    val rootcenter : HBox
    val roottop : HBox


    init {

        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE)
        this.styleClass.add("root")

        //BOUTONS
        boutonRelancer = Button("REJOUER")
        boutonQuitter = Button("Quitter")
        bouttonMenu = Button("Menu")


        // ajout des styles
        bouttonMenu.styleClass.add("button-custom")
        boutonQuitter.styleClass.add("button-custom")
        boutonRelancer.styleClass.add("button-custom")
        // taille des bouton
        bouttonMenu.setPrefSize(200.0, 100.0)
        boutonRelancer.setPrefSize(400.0, 100.0)
        boutonQuitter.setPrefSize(200.0, 100.0)

        //TOP
        score = Label("Résultat de la partie")
        score.styleClass.add("titre-neon")
        score.padding = Insets(50.0, 10.0, 50.0, 10.0)

        roottop = HBox(score)
        roottop.alignment = Pos.CENTER

        roottop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE)
        VBox.setVgrow(roottop, Priority.ALWAYS)



        //CENTER
        stat = Label("")
        //ajout du style
        stat.style = "-fx-background-color: rgb(0,0,0,0.5);-fx-text-fill: white ;-fx-font: 30 arial;-fx-background-radius: 20"
        stat.padding = Insets(10.0, 20.0, 10.0, 20.0)


        rootcenter = HBox(stat)
        rootcenter.alignment = Pos.CENTER


        // permet que stat prennent toutes la place restante
        HBox.setHgrow(stat, Priority.ALWAYS)
        rootcenter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE)



        //BOTTOM
        rootbot = HBox(boutonQuitter)
        rootbot.alignment = Pos.CENTER
        rootbot.spacing = 100.0
        rootbot.padding = Insets(10.0, 20.0, 50.0, 20.0)
        rootbot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE)

        //Ajout des root à leur partie dans la fenêtre
        this.top = roottop
        this.center = rootcenter
        this.bottom = rootbot
    }
    //Pour connecter les boutons  à leurs action
    fun attribuerQuitter(controlleur : EventHandler<ActionEvent>){
        boutonQuitter.onAction = controlleur
    }
    fun attribuerRelancer(controlleur : EventHandler<ActionEvent>){
        boutonRelancer.onAction = controlleur
    }
    fun attribuerMenu(controlleur : EventHandler<ActionEvent>){
        bouttonMenu.onAction = controlleur
    }

    fun majScores(scores: Map<String, Int?>) {
        stat.text = toStringStat(scores)
    }

    private fun toStringStat(scores: Map<String, Int?>): String {
        var rep = ""
        val classement = scores.entries.sortedByDescending { it.value }
        for ((i, score) in classement.withIndex()) {
            rep += "${i + 1} - ${score.key} : ${score.value}\n"
        }
        return rep
    }
}