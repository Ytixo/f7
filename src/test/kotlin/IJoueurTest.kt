import iut.info1.flip7.IJoueur
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Classe de tests unitaires pour les interfaces IJoueur.
 * * Permet de vérifier que les interfaces Ijoueur ont bien la méthode toString().
 * @author équipe 1-2
 */
class IJoueurTest {

    @Test
    fun CT1_IJoueur() {
        assertTrue(
            IJoueur::class.members.any {
                it.name == "toString"
            }
        )
    }
}