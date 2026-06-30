import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows


/**
 * Classe de tests unitaires pour la classe [CarteBonusPlusTest].
 * * Permet de vérifier les comportements de base de la carte "Bonus plus" :
 * identification, comparTo et toStrings().
 *  @author équipe 1-2
 */

class CarteBonusPlusTest {

    /**
     * Vérifie qu'une instance de [CarteBonusPlusTest] est bien reconnue comme une carte Bonus plus.
     */
    @Test
    @DisplayName("Doit être identifiée comme une carte Bonus Plus")

    fun CT1_TestCarteBonusPlus() {
        for (i in listOf(2,4,6,8,10)){
            val carte = CarteBonusPlus(i)
            assertTrue(carte.estCarteBonusPlus())
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur fausse
     */
    @Test
    @DisplayName("Carte Bonus Plus avec une valeur fausse")
    fun CT2_TestCarteBonusPlus() {
        for (i in listOf(3,5,7,9,11)){
            assertThrows<IllegalArgumentException> {
                CarteBonusPlus(i)
            }
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur fausse
     */
    @Test
    @DisplayName("Carte Bonus Plus avec une valeur fausse 1 ou 0")
    fun CT3_TestCarteBonusPlus() {
        for (i in listOf(1,0)){
            assertThrows<IllegalArgumentException> {
                CarteBonusPlus(i)
            }
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur négative
     */
    @Test
    @DisplayName("Carte Bonus Plus avec une valeur négative")
    fun CT4_TestCarteBonusPlus() {
        for (i in listOf(-1,-10,-100,-1000,-10000)){
            assertThrows<IllegalArgumentException> {
                CarteBonusPlus(i)
            }
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur négative
     */
    @Test
    @DisplayName("Carte Bonus Plus avec une valeur négative")
    fun CT5_TestCarteBonusPlus() {
        for (i in listOf(-2,-4,-6,-8,-10)){
            assertThrows<IllegalArgumentException> {
                CarteBonusPlus(i)
            }
        }
    }

    /**
     * Vérifie qu'un Entier maximumum ou minimum
     * ne peut être une valeur de Carte Bonus Plus.
     */
    @Test
    fun CT6_TestCarteBonusPlus() {
        for (i in listOf(Int.MIN_VALUE, Int.MAX_VALUE)){
            assertThrows<IllegalArgumentException> {
                CarteBonusPlus(i)
            }
        }
    }

    /**
     * Vérifie qu'aucun autre type de carte (Stop, Num, 2nd Chance, etc.)
     * n'est identifié par erreur comme une carte Bonus Plus.
     */

    @Test
    @DisplayName("Les autres cartes ne doivent pas être des cartes Bonus Plus")
    fun CT7_estCarteBonusPlus() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            CarteStop(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteNum(8)
        )
        for (carte in cartes) {
            assertFalse(carte.estCarteBonusPlus())
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [CarteBonusPlus] avec la même valeur sont considérées comme égales.
     */
    @Test
    @DisplayName("Deux cartes Bonus plus avec la même valeur doivent être égales")
    fun CT8_compareTo() {
        val carteA = CarteBonusPlus(4)
        val carteB = CarteBonusPlus(4)

        assertEquals(carteA, carteB)

    }

    /**
     * Vérification que pour chaque autre type de carte, une carte [CarteBonusPlus] n'est pas égale aux autres
     */
    @Test
    @DisplayName("Une carte Bonus Plus n'est pas égale aux autres types de cartes")
    fun CT9_compareTo() {

        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteNum(8),
            CarteStop()
        )

        for (carte1 in cartes) {
            val carte2 = CarteBonusPlus(8)
            assertNotEquals(carte1, carte2)   //Vérification que pour chaque autre type de carte, une carte numéro n'est pas égale aux autres
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [CarteBonusPlusTest] avec des valeurs différentes ne sont pas considérées comme égales.
     */
    @Test
    @DisplayName("Deux cartes Bonus plus avec des valeurs différentes ne doivent pas être égales")
    fun CT10_compareTo() {
        for (i in listOf(2,4,6,8)){
            val carteA = CarteBonusPlus(i)
            val carteB = CarteBonusPlus(i+2)
            assertTrue(carteA<carteB)
        }
    }

    /**
     * Vérifie qu'une carte [CarteBonusPlus] est toujours supérieure à une carte [CarteNum] avec la même valeur.
     */
    @Test
    fun CT11_compareTo() {
        val carteA = CarteBonusPlus(2)
        val carteB = CarteNum(2)
        assertTrue(carteB<carteA)
    }

    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [CarteBonusPlus] doit être strictement inferieur aux autres cartesaux autres cartes sauf carte num

     */
    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT12_compareTo() {
        val cartes1 = listOf<Carte>(
            CarteNum(12),
            CarteBonusMultiplie()
        )

        val cartes3 = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteStop(),
            Carte3aLaSuite()
        )


        for (carte1 in cartes1) {
            for (carte3 in cartes3) {
                val carte2 = CarteBonusPlus(10)
                assertTrue(carte1< carte2 && carte2 < carte3)
            }

        }
    }

    /**
     * Vérifie que toString renvoie quelque chose de non vide pour une carte [CarteBonusPlus].
     */
    @Test
    @DisplayName("toString() doit renvoyer une chaîne non vide")
    fun CT13_testToString() {
        val carte = CarteBonusPlus(8)
        val texte = carte.toString()

        assertTrue(texte.isNotEmpty())
    }
    /**
     * Vérifie la methode toStrings pour une carte Bonus Plus .
     */
    @Test
    @DisplayName("toString() doit renvoyer '[Carte Bonus +i] avec i la valeur de la carte'")

    fun CT14_testToString() {
        for (i in listOf(2,4,6,8,10)){
            val carte = CarteBonusPlus(i)
            val texte = carte.toString()
            assertTrue(texte == "[Carte Bonus +${i}]")
        }

    }
}