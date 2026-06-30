// VueInit.kt
package vue

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import outils.GestionnaireLangue

/**
 * Class Corespondant a la vue ou est entrer le score ainsi que les noms des joueurs
 */
class VueInit: BorderPane()  {
    private val centre: GridPane
    private val bas: HBox

    private val titre: Label = Label(GestionnaireLangue.texte("init.titre"))

    private val labelScore: Label
    private val labelJoueur1: Label
    private val labelJoueur2: Label
    private val labelJoueur3: Label
    private val labelJoueur4: Label

    private val entrerScore: Slider

    var valeurScore: Label
        private set

    private val entrerJoueur1: TextField
    private val entrerJoueur2: TextField
    private val entrerJoueur3: TextField
    private val entrerJoueur4: TextField

    private val buttonRetour: Button
    private val buttonJouer: Button

    init {
        this.titre.styleClass.add("titre-neon")
        setAlignment(this.titre, Pos.CENTER)
        setMargin(this.titre, Insets(20.0))

        // Label
        this.labelScore = Label(GestionnaireLangue.texte("init.score"))
        this.labelScore.styleClass.add("label-texte")
        this.labelJoueur1 = Label(GestionnaireLangue.texte("init.joueur1"))
        this.labelJoueur1.styleClass.add("label-texte")
        this.labelJoueur2 = Label(GestionnaireLangue.texte("init.joueur2"))
        this.labelJoueur2.styleClass.add("label-texte")
        this.labelJoueur3 = Label(GestionnaireLangue.texte("init.joueur3"))
        this.labelJoueur3.styleClass.add("label-texte")
        this.labelJoueur4 = Label(GestionnaireLangue.texte("init.joueur4"))
        this.labelJoueur4.styleClass.add("label-texte")

        // Slider et Textfields
        this.entrerScore = Slider(50.0, 200.0, 100.0)
        this.entrerScore.setMajorTickUnit(1.0)

        this.valeurScore = Label()
        this.valeurScore.styleClass.add("label-texte")
        this.valeurScore.prefWidth = 80.0
        this.valeurScore.alignment = Pos.CENTER_RIGHT

        //Binding pour afficher la valeur du slider
        this.valeurScore.textProperty().bind(this.entrerScore.valueProperty().asString("%.0f"))

        this.entrerJoueur1 = TextField("")
        this.entrerJoueur1.styleClass.addAll("couleur-joueur1","label-texte")
        this.entrerJoueur2 = TextField("")
        this.entrerJoueur2.styleClass.addAll("couleur-joueur2","label-texte")
        this.entrerJoueur3 = TextField("")
        this.entrerJoueur3.styleClass.addAll("couleur-joueur3","label-texte")
        this.entrerJoueur4 = TextField("")
        this.entrerJoueur4.styleClass.addAll("couleur-joueur4","label-texte")

        val largeurMax = 300.0
        this.entrerScore.maxWidth = largeurMax
        this.entrerJoueur1.maxWidth = largeurMax
        this.entrerJoueur2.maxWidth = largeurMax
        this.entrerJoueur3.maxWidth = largeurMax
        this.entrerJoueur4.maxWidth = largeurMax

        //Buton et attribution
        this.buttonRetour = Button(GestionnaireLangue.texte("init.bouton.retour"))
        this.buttonRetour.styleClass.add("button-custom")
        this.buttonJouer = Button(GestionnaireLangue.texte("init.bouton.jouer"))
        this.buttonJouer.styleClass.add("button-custom")

        this.centre = GridPane()
        this.bas = HBox()

        this.centre.hgap = 15.0
        this.centre.vgap = 10.0
        this.centre.alignment = Pos.CENTER

        // Placement dans le GridPane
        this.centre.add(labelScore, 0, 0)
        this.centre.add(entrerScore, 1, 0)
        this.centre.add(valeurScore, 2, 0)

        this.centre.add(labelJoueur1, 0, 1)
        this.centre.add(entrerJoueur1, 1, 1)

        this.centre.add(labelJoueur2, 0, 2)
        this.centre.add(entrerJoueur2, 1, 2)

        this.centre.add(labelJoueur3, 0, 3)
        this.centre.add(entrerJoueur3, 1, 3)

        this.centre.add(labelJoueur4, 0, 4)
        this.centre.add(entrerJoueur4, 1, 4)

        this.bas.spacing = 50.0
        this.bas.alignment = Pos.CENTER
        this.bas.padding = Insets(20.0)
        this.bas.children.addAll(buttonRetour, buttonJouer)

        // Placement dans la borderPane
        this.top = titre
        this.bottom = bas
        this.center = centre

        val cssUrl = javaClass.getResource("/Style.css")?.toExternalForm()
        if (cssUrl != null) {
            this.stylesheets.add(cssUrl)
        } else {
            println("Attention : Fichier CSS introuvable !")
        }
    }

    /**
     * Permet de réinitialisé les textFields car on ne change pas de vue
     */
    fun reinitialiser() {
        this.entrerScore.value = 100.0
        this.entrerJoueur1.text = ""
        this.entrerJoueur2.text = ""
        this.entrerJoueur3.text = ""
        this.entrerJoueur4.text = ""
    }

    /**
     * retourne les joueurs Qui ont été ecrit dans les text fiel
     */
    fun retourJoueur(): List<String> {
        val nomsJoueurs = mutableListOf<String>()
        if (entrerJoueur1.text != "" ) nomsJoueurs.add(entrerJoueur1.text)
        if (entrerJoueur2.text!= "" ) nomsJoueurs.add(entrerJoueur2.text)
        if (entrerJoueur3.text!= "" ) nomsJoueurs.add(entrerJoueur3.text)
        if (entrerJoueur4.text!= "" ) nomsJoueurs.add(entrerJoueur4.text)
        return nomsJoueurs
    }

    /**
     * Fonctions d'attribution des Bouttons
     */
    fun attribuerActionRetour( controlleur : EventHandler<ActionEvent>){
        buttonRetour.onAction = controlleur
    }

    fun attribuerActionJouer(controlleur : EventHandler<ActionEvent>){
        buttonJouer.onAction = controlleur
    }

    fun actualiserTextes() {
        titre.text = GestionnaireLangue.texte("init.titre")
        labelScore.text = GestionnaireLangue.texte("init.score")
        labelJoueur1.text = GestionnaireLangue.texte("init.joueur1")
        labelJoueur2.text = GestionnaireLangue.texte("init.joueur2")
        labelJoueur3.text = GestionnaireLangue.texte("init.joueur3")
        labelJoueur4.text = GestionnaireLangue.texte("init.joueur4")
        buttonRetour.text = GestionnaireLangue.texte("init.bouton.retour")
        buttonJouer.text = GestionnaireLangue.texte("init.bouton.jouer")
    }
}