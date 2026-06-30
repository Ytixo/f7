import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName


/**
 * Classe de tests unitaires pour la classe [CarteStopTest].
 * * Permet de vérifier les comportements de base de la carte "Stop" :
 * identification, comparTo et toStrings().
 * @author équipe 1-2
 */
class CarteStopTest {

    /**
     * Vérifie qu'une instance de [CarteStopTest] est bien reconnue comme une carte Stop.
     */

    @Test
    @DisplayName("Doit être identifiée comme une carte Stop")
    fun CT1_estCarteStop() {
        val carte = CarteStop()
        assertTrue(carte.estCarteStop())
    }


    /**
     * Vérifie qu'aucun autre type de carte (2nde Chance, 3 à la suite, Bonus, Num, etc.)
     * n'est identifié par erreur comme une carte Stop.
     */
    @Test
    @DisplayName("Les autres cartes ne doivent pas être des cartes Stop")
    fun CT2_estCarteStop() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )
        for (carte in cartes) {
            assertFalse(carte.estCarteStop())
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [CarteStopTest] sont considérées comme égales.
     */

    @Test
    @DisplayName("Deux cartes Stop doivent être égales")
    fun CT3_compareTo() {
        val carte1 = CarteStop()
        val carte2 = CarteStop()
        assertEquals(carte1, carte2)
    }

    /**
     * Vérifie qu'aucune carte d'un type différent n'est  égale
     * à une [CarteStopTest].
     */

    @Test
    @DisplayName("Une carte Stop n'est pas égale aux autres types de cartes")
    fun CT4_compareTo() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )

        for (carte1 in cartes) {
            val carte2 = CarteStop()
            assertNotEquals(carte1, carte2)
        }
    }

    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [CarteStopTest] doit être strictement supérieure à toutes les cartes
     */

    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT5_compareTo() {
        val cartes1 = listOf<Carte>(
            CarteNum(12),
            CarteBonusMultiplie(),
            CarteBonusPlus(10),
            Carte2ndeChance()
        )



        for (carte1 in cartes1) {

            val carte2 = CarteStop()
            assertTrue(carte1< carte2 )


        }
    }

    /**
     * Vérifie la methode toStrings pour une carte 2nde chance .
     */

    @Test
    @DisplayName("toString() doit renvoyer '[Carte Stop]'")
    fun CT6_toString() {
        val carte = CarteStop()
        assertTrue(carte.toString().isNotEmpty())
        assertEquals("[Carte Stop]", carte.toString())
    }
}