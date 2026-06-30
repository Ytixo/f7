import iut.info1.flip7.etats.EtatPartie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Classe de tests unitaires pour les Etats Partie.
 * * Permet de vérifier la bonne composition d'états de la classe EtatPartie.
 * @author équipe 1-2
 */
class EtatPartieTest {

    @Test
    fun CT1_EtatPartie() {
        val etats = listOf<EtatPartie>(EtatPartie.ATTENTE_CHOIX_JOUEUR, EtatPartie.ATTENTE_CIBLE_STOP, EtatPartie.ATTENTE_CIBLE_3SUITE,
            EtatPartie.MANCHE_TERMINEE, EtatPartie.NOUVELLE_MANCHE, EtatPartie.PARTIE_TERMINEE)
        assertEquals(etats, EtatPartie.entries)
    }
}