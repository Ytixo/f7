import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Classe de tests unitaires pour la classe [Carte2ndeChance].
 * * Permet de vérifier les comportements de base de la carte "2nde Chance" :
 * identification, comparTo et toStrings().
 * @author équipe 1-2
 */

class Carte2ndeChanceTest {

    /**
     * Vérifie qu'une instance de [Carte2ndeChance] est bien reconnue comme une carte 2nde chance.
     */
    @Test
    @DisplayName("Doit être identifiée comme une carte 2nde chance")

    fun CT1_estCarte2ndChance() {
        val carte = Carte2ndeChance()
        assertTrue(carte.estCarte2ndeChance())
    }

    /**
     * Vérifie qu'aucun autre type de carte (Stop, Bonus, Num, etc.)
     * n'est identifié par erreur comme une carte 2nde chance.
     */

    @Test
    @DisplayName("Les autres cartes ne doivent pas être des cartes 2nde chance")
    fun CT2_estCarte2ndChance() {
        val cartes = listOf<Carte>(
            Carte3aLaSuite(),
            CarteStop(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )
        for (carte in cartes) {
            assertFalse(carte.estCarte2ndeChance())
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [Carte2ndeChance] sont considérées comme égales.
     */

    @Test
    @DisplayName("Deux cartes 2nde chance doivent être égales")
    fun CT3_compareTo() {
        val carte1 = Carte2ndeChance()
        val carte2 = Carte2ndeChance()
        assertEquals(carte1, carte2)
    }

    /**
     * Vérifie qu'aucune carte d'un type différent n'est   égale
     * à une [Carte2ndeChance].
     */
    @Test
    @DisplayName("Une carte 2nde chance n'est pas égale aux autres types de cartes")
    fun CT4_compareTo() {
        val cartes = listOf<Carte>(
            Carte3aLaSuite(),
            CarteStop(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteNum(8)
        )

        for (carte1 in cartes) {
            val carte2 = Carte2ndeChance()
            assertNotEquals(carte1, carte2)
        }
    }

    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [Carte2ndeChance] doit être strictement supérieure aux cartes Num et Bonus,
     * mais strictement inférieure aux cartes 3 à la Suite et Stop.
     */
    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT5_compareTo() {
        val cartesInferieures = listOf<Carte>(
            CarteNum(12),
            CarteBonusMultiplie(),
            CarteBonusPlus(10)
        )

        val cartesSuperieures = listOf<Carte>(
            Carte3aLaSuite(),
            CarteStop(),
            Carte3aLaSuite()
        )

        for (carteInf in cartesInferieures) {
            for (carteSup in cartesSuperieures) {
                val carte2ndeChance = Carte2ndeChance()
                assertTrue(carteInf < carte2ndeChance && carte2ndeChance < carteSup)
            }
        }
    }

    /**
     * Vérifie la methode toStrings pour une carte 2nde chance .
     */
    @Test
    @DisplayName("toString() doit renvoyer '[Carte 2nde chance]'")
    fun CT6_toString() {
        val carte = Carte2ndeChance()
        assertEquals("[Carte 2nde chance]", carte.toString())
    }
}