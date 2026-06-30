import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName


/**
 * Classe de tests unitaires pour la classe [Carte3aLaSuite].
 * * Permet de vérifier les comportements de base de la carte "3 à la suite" :
 * identification, comparTo et toStrings().
 * @author équipe 1-2
 */
class Carte3aLaSuiteTest {

    /**
     * Vérifie qu'une instance de [Carte3aLaSuite] est bien reconnue comme une carte 3 à la suite.
     */
    @Test
    @DisplayName("Doit être identifiée comme une carte 3 à la suite")
    fun CT1_estCarte3aLaSuite() {
        val carte = Carte3aLaSuite()
        assertTrue(carte.estCarte3aLaSuite())
    }

    /**
     * Vérifie qu'aucun autre type de carte (Stop, Bonus, Num, etc.)
     * n'est identifié par erreur comme une carte 3 à la suite.
     */
    @Test
    @DisplayName("Les autres cartes ne doivent pas être des cartes 3 à la suite")
    fun CT2_estCarte3aLaSuite() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )
        for (carte in cartes) {
            assertFalse(carte.estCarte3aLaSuite())
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [Carte3aLaSuite] sont considérées comme égales.
     */
    @Test
    @DisplayName("Deux cartes 3 à la suite doivent être égales")
    fun CT3_compareTo() {
        val carte1 = Carte3aLaSuite()
        val carte2 = Carte3aLaSuite()
        assertEquals(carte1, carte2)
    }

    /**
     * Vérifie qu'aucune carte d'un type différent n'est considérée comme égale
     * à une [Carte3aLaSuite].
     */
    @Test
    @DisplayName("Une carte 3 à la suite doit être différente des autres cartes")
    fun CT4_compareTo() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )

        for (carte1 in cartes) {
            val carte2 = Carte3aLaSuite()
            assertNotEquals(carte1, carte2)
        }
    }

    /**
     * Vérifie qu'une carte [Carte3aLaSuite] est considérée comme inférieure à une carte [CarteStop].
     */

    @Test
    @DisplayName("inferieure pour 3 à la suite")
    fun CT5_compareTo() {
        val carte1 = Carte3aLaSuite()
        val carte2 = CarteStop()
        assertTrue(carte2 > carte1)
    }

    /**
     * Vérifie qu'une carte [Carte3aLaSuite] est considérée comme superieur au autre carte sauf [CarteStop]..
     */
    @Test
    @DisplayName("superieur pour 3 à la suite")
    fun CT6_compareTo() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )

        for (carte1 in cartes) {
            val carte2 = Carte3aLaSuite()
            assertTrue(carte1 < carte2)
        }
    }

    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [Carte3aLaSuite] doit être strictement supérieure aux cartes Num, Bonus, 2nd chance
     * mais strictement inférieure aux cartes Stop.
     */

    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT7_compareTo() {
        val cartes1 = listOf<Carte>(
            CarteNum(12),
            CarteBonusMultiplie(),
            CarteBonusPlus(10),
            Carte2ndeChance()
        )

        val cartes3 = listOf<Carte>(

            CarteStop()

        )


        for (carte1 in cartes1) {
            for (carte3 in cartes3) {
                val carte2 = Carte3aLaSuite()
                assertTrue(carte1< carte2 && carte2 < carte3)
            }

        }
    }
    /**
     * Vérifie la methode toStrings pour une carte 3 à la suite.
     */
    @Test
    @DisplayName("toString() doit renvoyer '[Carte 3 à la suite]'")
    fun CT8_toString() {
        val carte = Carte3aLaSuite()
        assertTrue(carte.toString().isNotEmpty())
        assertEquals("[Carte 3 à la suite]", carte.toString())
    }

}
