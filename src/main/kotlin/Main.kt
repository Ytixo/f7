import outils.Musique
import controleur.ControleurInitLancerPartie
import controleur.ControleurBoutonParametre
import controleur.ControleurBoutonReinitialiserParametre
import controleur.ControleurCredit
import controleur.ControleurQuitter
import controleur.ControleurQuitterLesParametre
import controleur.ControleurLuminosite
import controleur.ControleurRegle
import controleur.ControleurSon
import controleur.ControleurVersMenuInit
import controleur.ControleurVersMenuMain
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.stage.Stage
import model.CreationPartie
import vue.MainVue
import javafx.stage.Screen
import outils.GestionnaireLangue


class Main: Application() {
    override fun start(primaryStage: Stage) {

        Musique.playBackgroundMusic("musique_fond")

        Font.loadFont(javaClass.getResourceAsStream("/BungeeInline-Regular.ttf"), 10.0)
        Font.loadFont(javaClass.getResourceAsStream("/Monoton-Regular.ttf"), 60.0)


        val vue = MainVue()

        val largeur = Screen.getPrimary().bounds.width
        val hauteur = Screen.getPrimary().bounds.height
        val largeurScene : Double
        val hauteurScene : Double
        val ratio = 1280.0 / 1050.0
        if (largeur / hauteur > ratio) {
            hauteurScene = hauteur * 0.9 // marge de 10% pour qu'elle ne soit pas collé aux bords
            largeurScene = hauteurScene * ratio
        } else {
            largeurScene = largeur * 0.9 // marge de 10% pour qu'elle ne soit pas collé aux bords
            hauteurScene = largeurScene / ratio
        }

        val scene = Scene(vue, largeurScene, hauteurScene)

        scene.stylesheets.add(javaClass.getResource("/Style.css")?.toExternalForm())



        vue.menuInit.attribuerActionRetour(ControleurVersMenuMain(vue))
        vue.menuInit.attribuerActionJouer(ControleurInitLancerPartie(vue,CreationPartie()))

        vue.menuPartie.fixeControleurParametre(ControleurBoutonParametre(vue))

        vue.menuMain.attribuerParametre(ControleurBoutonParametre(vue))
        vue.menuMain.attribuerJouer(ControleurVersMenuInit(vue))
        vue.menuMain.attribuerQuitter(ControleurQuitter(vue))

        vue.menuPartie.vueFin.attribuerMenu(ControleurVersMenuInit(vue))
        vue.menuPartie.vueFin.attribuerQuitter(ControleurQuitter(vue))

        ControleurLuminosite(vue, vue.menuParametre)
        ControleurSon(vue)


        vue.menuParametre.attribuerActionRetour(ControleurBoutonReinitialiserParametre(vue,vue.menuParametre))
        vue.menuParametre.attribuerQuitter(ControleurQuitter(vue))

        vue.menuParametre.attribuerActionCredit(ControleurCredit())
        vue.menuParametre.attribuerActionRegle(ControleurRegle())
        vue.menuParametre.attribuerQuitter(ControleurVersMenuMain(vue))
        vue.menuParametre.attribuerChangementLangue(EventHandler<ActionEvent> {
            val langueChoisie = vue.menuParametre.comboLangue.value
            GestionnaireLangue.changerLangue(langueChoisie)
            vue.actualiserToutesLesLangues()

        })


        primaryStage.scene = scene
        primaryStage.title="Flip7"
        primaryStage.show()
    }
}

fun main(){
    Application.launch(Main::class.java)
}