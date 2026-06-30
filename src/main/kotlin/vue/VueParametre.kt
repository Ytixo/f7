// VueParametre.kt
package vue

import javafx.beans.property.DoubleProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import outils.GestionnaireLangue

class VueParametre : BorderPane() {
    val haut: HBox
    val bas: HBox
    val conteneurCentral: VBox
    val grilleParametres: GridPane
    val quitter: Button
    val btnFermer : Button
    val btnCredit: Button
    var sliderSon : Slider
    var sliderSombre : Slider
    val comboLangue : ComboBox<String>
    val btnInfoJeu : Button

    var grosTitreParametre: Label

    //1. Création des boutons, des Labels et des sliders etc...
    init {
        conteneurCentral = VBox()
        conteneurCentral.alignment = Pos.CENTER
        conteneurCentral.spacing = 40.0
        conteneurCentral.style = "-fx-padding: 40;"

        grosTitreParametre = Label(GestionnaireLangue.texte("parametre.titre"))
        grosTitreParametre.styleClass.add("titre-neon")
        grosTitreParametre.style = "-fx-font-size: 50px; -fx-font-weight: bold;"

        grilleParametres = GridPane()
        grilleParametres.alignment = Pos.CENTER
        grilleParametres.hgap = 25.0
        grilleParametres.vgap = 30.0
        grilleParametres.maxWidth = 600.0

        val passageEnBlanc = ColorAdjust()
        passageEnBlanc.brightness = 1.0

        val logo1 = ImageView("file:src/main/resources/images/logo_son.png")
        logo1.fitWidth = 28.0
        logo1.isPreserveRatio = true
        logo1.effect = passageEnBlanc

        sliderSon = Slider(0.0, 1.0, 0.5)
        sliderSon.maxWidth = Double.MAX_VALUE

        GridPane.setConstraints(logo1, 0, 0)
        GridPane.setConstraints(sliderSon, 1, 0)

        val logo2 = ImageView("file:src/main/resources/images/logo_langue.png")
        logo2.fitWidth = 28.0
        logo2.isPreserveRatio = true
        logo2.effect = passageEnBlanc

        comboLangue = ComboBox<String>()
        comboLangue.items.addAll("Français", "English")
        comboLangue.value = "Français"
        comboLangue.maxWidth = Double.MAX_VALUE

        GridPane.setConstraints(logo2, 0, 1)
        GridPane.setConstraints(comboLangue, 1, 1)

        val logo3 = ImageView("file:src/main/resources/images/logo_mode_sombre.png")
        logo3.fitWidth = 28.0
        logo3.isPreserveRatio = true
        logo3.effect = passageEnBlanc

        sliderSombre = Slider(0.0, 1.0, 0.0)
        sliderSombre.maxWidth = Double.MAX_VALUE

        GridPane.setConstraints(logo3, 0, 2)
        GridPane.setConstraints(sliderSombre, 1, 2)

        grilleParametres.children.addAll(logo1, sliderSon, logo2, comboLangue, logo3, sliderSombre)

        val tailleLogo = ColumnConstraints()
        tailleLogo.hgrow = Priority.NEVER

        val tailleControle = ColumnConstraints()
        tailleControle.hgrow = Priority.ALWAYS

        grilleParametres.columnConstraints.addAll(tailleLogo,tailleControle)

        conteneurCentral.children.addAll(grosTitreParametre, grilleParametres)

        bas = HBox()
        bas.alignment = Pos.CENTER
        bas.spacing = 40.0
        bas.style = "-fx-padding: 20;"

        btnFermer = Button(GestionnaireLangue.texte(("parametre.boutton.reinitialisé")))
        btnFermer.styleClass.add("button-custom")

        quitter = Button(GestionnaireLangue.texte(("parametre.boutton.valider")))
        quitter.styleClass.add("button-custom")

        btnCredit = Button(GestionnaireLangue.texte(("parametre.boutton.credit")))
        btnCredit.styleClass.add("button-custom")

        btnInfoJeu = Button(GestionnaireLangue.texte("parametre.boutton.regle"))
        btnInfoJeu.styleClass.add("button-custom")

        haut = HBox()
        haut.alignment = Pos.CENTER
        haut.children.addAll(btnCredit,btnInfoJeu)
        haut.style = "-fx-padding: 15;"
        haut.spacing= 40.0

        bas.children.addAll(btnFermer, quitter)

        this.top = haut
        this.center = conteneurCentral
        this.bottom = bas
        this.style = "-fx-background-color: #0d0d0d;"
    }

    //2.Crétion des fonctions pour accorder les buttons etc... aux controleurs
    fun attribuerActionRetour( controlleur : EventHandler<ActionEvent>){
        btnFermer.onAction = controlleur
    }

    fun attribuerQuitter(controlleur : EventHandler<ActionEvent>){
        quitter.onAction = controlleur
    }
    fun attribuerActionCredit(controlleur: EventHandler<ActionEvent>) {
        btnCredit.onAction = controlleur
    }
    fun attribuerActionRegle(controlleur: EventHandler<ActionEvent>){
        btnInfoJeu.onAction = controlleur
    }
    fun attribuerChangementLangue(controleur: EventHandler<ActionEvent>) {
        comboLangue.onAction = controleur
    }
    fun reinitialiser() {
        this.sliderSon.value = 1.0
        this.sliderSombre.value = 0.0
        this.comboLangue.value = "Français"
    }
    fun actualiserTextes() {
        grosTitreParametre.text = GestionnaireLangue.texte("parametre.titre")
        btnFermer.text = GestionnaireLangue.texte("parametre.boutton.reinitialisé")
        quitter.text = GestionnaireLangue.texte("parametre.boutton.valider")
        btnCredit.text = GestionnaireLangue.texte("parametre.boutton.credit")
        btnInfoJeu.text = GestionnaireLangue.texte("parametre.boutton.regle")
    }
    fun sombreProperty(): DoubleProperty = sliderSombre.valueProperty()
}