import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


/**
 * Classe de tests unitaires pour la classe [CarteBonusMultiplie].
 * * Permet de vérifier les comportements de base de la carte "Bonus Multiplie" :
 * identification, comparTo et toStrings().
 *  @author équipe 1-2
 */
class CarteBonusMultiplicationTest {

    /**
    * Vérification d'une Carte Bonus Multiplie
    */
    @Test
    @DisplayName("Doit être identifié comme une carte Bonus Multiplie")
    fun CT1_estCarteBonusMultiplie() {
        val carte = CarteBonusMultiplie()
        assertTrue(carte.estCarteBonusMultiplie())
    }
    /**
    * Vérification d'une Carte qui n'est pas une carte Bonus Multiplie
     */
    @DisplayName("Les autres cartes ne doivent pas être des cartes Bonus Multiplie")
    @Test
    fun CT2_estCarteBonusMultiplie() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            Carte3aLaSuite(),
            CarteBonusPlus(8),
            CarteNum(8)
        )
        for (carte in cartes) {
            assertFalse(carte.estCarteBonusMultiplie())
        }
    }

    /**
     * Vérifie que deux instance de cartes [CarteBonusMultiplie] sont égale
     */
    @Test
    @DisplayName("Deux carte Bonus Multiplie doivent être égale")
    fun CT3_compareTo() {
        val carte1 = CarteBonusMultiplie()
        val carte2 = CarteBonusMultiplie()
        assertEquals(carte1, carte2)
    }


    /**
     * Vérifie que deux cartes [CarteBonusMultiplie] sont différentes d'une autre carte
     */
    @Test
    @DisplayName("Une carte Bonus Multiplie n'est pas égale aux autres types de cartes")
    fun CT4_compareTo() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            Carte3aLaSuite(),
            CarteBonusPlus(8),
            CarteNum(8)
        )

        for (carte1 in cartes) {
            val carte2 = CarteBonusMultiplie()
            assertNotEquals(carte1, carte2)
        }
    }

    /**
     * Vérifie que la carte multiplie est inferieure à toutes les cartes sauf num
     */

    @Test
    @DisplayName("inférieure carte Bonus Multiplie ")
    fun CT5_compareTo() {
        val carte1 = CarteBonusMultiplie()
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            CarteBonusPlus(8),
            Carte3aLaSuite()

        )

        for (carte2 in cartes) {
            assertTrue(carte1 < carte2)
        }
    }

    /**
     * Vérifie que la carte multiplie est supperieur aux cartes num
     */

    @Test
    @DisplayName("supérieure carte Bonus Multiplie ")
    fun CT6_compareTo() {
        val cartes = listOf<Carte>(

            CarteNum(8),
            CarteNum(10),
            CarteNum(12)
        )

        for (carte1 in cartes) {
            val carte2 = CarteBonusMultiplie()
            assertTrue(carte1 < carte2)
        }
    }


    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [CarteBonusMultiplie] doit être strictement supérieure aux cartes Num,
     * mais strictement inférieure aux autres cartes
     */

    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT7_compareTo() {
        val cartes1 = listOf<Carte>(
            CarteNum(12)
        )

        val cartes3 = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusPlus(8),
            CarteStop(),
            Carte3aLaSuite()
        )


        for (carte1 in cartes1) {
            for (carte3 in cartes3) {
                val carte2 = CarteBonusMultiplie()
                assertTrue(carte1< carte2 && carte2 < carte3)
            }

        }
    }

    /**
     * Vérifie la methode toStrings pour une carte Bonus Multiplie .
     */

    @Test
    @DisplayName("toString() doit renvoyer '[Carte Bonus x2]'")
      fun CT18_toString() {
        val carte = CarteBonusMultiplie()
        val texte = carte.toString()
        assertTrue(texte.isNotEmpty())
        assertEquals("[Carte Bonus x2]", texte)
    }
}