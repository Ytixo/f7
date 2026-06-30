// ControleurPartie.kt
package controleur

import iut.info1.flip7.Flip7
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import javafx.animation.PauseTransition
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import vue.VuePartie
import javafx.util.Duration
import outils.GestionnaireLangue

class ControleurPartie(val modele: Flip7, val vue: VuePartie) {

    // Les différentes property à actualiser pour vérifier l'état de la partie
    val etatPartieProperty = SimpleObjectProperty(modele.etatPartie)
    val etatJoueurProperty = SimpleObjectProperty(modele.etatJoueur)
    val joueurCourantProperty = SimpleIntegerProperty(modele.joueurCourant)

    init {
        /**
         * On fait un listener pour vérifier si l'état de la partie change afin de réaliser les différentes actions
         */
        etatPartieProperty.addListener { _, _, nouvelEtat ->
            when (nouvelEtat) {
                EtatPartie.MANCHE_TERMINEE -> {
                    modele.scoreManche()
                    mettreAJourScores()
                    vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.mancheTerminee"))
                    rafraichir()
                }
                EtatPartie.PARTIE_TERMINEE -> {
                    mettreAJourScores()
                    vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.partieTerminee"))
                    val pause = PauseTransition(Duration.millis(1900.0))
                    pause.play()
                    val scores = modele.joueurs.indices.associate { i ->
                        modele.joueurs[i].donneNom() to modele.score[i] // Utilisation de l'ia pour adapter la map score en fonction du nombre de joueurs
                    }
                    vue.afficherFin(scores)
                }
                EtatPartie.NOUVELLE_MANCHE -> {
                    val joueurEnTete = modele.score.maxBy { it.value }.key
                    val nomEnTete = joueurEnTete.let { modele.joueurs[it].donneNom() }
                    if (modele.score.values.distinct().size == 1) {
                        vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.egalite"))
                    } else {
                        vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.entete").replace("{0}", nomEnTete))
                    }

                    val pause = PauseTransition(Duration.millis(1900.0))
                    pause.setOnFinished {
                        modele.nouvelleManche()
                        vue.majDefausse(modele.defausse[modele.defausse.size - 1], modele.defausse.size)
                        vue.majCarteTiree(null)
                        vue.majMain(modele.main)
                        rafraichir()
                    }
                    pause.play()
                }
                else -> {}
            }
        }

        /**
         * On fait un listener pour vérifier si le joueurCourant à changé afin d'afficher qui doit jouer.
         */
        joueurCourantProperty.addListener { _, _, nouveau ->
            val nomTour = modele.joueurs[nouveau.toInt()].donneNom()
            vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.tour").replace("{0}", nomTour))
        }

        /**
         * On fait un listener pour vérifier si l'état du Joueur change afin de réaliser les différentes actions
         */
        etatJoueurProperty.addListener { _, ancienEtat, nouvelEtat ->
            val joueurPerdu = nouvelEtat.entries.firstOrNull { (id, etat) ->
                etat == EtatJoueur.PERDU && ancienEtat[id] != EtatJoueur.PERDU
            }?.key

            if (joueurPerdu != null) {
                val nomPerdu = modele.joueurs[joueurPerdu].donneNom()
                vue.afficherBandeauPerso(GestionnaireLangue.texte("partie.controleur.perdu").replace("{0}", nomPerdu))
                val pause = PauseTransition(Duration.millis(1900.0))
                pause.play()
            }

            mettreAJourDisponibiliteControles()
        }
    }

    /**
     * Actualise-les propriétés en fonction du modele
     */
    fun rafraichir() {
        if (modele.joueurCourant != joueurCourantProperty.get()) {
            joueurCourantProperty.set(modele.joueurCourant)
        }

        etatJoueurProperty.set(modele.etatJoueur.toMap())
        etatPartieProperty.set(modele.etatPartie)
    }

    /**
     * Rafraichît le score en fonction du nombre de joueurs
     */
    private fun mettreAJourScores() {
        for (i in 0 until modele.nbJoueurs) {
            vue.majScore(i, modele.score[i])
        }
    }

    /**
     * Bloque ou non les contrôle du joueur en fonction de si oui ou non, il peut jouer
     */
    private fun mettreAJourDisponibiliteControles() {
        val etatCourant = modele.etatJoueur[modele.joueurCourant]

        val peutJouer = etatCourant == EtatJoueur.JOUE_ENCORE
        vue.boutonStop.isDisable = !peutJouer
        vue.imagePioche.isDisable = !peutJouer
    }
}