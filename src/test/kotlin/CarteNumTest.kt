import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonus
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows


/**
 * Classe de tests unitaires pour la classe [CarteNum].
 * * Permet de vérifier les comportements de base de la carte "2nde Chance" :
 * identification, comparTo et toStrings().
 * @author équipe 1-2
 */

class CarteNumTest {
    /**
     * Vérifie qu'une instance de [CarteNum] est bien reconnue comme une carte num.
     */
    @Test
    @DisplayName("Doit être identifiée comme une carte Num")
    fun CT1_estCarteNum() {
        for (i in 0..12) {
            val carte = CarteNum(i)
            assertTrue(carte.estCarteNum())
        }
    }

    /**
     * Vérifie qu'aucun autre type de carte (Stop, Bonus, 2nd Chance, etc.)
     * n'est identifié par erreur comme une carte Num.m.
     */

    @Test
    @DisplayName("Les autres cartes ne doivent pas être des cartes Num")

    fun CT2_estCarteNum() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteStop()
        )

        for (carte in cartes) {
            assertFalse(carte.estCarteNum())
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur négative
     */
    @Test
    @DisplayName("Carte Num avec une valeur négative")
    fun CT3_estCarteNum() {
        assertThrows<IllegalArgumentException> {
            CarteNum(-1)
        }
    }

    /**
     *  Vérification de la levée d'exception avec une valeur supérieur à 12
     */
    @Test
    @DisplayName("Carte Num avec une valeur supérieur à 12")
    fun CT4_estCarteNum() {
        assertThrows<IllegalArgumentException> {
            CarteNum(13)
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur Max_value
     */
    @Test
    @DisplayName("Carte Num avec une valeur MAX")

    fun CT5_estCarteNum() {
        assertThrows<IllegalArgumentException> {
            CarteNum(kotlin.Int.MAX_VALUE)   //Vérification de la levée d'exception avec l'entier maximum en kotlin
        }
    }

    /**
     * Vérification de la levée d'exception avec une valeur MIN_value
     */
    @Test
    @DisplayName("Carte Num avec une valeur MIN")
    fun CT6_estCarteNum() {
        assertThrows<IllegalArgumentException> {
            CarteNum(kotlin.Int.MIN_VALUE)   //Vérification de la levée d'exception avec l'entier minimum en kotlin
        }
    }


    /**
     * Vérifie que deux cartes distinctes de [CarteNum] avec la même valeur sont considérées comme égales.
     */
    @Test
    @DisplayName("Deux cartes Num avec la même valeur doivent être égales")
    fun CT7_compareTo() {
        for (i in 0..12) {
            val carte1 = CarteNum(i)
            val carte2 = CarteNum(i)
            assertEquals(carte1, carte2)   //Vérification que pour chaque valeur possible les cartes de même valeur soit égale.
        }
    }

    /**
     * Vérifie que deux cartes distinctes de [CarteNum] avec des valeurs différentes ne sont pas considérées comme égales.
     */
    @Test
    @DisplayName("Deux cartes Num avec des valeurs différentes ne doivent pas être égales")
    fun CT8_compareTo() {
        for (i in 0..12) {
            for (j in 0..12) {
                if (i != j) {
                    val carte1 = CarteNum(i)
                    val carte2 = CarteNum(j)
                    assertNotEquals(carte1, carte2)   //Vérification que pour chaque valeur de carte pour chaque carte elles ne soient pas égale aux autres valeurs.
                }
            }
        }
    }

    /**
     * Vérification que pour chaque autre type de carte, une carte [CarteNum] n'est pas égale aux autres
     */
    @Test
    @DisplayName("Une carte Num n'est pas égale aux autres types de cartes")
    fun CT9_compareTo() {
        val cartes = listOf<Carte>(
            Carte2ndeChance(),
            Carte3aLaSuite(),
            CarteBonusMultiplie(),
            CarteBonusPlus(8),
            CarteStop()
        )

        for (carte1 in cartes) {
            val carte2 = CarteNum(8)
            assertNotEquals(carte1, carte2)   //Vérification que pour chaque autre type de carte, une carte numéro n'est pas égale aux autres
        }
    }



    /**
     * Vérifie le respect du bon ordre des cartes selon les règles du jeu.
     * * Une [CarteNum] doit être strictement inferieur aux autres cartes

     */
    @Test
    @DisplayName("Doit respecter l'ordre de comparaison défini dans les règles")
    fun CT11_compareTo() {
        for (i in 0..12) {
            for (j in 0..12) {
                if (i != j) {
                    val carte1 = CarteNum(i)
                    val carte2 = CarteNum(j)
                    if (i > j) {
                        assertTrue(carte1 > carte2)
                    } else {
                        assertTrue(carte2 > carte1)
                    }
                }
            }
        }
    }

    /**
     * Vérifie la methode toStrings pour une carte Num .
     */
    @Test
    @DisplayName("toString() doit renvoyer '[Carte n°i] avec i la valeur de la carte'")

    fun CT12_toString() {
        for (i in 0..12) {
            val carte = CarteNum(i)
            assertTrue(carte.toString().isNotEmpty())
            assertEquals("[Carte n°${i}]", carte.toString())   //Vérification de la bonne écriture avec la méthode .toString()..
        }
    }
}
