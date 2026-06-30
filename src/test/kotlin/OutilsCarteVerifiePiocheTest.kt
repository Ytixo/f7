import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.OutilsCarte
import iut.info1.flip7.exceptions.PiocheInvalideException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows


class OutilsCarteVerifiePiocheTest {
    val outils = OutilsCarte()

    // =========================================================================
    // THÈME 1 : PIOCHES VALIDES
    // =========================================================================
    /** CT01 : Vérification d'une pioche complète */
    @Test
    fun CT01_VerifiePiocheValideBasique(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }

        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertDoesNotThrow{ outils.verifiePiocheInitiale(pioche) }
    }

    /** CT02 : Vérification d'une pioche valide mais mélangée */
    @Test
    fun CT02_VerifiePiocheValideMelangee(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        pi.shuffle()
        assertDoesNotThrow{ outils.verifiePiocheInitiale(pioche) }
    }

    // =========================================================================
    // THÈME 2 : NOMBRE DE CARTE NON RÉGLEMENTAIRE
    // =========================================================================
    /** CT03 : Pioche avec une carte */
    @Test
    fun CT03_VerifiePiochePresqueVide(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException>{ outils.verifiePiocheInitiale(pioche) }
    }

    /** CT04 : Pioche sans deux cartes */
    @Test
    fun CT04_VerifiePiocheManqueDeuxCartes() {
        val piocheValide = arrayListOf<Carte>()
        piocheValide.add(CarteNum(0))

        for (i in 1..12) {
            for (j in 0 until i) {
                piocheValide.add(CarteNum(i))
            }
        }
        for (i in listOf(2, 4, 6, 8, 10)) {
            piocheValide.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3) {
            piocheValide.add(CarteStop())
            piocheValide.add(Carte2ndeChance())
            piocheValide.add(Carte3aLaSuite())
        }
        piocheValide.add(CarteBonusMultiplie())

        val copie = ArrayList(piocheValide)

        for (i in 0 until copie.size - 1) {
            for (j in i + 1 until copie.size) {
                val pioche = ArrayList(piocheValide)
                pioche.removeAt(j)
                pioche.removeAt(i)

                assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
            }
        }
    }

    /** CT05 : Pioche avec une carte en trop  */
    @Test
    fun CT05_VerifiePiocheAvecUneCarteEnTrop() {
        val piocheValide = arrayListOf<Carte>()
        piocheValide.add(CarteNum(0))

        for (i in 1..12) {
            for (j in 0 until i) {
                piocheValide.add(CarteNum(i))
            }
        }
        for (i in listOf(2, 4, 6, 8, 10)) {
            piocheValide.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3) {
            piocheValide.add(CarteStop())
            piocheValide.add(Carte2ndeChance())
            piocheValide.add(Carte3aLaSuite())
        }
        piocheValide.add(CarteBonusMultiplie())

        val copie = ArrayList(piocheValide)

        for (i in 0 until copie.size - 1) {
            val pioche = ArrayList(piocheValide)
            pioche.add(copie[i])
            assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
        }
    }

    // =========================================================================
    // THÈME 3 : ERREUR sur les cartes Numériques (CarteNum)
    // =========================================================================

    /** CT06 : Pioche contenant des doublons invalides de CarteNum(1) */
    @Test
    fun CT06_VerifiePiocheDoublonCarteNumUn(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        pi.add(CarteNum(2))
        pi.add(CarteNum(1))
        pi.add(CarteNum(1))
        for (i in 2 until 13){
            for ( j in 2 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException>{ outils.verifiePiocheInitiale(pioche) }
    }

    /** CT07 : Pioche contenant à la fois un doublon de CarteNum(1) et un doublon de Multiplie */
    @Test
    fun CT07_VerifiePiocheDoublonCarteMultiplie(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        pi.add(CarteNum(0))

        for (i in 1..12) {
            for (j in 0 until i) {
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException>{ outils.verifiePiocheInitiale(pioche) }
    }

    /** CT08 : Pioche où la carte obligatoire CarteNum(0) est manquante */
    @Test
    fun CT08_VerifiePiocheManqueCarteNumZero(){
        var pi = arrayListOf<Carte>()
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
    }

    // =========================================================================
    // THÈME 4 : Erreur sur les cartes d'Action (Stop, 2ndeChance, 3aLaSuite)
    // =========================================================================

    /** CT09 : Pioche sans aucune carte Stop et remplacée par des cartes 3aLaSuite */
    @Test
    fun CT09_VerifiePiocheSansCarteStop(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(Carte3aLaSuite())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
    }

    /** CT10 : Pioche avec une mauvaise répartition des cartes */
    @Test
    fun CT10_VerifiePiocheMauvaiseRepartitionActions(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 2){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(Carte3aLaSuite())
        pi.add(Carte2ndeChance())
        pi.add(Carte3aLaSuite())
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
    }

    // =========================================================================
    // THÈME 5 : Anomalies sur les cartes Bonus (BonusPlus, BonusMultiplie)
    // =========================================================================

    /** CT11 : Pioche contenant deux cartes BonusMultiplie au lieu d'une seule */
    @Test
    fun CT11_VerifiePiocheDeuxBonusMultiplie(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())
        pi.add(CarteBonusMultiplie())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException>{ outils.verifiePiocheInitiale(pioche) }
    }

    /** CT12 : Pioche où la carte BonusMultiplie est remplacée par un BonusPlus(+2) */
    @Test
    fun CT12_VerifiePiocheBonusMultiplieRemplaceParBonusPlus(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusPlus(2))
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
    }

    /** CT13 : Pioche où la carte BonusMultiplie est remplacée par une carte action (2ndeChance) */
    @Test
    fun CT13_VerifiePiocheBonusMultiplieRemplaceParAction(){
        var pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 0 until 13){
            for ( j in 0 until i){
                pi.add(CarteNum(i))
            }
        }
        for (i in listOf(2,4,6,8,10)){
            pi.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3){
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(Carte2ndeChance())
        var pioche : List<Carte> = pi
        print(pioche)
        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
    }

    /** CT14 : Pioche contenant cinq fois la valeur 2 pour les BonusPlus au lieu de la suite 2, 4, 6, 8, 10 */
    @Test
    fun CT14_VerifiePiocheCinqFoisBonusPlusDeux() {
        val pi = arrayListOf<Carte>(CarteNum(0))
        for (i in 1..12) {
            for (j in 0 until i) {
                pi.add(CarteNum(i))
            }
        }
        for (i in 0 until 5) {
            pi.add(CarteBonusPlus(2))
        }
        for (i in 0 until 3) {
            pi.add(CarteStop())
            pi.add(Carte2ndeChance())
            pi.add(Carte3aLaSuite())
        }
        pi.add(CarteBonusMultiplie())

        assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pi) }
    }

    // =========================================================================
    // THÈME 6 : ÉCHANGE DU NOMBRE DE CARTE
    // =========================================================================

    /** CT15 : Pioche où une carte valide est remplacée par un doublon d'une autre carte présente  */
    @Test
    fun CT15_VerifiePiocheSubstitutionParDoublon() {
        val piocheValide = arrayListOf<Carte>()
        piocheValide.add(CarteNum(0))

        for (i in 1..12) {
            for (j in 0 until i) {
                piocheValide.add(CarteNum(i))
            }
        }
        for (i in listOf(2, 4, 6, 8, 10)) {
            piocheValide.add(CarteBonusPlus(i))
        }
        for (i in 0 until 3) {
            piocheValide.add(CarteStop())
            piocheValide.add(Carte2ndeChance())
            piocheValide.add(Carte3aLaSuite())
        }
        piocheValide.add(CarteBonusMultiplie())

        val copie = ArrayList(piocheValide)

        for (i in 0 until copie.size - 1) {
            for (j in i + 1 until copie.size) {
                if (copie[i] != copie[j]){
                    val pioche = ArrayList(piocheValide)
                    pioche[i] = pioche[j]
                    assertThrows<PiocheInvalideException> { outils.verifiePiocheInitiale(pioche) }
                }
            }
        }
    }
}