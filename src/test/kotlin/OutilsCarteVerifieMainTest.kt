import iut.info1.flip7.cartes.*
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.exceptions.MainInvalideException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.collections.arrayListOf

//utilisation de l'ia pour réoganiser les test

/**
 * Classe de tests unitaires pour la classe [Carte2ndeChance].
 * * Permet de vérifier les comportements de base de la carte "2nde Chance" :
 * identification, comparTo et toStrings().
 * @author équipe 1-2
 */
class OutilsCarteVerifieMainTest {
    val outils = OutilsCarte()

    // ==========================================
    // 1. CARTE BONUS MULTIPLIE
    // ==========================================

    /**
     * Vérifie une main  avec une [CarteBonusMultiplie]
     */
    @Test
    @DisplayName("Check main 1 carte Multiplie")
    fun CT1_verifieMain() {
        val main = listOf<Carte>(CarteBonusMultiplie())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }
    /**
     * Vérifie une main avec deux carte [CarteBonusMultiplie]
     *
     */

    @Test
    @DisplayName("Check main 2 carte Multiplie")
    fun CT2_verifieMain() {
        val main = listOf<Carte>(CarteBonusMultiplie(), CarteBonusMultiplie())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main)}
    }

    // ==========================================
    // 2. CARTE BONUS PLUS
    // ==========================================


    /**
     * Vérifie un jeu avec une [CarteBonusPlus] pour les valeurs 2, 4, 6, 8 et 10
     */
    @Test
    @DisplayName("Check main 1 carte Bonus Plus")
    fun CT3_verifieMain() {
        for (i in listOf(2, 4, 6, 8, 10)) {
            val main = listOf<Carte>(CarteBonusPlus(i))
            assertDoesNotThrow { outils.verifieMainCorrecte(main) }
        }
    }

    /**
     * Vérifie une main avec deux [CarteBonusPlus] de meme valeur pour les valeurs 2, 4, 6, 8 et 10
     */
    @Test
    @DisplayName("Check main 2 carte Bonus Plus de meme valeur")
    fun CT4_verifieMain() {
        for (i in listOf(2, 4, 6, 8, 10)) {
            val main = listOf<Carte>(CarteBonusPlus(i), CarteBonusPlus(i))
            assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
        }
    }

    // ==========================================
    // 3. CARTE NUMÉROS
    // ==========================================


    /**
     * Vérifie une main avec une [CarteNum] pour les valeurs 0 à 12
     */
    @Test
    @DisplayName("Check main 1 carte Num")
  fun CT5_verifieMain() {
        for (i in 0 until 13) {
            val main = listOf<Carte>(CarteNum(i))
            assertDoesNotThrow { outils.verifieMainCorrecte(main) }
        }
    }

    /**
     * Vérifie une main avec trois carte [CarteNum] de même valeur pour les valeurs 0 à 12
     */
    @Test
    @DisplayName("Check main 3 carte Num de meme valeur")
  fun CT6_verifieMain() {
        for (i in 0 until 13) {
            val main = listOf<Carte>(CarteNum(i), CarteNum(i), CarteNum(i))
            assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
        }
    }

    /**
     * Vérifie une main avec deux carte [CarteNum] de même valeur pour la valeurs 0
     */
    @Test
    @DisplayName("Check main 2 carte Num 0")
    fun CT7_verifieMain() {
        val main = listOf<Carte>(CarteNum(0), CarteNum(0))
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec deux carte [CarteNum] de même valeur pour la valeurs 1
     */
    @Test
    @DisplayName("Check main 2 carte Num 1")
    fun CT8_verifieMain() {
        val main = listOf<Carte>(CarteNum(1), CarteNum(1))
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }


    // ==========================================
    // 4. CARTE STOP
    // ==========================================
    /**
     * Vérifie une main avec une [CarteStop]
     */
    @Test
    @DisplayName("Check main 1 carte Stop")
    fun CT9_verifieMain() {
        val main = listOf<Carte>(CarteStop())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec deux [CarteStop]
     */
    @Test
    @DisplayName("Check main 2 carte Stop")
    fun CT10_verifieMain() {
        val main = listOf<Carte>(CarteStop(), CarteStop())
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    // ==========================================
    // 5. CARTE 3 À LA SUITE
    // ==========================================

    /**
     * Vérifie une main avec une [Carte3aLaSuite]
     */

    @Test
    @DisplayName("Check main 1 carte 3 à la suite")
    fun CT11_verifieMain() {
        val main = listOf<Carte>(Carte3aLaSuite())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 2 [Carte3aLaSuite]
     */

    @Test
    @DisplayName("Check main 2 carte 3 à la suite")
    fun CT12_verifieMain() {
        val main = listOf<Carte>(Carte3aLaSuite(), Carte3aLaSuite())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 3 [Carte3aLaSuite]
     */

    @Test
    @DisplayName("Check main 3 carte 3 à la suite")
    fun CT13_verifieMain() {
        val main = listOf<Carte>(Carte3aLaSuite(), Carte3aLaSuite(), Carte3aLaSuite())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 4 [Carte3aLaSuite]
     */

    @Test
    @DisplayName("Check main 4 carte 3 à la suite")
    fun CT14_verifieMain() {
        val main = listOf<Carte>(Carte3aLaSuite(), Carte3aLaSuite(), Carte3aLaSuite(), Carte3aLaSuite())
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    // ==========================================
    // 6. CARTE SECONDE CHANCE
    // ==========================================

    /**
     * Vérifie une main avec une [Carte2ndeChanceTest]
     */

    @Test
    @DisplayName("Check main 1 carte 2nde chance")
    fun CT15_verifieMain() {
        val main = listOf<Carte>(Carte2ndeChance())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 2 [Carte2ndeChanceTest]
     */

    @Test
    @DisplayName("Check main 2 carte 2nde chance")
    fun CT16_verifieMain() {
        val main = listOf<Carte>(Carte2ndeChance(), Carte2ndeChance())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 3 [Carte2ndeChanceTest]
     */

    @Test
    @DisplayName("Check main 3 carte 2nde chance")
    fun CT17_verifieMain() {
        val main = listOf<Carte>(Carte2ndeChance(), Carte2ndeChance(), Carte2ndeChance())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 4 [Carte2ndeChanceTest]
     */
    @Test
    @DisplayName("Check main 4 carte 2nde chance")
    fun CT18_verifieMain() {
        val main = listOf<Carte>(Carte2ndeChance(), Carte2ndeChance(), Carte2ndeChance(), Carte2ndeChance())
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    // ==========================================
    // 7. STRUCTURES GLOBALES DE LA MAIN
    // ==========================================

    /**
     * Vérifie 2 double et une carte seconde Chance
     */
    @Test
    @DisplayName("Check main 1 carte 2nde chance et 2 double")
    fun CT19_verifieMain() {
        val main = listOf<Carte>(
            CarteNum(0), CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(4), Carte2ndeChance(), CarteBonusPlus(2)
        )
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une pioche vide
     */
    @Test
    @DisplayName("check Main vide")
    fun CT20_verifieMain() {
        val main = listOf<Carte>()
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }


    /**
     * Vérifie une main avec 2 cartes Bonus Plus de valeurs différentes
     */
    @Test
    @DisplayName("Check main 2 carte 2nde chance")
    fun CT21_verifieMain() {
        val main = listOf<Carte>(CarteBonusPlus(2), CarteBonusPlus(4))
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vériie une main avec 8 cartes Num de valeurs différentesn (Flip7 dépasser)
     */
    @Test
    fun CT22_verifieMain() {
        val main = listOf<Carte>(
            CarteNum(2), CarteNum(3), CarteNum(4), CarteNum(5),
            CarteNum(6), CarteNum(7), CarteNum(8), CarteNum(9)
        )
        assertThrows<MainInvalideException> { outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main valide
     */
    @Test
    @DisplayName("Check main Valide")
    fun CT23_verifieMain() {
        val main = mutableListOf<Carte>()

        for (i in 0..5) {
            main.add(CarteNum(i))
            assertDoesNotThrow { outils.verifieMainCorrecte(main) }
        }
        main.add(CarteBonusMultiplie())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }

        main.add(CarteBonusPlus(2))
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }

        main.add(CarteStop())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }
    /**
     * Vérifie une main valide
     */
    @Test
    @DisplayName("Check main Valide")
    fun CT24_verifieMain() {
        val main = mutableListOf<Carte>()

        for (i in 0..6) {
            main.add(CarteNum(i))
            assertDoesNotThrow { outils.verifieMainCorrecte(main) }
        }
    }

    /**
     * Vérifie une main valide Pleine
     */
    @Test
    @DisplayName("Check main Valide Pleine")
    fun CT25_verifieMain(){
        val main = listOf<Carte>(CarteBonusPlus(2), CarteBonusPlus(4), CarteBonusPlus(6),
            CarteBonusPlus(8), CarteBonusPlus(10),Carte2ndeChance(),Carte3aLaSuite(), CarteNum(0), CarteNum(1), CarteNum(2), CarteNum(3), CarteNum(4), CarteNum(5), CarteNum(6),
            CarteBonusMultiplie())
        assertDoesNotThrow { outils.verifieMainCorrecte(main) }
    }



    // ==========================================
    // 8. MAIN SPECIAL
    // ==========================================

    /**
     * Vérifie une main avec 2 cartes Num de même valeur et une carte Stop
     */
    @Test
    @DisplayName("Check main 2 carte Num de même valeur et une carte Stop")
    fun CT26_verifieMain(){
        val main = listOf<Carte>(CarteNum(2),CarteNum(2), CarteStop())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 2 cartes Num de même valeur et une carte 2nde Chance
     */
    @Test
    @DisplayName("Check main 2 carte Num de même valeur et une carte 2nde Chance")
    fun CT27_verifieMain(){
        val main = listOf<Carte>(CarteNum(2),CarteNum(2), Carte2ndeChance())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }

    /**
     * Vérifie une main avec 2 cartes Num de même valeur et une carte 3 à la suite
     */
    @Test
    @DisplayName("Check main 2 carte Num de même valeur et une carte 3 à la suite")
    fun CT28_verifieMain(){
        val main = listOf<Carte>(CarteNum(2),CarteNum(2), Carte3aLaSuite())
        assertDoesNotThrow{ outils.verifieMainCorrecte(main) }
    }

    // ==========================================
    // 9. Cas de bug
    // ==========================================

    /*
    @Test
    fun CT29_verifieMain(){
        val main = listOf<Carte>(Carte3aLaSuite(), Carte3aLaSuite())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }
    @Test
    fun CT30_verifieMain(){
        val main = listOf<Carte>(Carte3aLaSuite(), CarteStop())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }

    @Test
    fun CT31_verifieMain(){
        val main = listOf<Carte>(Carte3aLaSuite(), CarteStop(), Carte3aLaSuite())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }

    @Test
    fun CT32_verifieMain(){
        val main = listOf<Carte>(Carte3aLaSuite(), Carte3aLaSuite(), Carte3aLaSuite())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }

    //
    //BUG
    //
    @Test
    fun CT33_verifieMain(){
        val main = listOf<Carte>(CarteNum(1),CarteNum(2),CarteNum(3),CarteNum(4),CarteNum(5),CarteNum(6),CarteNum(7),
            CarteStop())
        assertThrows<MainInvalideException>{ outils.verifieMainCorrecte(main) }
    }
    */






    


}