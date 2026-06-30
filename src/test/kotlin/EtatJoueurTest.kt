import iut.info1.flip7.etats.EtatJoueur
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Classe de tests unitaires pour les Etats Joueur.
 * * Permet de vérifier la bonne composition d'états de la classe EtatJoueur.
 * @author équipe 1-2
 */
class EtatJoueurTest {

    @Test
    fun CT1_EtatJoueur() {
        val etats = listOf<EtatJoueur>(EtatJoueur.JOUE_ENCORE, EtatJoueur.STOP, EtatJoueur.PERDU)
        assertEquals(etats, EtatJoueur.entries)
    }
}