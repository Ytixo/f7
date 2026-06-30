package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Alert
import model.CreationPartie
import vue.MainVue
import outils.GestionnaireLangue

class ControleurInitLancerPartie(val vue: MainVue, mod: CreationPartie): EventHandler<ActionEvent> {

    val model = mod
    /**
     * Lance la partie
     */
    override fun handle(p0: ActionEvent?) {
        val mesJoueurs = vue.menuInit.retourJoueur()
        // Vérification du nombre de joueurs et qu'ils aient tous un nom différent

        if ((mesJoueurs.size < 2 ) || (mesJoueurs.size != mesJoueurs.distinct().size)) {
            val monAlerte = Alert(Alert.AlertType.WARNING)

            monAlerte.title = GestionnaireLangue.texte("init.lancer.erreur.titre")
            monAlerte.headerText = null // Optionnel, pour retirer le sous-titre par défaut
            monAlerte.contentText = GestionnaireLangue.texte("init.lancer.erreur.texte")
            monAlerte.showAndWait()

        } else {
            val fabrique = model
            val scoreFin = vue.menuInit.valeurScore.text.toInt()
            val jeu = fabrique.creerJeu(mesJoueurs, scoreFin)
            vue.menuPartie.vueFin.attribuerRelancer(ControleurRelancer(vue, jeu))
            vue.menuPartie.construire(mesJoueurs.size, jeu.joueurs)
            vue.menuPartie.majScoreAAtteindre(scoreFin)

            val controleurPartie = ControleurPartie(jeu, vue.menuPartie)
            vue.menuPartie.boutonStop.onAction = ControleurBoutonStop(jeu, vue.menuPartie, controleurPartie)
            vue.menuPartie.fixeControleurPioche(ControleurPioche(jeu, vue.menuPartie, controleurPartie))

            vue.changerVue(vue.menuPartie)

            val nomPremierJoueur = jeu.joueurs[0].donneNom()
            val texteBandeau = GestionnaireLangue.texte("init.lancer.bandeau").replace("{0}", nomPremierJoueur)

            vue.menuPartie.afficherBandeauPerso(texteBandeau)
        }
    }
}