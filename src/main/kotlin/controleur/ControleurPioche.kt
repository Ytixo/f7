// ControleurPioche.kt
package controleur

import iut.info1.flip7.Flip7
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import javafx.event.EventHandler
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.ChoiceDialog
import javafx.scene.input.MouseEvent
import outils.GestionnaireLangue
import vue.VuePartie

class ControleurPioche(val modele: Flip7, val vue: VuePartie, val controleurPartie: ControleurPartie) : EventHandler<MouseEvent> {

    /**
     * Si une carte est pioché, on met à jour la vue, vérifie son type afin de demander la cible pour les cartes Stop et 3 à la suite
     */
    override fun handle(p0: MouseEvent) {
        val cartePiochee = modele.joueurCourantPiocheUneCarte()
        vue.majCarteTiree(cartePiochee)

        when (modele.etatPartie) {
            EtatPartie.ATTENTE_CIBLE_STOP -> choisirCible { index ->
                modele.joueurCourantCibleStop(cartePiochee, index)
                controleurPartie.rafraichir()
            }
            EtatPartie.ATTENTE_CIBLE_3SUITE -> choisirCible { index ->
                modele.joueurCourantCible3aLaSuite(cartePiochee, index)
                controleurPartie.rafraichir()
            }
            else -> controleurPartie.rafraichir()
        }
        vue.majMain(modele.main)
    }

    /**
     * Permet de choisir la cible dans les joueurs actifs avec un bouton Aléatoire qui permet de choisir aléatoirement
     */
    private fun choisirCible(onChoix: (Int) -> Unit) {
        val joueursActifs = modele.joueurs.filterIndexed { index, _ ->
            modele.etatJoueur[index] == EtatJoueur.JOUE_ENCORE
        }

        val nomsActifs = joueursActifs.map { it.donneNom() }
        val premierNom = nomsActifs.first()

        val dialog = ChoiceDialog(premierNom, nomsActifs)
        dialog.title = GestionnaireLangue.texte("pioche.controleur.cible.titre")
        dialog.headerText = GestionnaireLangue.texte("pioche.controleur.cible.header")
        dialog.dialogPane.buttonTypes.remove(ButtonType.CANCEL)

        val boutonAleatoire = ButtonType(GestionnaireLangue.texte("pioche.controleur.cible.aleatoire"), ButtonBar.ButtonData.LEFT)
        dialog.dialogPane.buttonTypes.add(boutonAleatoire)

        dialog.setResultConverter { boutonClique ->
            when (boutonClique) {
                ButtonType.OK -> dialog.selectedItem
                boutonAleatoire -> nomsActifs.random()
                else -> null
            }
        }

        dialog.showAndWait().ifPresent { nom ->
            val index = modele.joueurs.indexOfFirst { it.donneNom() == nom }
            onChoix(index)
        }
    }
}