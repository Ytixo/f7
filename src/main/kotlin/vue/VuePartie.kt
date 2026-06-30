// VuePartie.kt
package vue

import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import javafx.animation.FadeTransition
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.util.Duration
import javafx.animation.Interpolator
import javafx.animation.PauseTransition
import javafx.animation.SequentialTransition
import javafx.animation.TranslateTransition
import javafx.animation.ParallelTransition
import javafx.event.ActionEvent
import outils.GestionnaireLangue

class VuePartie : StackPane() {

    // La vuePartie est un stackPane qui contient le borderPane pour afficher le bandeau central
    private val borderPane = BorderPane()
    private var nbJoueurs = 4

    // La vue des résultats est créé et actualisé dans la vuePartie
    val vueFin = VueFin()

    private var scoreObjectif = 0
    private val nomsJoueursMap = mutableMapOf<Int, String>()
    private val scoresJoueursMap = mutableMapOf<Int, Int?>()

    /**
     * Eléments pour afficher les cartes, le pseudo et le score
     */
    val haut: HBox = HBox(10.0) // spacing de 10.0
    val bas: HBox = HBox(10.0)
    val gauche: VBox = VBox(10.0)
    val droite: VBox = VBox(10.0)
    val centre: GridPane = GridPane()
    val centreBorder: BorderPane = BorderPane()

    // Le haut et bas sont des stackpane pour placer les boutons Paramètre ou Stop
    private val hautWrapper = StackPane(haut)
    private val basWrapper = StackPane(bas)

    /**
     * Tous les éléments qui seront ensuite ajoutés dans les placements.
     */
    val scoreHaut = Label(GestionnaireLangue.texte("partie.score") + "0")
    val scoreBas = Label(GestionnaireLangue.texte("partie.score") + "0")
    val scoreGauche = Label(GestionnaireLangue.texte("partie.score") + "0")
    val scoreDroite = Label(GestionnaireLangue.texte("partie.score") + "0")
    val nomHaut = Label("")
    val nomBas = Label("")
    val nomGauche = Label("")
    val nomDroite = Label("")
    val scoreAAtteindre = Label(GestionnaireLangue.texte("partie.scoreAAtteindre") + "0")

    // Le bandeau au centre de l'écran pour informer au joueur les informations du jeu.
    val bandeau = Label("").apply {
        styleClass.add("bandeau-joueur")
        isVisible = false
        maxWidth = Double.MAX_VALUE
        alignment = Pos.CENTER
        prefHeight = 80.0
    }

    /**
     * Les boutons stockés dans les stackPane haut et bas
     */
    private val imageParametre = ImageView(Image("file:src/main/resources/images/logo_parametre.png", 50.0, 50.0, true, true))
    val boutonParametres = Button()
    val boutonStop = Button(GestionnaireLangue.texte("partie.bouton.stop"))

    /**
     * Tous les éléments affiché au centre.
     */
    val imageDefausse = ImageView()
    val imagePioche = ImageView(Image("file:src/main/resources/images/Carte_Dos.png", 130.0, 180.0, true, true))
    val imageCarteTiree = ImageView()

    init {
        //Seul le centre est construit sans paramètre.
        construireCentre()

        // Placage des éléments aux positions correspondants du borderPane
        borderPane.top = hautWrapper
        borderPane.bottom = basWrapper
        borderPane.left = gauche
        borderPane.right = droite
        borderPane.center = centreBorder
        centreBorder.center = centre

        haut.maxWidthProperty().bind(this.widthProperty().subtract(400.0))
        bas.maxWidthProperty().bind(this.widthProperty().subtract(400.0))

        //Mettre l'image paramètre au bouton
        boutonParametres.graphic = imageParametre

        // Ajout du borderPane global
        children.add(borderPane)

        // Ajout du bandeau central
        children.add(bandeau)
        bandeau.prefWidthProperty().bind(widthProperty())
        setAlignment(bandeau, Pos.CENTER)
    }

    /**
     * Reinitialise la vue pour refaire une nouvelle manche
     */
    fun reinitialiser() {
        haut.children.clear()
        bas.children.clear()
        gauche.children.clear()
        droite.children.clear()
        haut.styleClass.clear()
        bas.styleClass.clear()
        gauche.styleClass.clear()
        droite.styleClass.clear()
        borderPane.left = null
        borderPane.right = null

        scoreHaut.text = GestionnaireLangue.texte("partie.score") + "0"
        scoreBas.text = GestionnaireLangue.texte("partie.score") + "0"
        scoreGauche.text = GestionnaireLangue.texte("partie.score") + "0"
        scoreDroite.text = GestionnaireLangue.texte("partie.score") + "0"

        scoresJoueursMap.clear()
    }

    /**
     * Permet de construire toutes les zones de jeu en fonction du nombre de joueurs et de leur pseudo.
     */
    fun construire(nbJoueurs: Int, joueurs : List<IJoueur>) {
        this.nbJoueurs = nbJoueurs
        nomsJoueursMap[0] = joueurs[0].donneNom()
        nomsJoueursMap[1] = joueurs[1].donneNom()
        if (nbJoueurs >= 3) nomsJoueursMap[2] = joueurs[2].donneNom()
        if (nbJoueurs >= 4) nomsJoueursMap[3] = joueurs[3].donneNom()

        val constructions = listOf(
            { construireBas(nomsJoueursMap[0]!!) },
            { construireHaut(nomsJoueursMap[1]!!) },
            { construireGauche(nomsJoueursMap[2] ?: "") },
            { construireDroite(nomsJoueursMap[3] ?: "") },
        )

        borderPane.left = null
        borderPane.right = null

        for (i in 0 until nbJoueurs) {
            constructions[i]()
        }

        if (nbJoueurs >= 3) borderPane.left = gauche
        if (nbJoueurs >= 4) borderPane.right = droite
    }

    /**
     * Construit la zone avec leurs éléments correspondants et mise en place du style.css
     */
    private fun construireHaut(nom : String) {
        haut.alignment = Pos.CENTER
        nomHaut.text = GestionnaireLangue.texte("partie.joueur") + nom + "      "
        haut.padding = Insets(10.0)
        haut.minHeight = 200.0
        haut.apply { styleClass.add("couleur-joueur2") }
        scoreHaut.apply { styleClass.add("scorePseudo") }
        nomHaut.apply { styleClass.add("scorePseudo") }
        boutonParametres.style = "-fx-background-color: transparent;"

        hautWrapper.children.add(boutonParametres)
        setAlignment(boutonParametres, Pos.TOP_RIGHT)
        setMargin(boutonParametres, Insets(10.0))
    }

    /**
     * Construit la zone avec leurs éléments correspondants et mise en place du style.css
     */
    private fun construireBas(nom : String) {
        bas.alignment = Pos.CENTER
        nomBas.text = GestionnaireLangue.texte("partie.joueur") + nom + "      "
        bas.padding = Insets(10.0)
        bas.minHeight = 200.0
        bas.apply { styleClass.add("couleur-joueur1") }
        scoreBas.apply { styleClass.add("scorePseudo") }
        nomBas.apply { styleClass.add("scorePseudo") }

        basWrapper.children.add(boutonStop)
        setAlignment(boutonStop, Pos.CENTER_LEFT)
        setMargin(boutonStop, Insets(10.0))
        boutonStop.apply { styleClass.add("button-custom") }
    }

    /**
     * Construit la zone avec leurs éléments correspondants et mise en place du style.css
     */
    private fun construireGauche(nom : String) {
        gauche.alignment = Pos.CENTER
        nomGauche.text = GestionnaireLangue.texte("partie.joueur") + nom + "      "
        gauche.padding = Insets(10.0)
        gauche.minWidth = 200.0
        scoreGauche.apply { styleClass.add("scorePseudo") }
        nomGauche.apply { styleClass.add("scorePseudo") }
        gauche.apply { styleClass.add("couleur-joueur3") }
    }

    /**
     * Construit la zone avec leurs éléments correspondants et mise en place du style.css
     */
    private fun construireDroite(nom : String) {
        droite.alignment = Pos.CENTER
        nomDroite.text = GestionnaireLangue.texte("partie.joueur") + nom + "      "
        droite.padding = Insets(10.0)
        droite.minWidth = 200.0
        droite.apply { styleClass.add("couleur-joueur4") }
        scoreDroite.apply { styleClass.add("scorePseudo") }
        nomDroite.apply { styleClass.add("scorePseudo") }
    }

    /**
     * Construit la zone avec leurs éléments correspondants et mise en place du style.css
     */
    private fun construireCentre() {
        centre.alignment = Pos.TOP_CENTER
        centre.hgap = 15.0
        centre.vgap = 15.0
        centre.padding = Insets(10.0)
        val infoHaut = HBox(nomHaut, scoreHaut.apply { style= "-fx-text-fill: white;" }).apply { alignment = Pos.CENTER }
        val infoBas = HBox(nomBas,  scoreBas.apply { style= "-fx-text-fill: white;" }).apply { alignment = Pos.CENTER }

        val infoGauche = HBox(nomGauche, scoreGauche.apply { style= "-fx-text-fill: white;" }).apply { rotate = 90.0 }
        val infoDroite = HBox(nomDroite, scoreDroite.apply { style= "-fx-text-fill: white;" }).apply { rotate = -90.0  }

        val groupeGauche = Group(infoGauche)
        val groupeDroite = Group(infoDroite)
        BorderPane.setAlignment(groupeGauche, Pos.CENTER)
        BorderPane.setAlignment(groupeDroite, Pos.CENTER)

        centreBorder.top = infoHaut
        centreBorder.bottom = infoBas
        centreBorder.left = groupeGauche
        centreBorder.right = groupeDroite

        centre.add(scoreAAtteindre,1 , 0)
        centre.add(imageDefausse, 0, 1)
        centre.add(imagePioche, 2, 1)
        centre.add(imageCarteTiree.apply { alignment = Pos.CENTER }, 1, 2)

        centre.styleClass.add("couleur-centre")
        scoreAAtteindre.apply { styleClass.add("label-texte") }
    }

    /**
     * Permet d'appeler le bon nom de Fichier d'image en fonction de la carte.
     */
    private fun nomFichierCarte(carte: Carte): String = when {
        carte.estCarteBonusMultiplie() -> "Carte_Double.png"
        carte.estCarte2ndeChance()     -> "Carte_2ndC.png"
        carte.estCarteBonusPlus()      -> "Carte_+${carte.valeur}.png"
        carte.estCarteStop()           -> "Carte_Freeze.png"
        carte.estCarte3aLaSuite()      -> "Carte_3C.png"
        else                           -> "Carte_${carte.valeur}.png"
    }

    /**
     * Affiche les cartes dans la zone en paramètre en adaptant la taille et en les faisant tourner pour les zones sur les côtés.
     */
    fun afficherCarteJoueur(cartes: List<Carte>, place: Pane) {
        place.children.removeIf { it !is Label } // Supprime toutes les cartes sauf le score et le pseudo

        val nbCartes = cartes.size
        val largeur = when {
            nbCartes <= 3 -> 110.0
            nbCartes <= 5 -> 70.0
            nbCartes <= 7 -> 50.0
            else -> 50.0
        }
        val hauteur = largeur * (180.0 / 130.0)

        val imagesCartes = cartes.map { carte ->
            ImageView(Image("file:src/main/resources/images/${nomFichierCarte(carte)}", largeur, hauteur, true, true))
        }

        when (place) {
            gauche -> {
                val cartesGroupees = imagesCartes.map { iv -> iv.rotate = 90.0; Group(iv) as Node }
                place.children.addAll(cartesGroupees)
            }
            droite -> {
                val cartesGroupees = imagesCartes.map { iv -> iv.rotate = -90.0; Group(iv) as Node }
                place.children.addAll(cartesGroupees)
            }
            else -> place.children.addAll(imagesCartes)
        }
    }

    /**
     * Change le score affiché du joueur correspondant
     */
    fun majScore(joueur: Int, score: Int?) {
        scoresJoueursMap[joueur] = score
        val texte = GestionnaireLangue.texte("partie.score") + "${score ?: 0}"
        when (joueur) {
            1 -> scoreHaut.text = texte
            0 -> scoreBas.text = texte
            2 -> scoreGauche.text = texte
            3 -> scoreDroite.text = texte
        }
    }

    /**
     * Permet d'afficher le score à atteindre
     */
    fun majScoreAAtteindre(score: Int) {
        scoreObjectif = score
        scoreAAtteindre.text = GestionnaireLangue.texte("partie.scoreAAtteindre") + score
    }

    /**
     * Une fois qu'une macnche est terminé, la dernirès carte pioché est affiché afin d'indiquer que c'est la défausse.
     */
    fun majDefausse(carte: Carte?, tailleDefausse : Int) {
        if (carte != null && tailleDefausse > 0) {
            imageDefausse.image = chargerImage(carte)
        }
    }

    /**
     * Permet d'afficher la carte qui vient d'être tirée
     */
    fun majCarteTiree(carte: Carte?) {
        if (carte != null) {
            imageCarteTiree.image = chargerImage(carte)
        } else {
            imageCarteTiree.image = null
        }
    }

    /**
     * Met à jour les mains en fonctions des cartes de chaque joueur
     */
    fun majMain(main: Map<Int, List<Carte>>) {
        afficherCarteJoueur(main[0] ?: emptyList(), bas)
        afficherCarteJoueur(main[1] ?: emptyList(), haut)

        if (nbJoueurs >= 3)
            afficherCarteJoueur(main[2] ?: emptyList(), gauche)

        if (nbJoueurs >= 4)
            afficherCarteJoueur(main[3] ?: emptyList(), droite)
    }

    /**
     * Fonction utilitaires pour charger les images en fonction de leur types
     */
    private fun chargerImage(carte: Carte): Image {
        return Image("file:src/main/resources/images/${nomFichierCarte(carte)}", 130.0, 180.0, true, true)
    }

    /**
     * Affiche un bandeau centrale pour indiquer les informations du jeu (Chatgpt à était utilisé pour faire l'animation)
     */
    fun afficherBandeauPerso(texte: String) {
        bandeau.text = texte
        bandeau.isVisible = true

        bandeau.translateY = -150.0
        bandeau.opacity = 0.0

        val slideIn = TranslateTransition(Duration.millis(500.0), bandeau).apply {
            toY = 0.0
            interpolator = Interpolator.EASE_OUT
        }

        val fadeIn = FadeTransition(Duration.millis(500.0), bandeau).apply {
            fromValue = 0.0
            toValue = 1.0
        }

        val apparition = ParallelTransition(slideIn, fadeIn)

        val pause = PauseTransition(Duration.seconds(0.5))

        val slideOut = TranslateTransition(Duration.millis(400.0), bandeau).apply {
            toY = -150.0
            interpolator = Interpolator.EASE_IN
        }

        val fadeOut = FadeTransition(Duration.millis(400.0), bandeau).apply {
            fromValue = 1.0
            toValue = 0.0
        }

        val disparition = ParallelTransition(slideOut, fadeOut)

        disparition.setOnFinished {
            bandeau.isVisible = false
        }


        SequentialTransition(apparition, pause, disparition).apply {
            delay = Duration.seconds(0.5)
            play()
        }
    }

    /**
     * Permet de mettre à jour les scores de la vueFin
     */
    fun afficherFin(scores: Map<String, Int?>) {
        vueFin.majScores(scores)
        if (!children.contains(vueFin)) {
            children.add(vueFin)
        }
    }

    /**
     * Affecter le controleur au bouton de la pioche
     */
    fun fixeControleurPioche(controleur : EventHandler<MouseEvent>) {
        imagePioche.onMouseClicked = controleur
    }

    /**
     * Affecter le controleur au bouton des paramètres.
     */
    fun fixeControleurParametre(controleur : EventHandler<ActionEvent>) {
        boutonParametres.onAction = controleur
    }

    fun actualiserTextes() {
        boutonStop.text = GestionnaireLangue.texte("partie.bouton.stop")

        majScoreAAtteindre(scoreObjectif)

        for ((joueur, score) in scoresJoueursMap) {
            majScore(joueur, score)
        }

        if (nomsJoueursMap.containsKey(1)) nomHaut.text = GestionnaireLangue.texte("partie.joueur") + nomsJoueursMap[1] + "      "
        if (nomsJoueursMap.containsKey(0)) nomBas.text = GestionnaireLangue.texte("partie.joueur") + nomsJoueursMap[0] + "      "
        if (nomsJoueursMap.containsKey(2)) nomGauche.text = GestionnaireLangue.texte("partie.joueur") + nomsJoueursMap[2] + "      "
        if (nomsJoueursMap.containsKey(3)) nomDroite.text = GestionnaireLangue.texte("partie.joueur") + nomsJoueursMap[3] + "      "
    }

}